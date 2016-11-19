package dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import dao.CityDao;
import dao.ImgDao;
import dao.SectionDao;
import dao.ShowedItemDao;
import dao.exceptions.DaoSystemException;
import dao.exceptions.NoSuchEntityException;
import entity.Adress;
import entity.City;
import entity.Map;
import entity.Rating;
import entity.Section;
import entity.Tag;
import entity.ShowedItem;
import entity.ShowedUser;
import entity.impl.SimpleAdressImpl;
import entity.impl.SimpleCity;
import entity.impl.SimpleMapImpl;
import entity.impl.SimpleRatingImpl;
import entity.impl.SimpleShowedItem;
import entity.impl.SimpleShowedUser;
import entity.impl.SimpleTagImpl;
import entity.work.Work;
import entity.work.impl.SimpleWorkFactoryImpl;

import static utils.JBDCUtil.closeQuaetly;

public class ShowedItemDAOJDBCimpl implements ShowedItemDao {

	public static Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	private final static String ENTITY_TABL_NAME = "freecity.entity";
	private final static String ENTITY_ID_COL_NAME = "entity_id";
	private final static String ENTITY_HEADER_COL_NAME = "header";
	private final static String ENTITY_DESCRIPTION_COL_NAME = "description";
	private final static String ENTITY_DATE_COL_NAME = "entity.date";
	private final static String ENTITY_USER_COL_NAME = "entity.user_id";

	private final static String USER_TABL_NAME = "freecity.user";
	private final static String USER_ID_COL_NAME = "user_id";
	private final static String USER_FIRST_NAME_COL_NAME = "first_name";
	private final static String USER_LAST_NAME_COL_NAME = "last_name";
	private final static String USER_EMAIL_COL_NAME = "email";
	private final static String USER_LOGIN_COL_NAME = "login";

	private final static String CITY_TABL_NAME = "freecity.city";
	private final static String CITY_ID_COL_NAME = "city.city_id";
	private final static String CITY_NAME_COL_NAME = "name";
	private final static String CITY_IDs_COL_NAME = "city_ids";
	private final static String CITY_NAMEs_COL_NAME = "city_names";

	private final static String SECTION_TABL_NAME = "freecity.section";
	private final static String SECTION_IDs_COL_NAME = "section_id";
	private final static String SECTION_NAME_COL_NAME = "name";

	private final static String URL_TABL_NAME = "freecity.url";
	private final static String URL_IDs_COL_NAME = "entity_id";
	private final static String URL_LINK_COL_NAME = "url_link";

	private final static String MAP_TABL_NAME = "freecity.map";
	private final static String MAP_IDs_COL_NAME = "entity_id";
	private final static String MAP_LINK_COL_NAME = "map_url";

	private final static String TAG_TABL_NAME = "freecity.tags";
	private final static String TAG_TAG_ID_COL_NAME = "tags.tag_id";
	private final static String TAG_TAG_NAME_COL_NAME = "tag_name";

	private final static String RATING_TABL_NAME = "freecity.rating";
	private final static String RATING_COL_NAME = "rating";
	private final static String RATING_TAG_ID_COL_NAME = "rating.voice";
	private final static String RATING_TAG_NAME_COL_NAME = "rating.stars";

	private final static String ADRESS_ADRESS_COL_NAME = "adress";
	private final static String WORK_COL_NAME = "work_date";
	private final static String IMG_LIST_COL_NAME = "img_list";
	private final static String IMG_COL_NAME = "img";

	private final static String ID = "ID";
	private final static String SELECT_ALL_CALL = "{call selectAll()}";
	private final static String SELECT_BY_ID_CALL = "{call selectById(@" + ID + ")}";

	private final static String COL_NAME = "column_name ";
	private final static String COL_VAL = "column_val ";
	private final static String COL_LIMIT = "column_limit ";
	private final static String OPERATION = "\' = \'";
	// private final static String SELECT_BY_PROP_CALL = "{call
	// getItemsByProperties(\'@" + COL_NAME + "\', @" + OPERATION
	// + ", @" + COL_VAL + ")}";
	// private final static String SELECT_LIMIT_CALL = "{call
	// selectAllLimitFrom(@" + COL_LIMIT + ")}";
	// private final static String SELECT_LIMIT_PROP_CALL = "{call
	// getItemsByPropertiesLimit(\'@" + COL_NAME + "\', @"
	// + OPERATION + ", @" + COL_VAL + ", @" + COL_LIMIT + ")}";
	// private final static String SELECT_BY_PROP_CALL_CITY = "{call
	// getItemsByPropertiesCity(\'@" + COL_NAME + "\', @"
	// + OPERATION + ", @" + COL_VAL + ", \'@" + CITY_ID_COL_NAME + "\')}";
	// private final static String SELECT_LIMIT_PROP_CITY_CALL = "{call
	// getItemsByPropertiesLimitCity(\'@" + COL_NAME
	// + "\', @" + OPERATION + ", @" + COL_VAL + ",\'@" + COL_LIMIT + "\', \'@"
	// + CITY_ID_COL_NAME + "\')}";

//	private final static String CONDITIONS1 = "conditions1";
//	private final static String CONDITIONS2 = "conditions2";
//	private final static String CONDITIONS3 = "conditions3";

//	private final static String EXECUTE_SELECT_ALL_CALL = "{call executeSelectAll(@" + CONDITIONS1 + ", @" + CONDITIONS2
//			+ ", @" + CONDITIONS3 + ")}";

	private final static String EXECUTE_SELECT_ALL_CALL = "{call executeSelectAll(?,?,?,?)}";

	private SectionDao sectionDao;

	private DataSource dataSource;

	private CityDao cityDao;
	
	private ImgDao imgDao;

	public ImgDao getImgDao() {
		return imgDao;
	}

	public void setImgDao(ImgDao imgDao) {
		this.imgDao = imgDao;
	}

	public void setCityDao(CityDao cityDao) {
		this.cityDao = cityDao;
	}
	
	
	@Override
	public CopyOnWriteArraySet<ShowedItem> executeSelectAll(String city, String date, String section,
			String section_view, String user, String from, String limit, String tag, String action, int [] count)
			throws NoSuchEntityException, NumberFormatException, DaoSystemException {

		CallableStatement selectAllStat = null;
		ResultSet selectAllResultSet = null;
		CopyOnWriteArraySet<ShowedItem> entitys = new CopyOnWriteArraySet<ShowedItem>();
		StringBuilder CONDITIONS11 = new StringBuilder("");
		StringBuilder CONDITIONS22 = new StringBuilder("");
		StringBuilder CONDITIONS33 = new StringBuilder(""); 
		try {
			Connection conn = dataSource.getConnection();
			if (city != null && !city.equals("") && !city.equals("-1")) {

				if (CONDITIONS11.toString().equals(""))
					CONDITIONS11.append(" where ");
				else
					CONDITIONS11.append(" and ");
				CONDITIONS11.append(" city.city_id = " + city);
			}
			if (date != null && !date.equals("")) {

				Calendar ret = Calendar.getInstance();
				ret.setTimeInMillis(Long.parseLong(date));
				Formatter month = new Formatter().format("%1$02d", (1 + ret.get(Calendar.MONTH)));
				Formatter dates = new Formatter().format("%1$02d", ret.get(Calendar.DATE));
				String id1 = +ret.get(Calendar.YEAR) + "" + month + "" + dates + "000000  AND  "
						+ ret.get(Calendar.YEAR) + "" + month + "" + dates + "235959 ";
				month.close();

				if (CONDITIONS11.toString().equals(""))
					CONDITIONS11.append(" where ");
				else
					CONDITIONS11.append(" and ");

				CONDITIONS11.append(" entity.date BETWEEN " + id1);
			}
			if (section != null && !section.equals("")) {

				String id1 = "(";
				List<Section> s = sectionDao.getSectionById(new Integer(section)).getAllChilds();
				int i = s.size();
				for (Section id0 : s) {
					id1 += id0.getSectionId();
					if (--i > 0)
						id1 += ", ";
				}
				id1 += ")";

				if (CONDITIONS11.toString().equals(""))
					CONDITIONS11.append(" where ");
				else
					CONDITIONS11.append(" and ");

				CONDITIONS11.append(" section.section_id in" + id1);
			}
			if (user != null && !user.equals("")) {

				if (CONDITIONS11.toString().equals(""))
					CONDITIONS11.append(" where ");
				else
					CONDITIONS11.append(" and ");

				CONDITIONS11.append(" user.user_id = " + user);
			}
			if (limit != null && !limit.equals("")) {

				CONDITIONS22.append(" limit ");
				if (from != null && !from.equals("") && !from.equals("0")) {
					CONDITIONS22.append(from + ", ");
				}
				CONDITIONS22.append(limit);
			}
			if (tag != null && !tag.equals("")) {
				if (CONDITIONS11.toString().equals(""))
					CONDITIONS11.append(" where ");
				else
					CONDITIONS11.append(" and ");

				CONDITIONS11.append(" tags.tag_id = " + tag);
			}
			if (section_view != null && !section_view.equals("")) {
				if (CONDITIONS11.toString().equals(""))
					CONDITIONS11.append(" where ");
				else
					CONDITIONS11.append(" and ");

				CONDITIONS11.append(" section.section_view in(" + section_view + ")");
			}
 
			if (action != null && !action.equals("")) {

				Calendar ret = Calendar.getInstance();
				ret.setFirstDayOfWeek(Calendar.MONDAY);
				String actions1 = null;
				String actions2 = null;
				if (action.equalsIgnoreCase("active")) {
					actions1 = " date.date_from <= \'" + ret.get(Calendar.YEAR) + "-" + (ret.get(Calendar.MONTH) + 1)
							+ "-" + ret.get(Calendar.DAY_OF_MONTH) + "\' and date.date_to >= \'"
							+ ret.get(Calendar.YEAR) + "-" + (ret.get(Calendar.MONTH) + 1) + "-"
							+ ret.get(Calendar.DAY_OF_MONTH) + "\'";
				} else if (action.equalsIgnoreCase("now")) {
					actions1 = " date.date_from <= \'" + ret.get(Calendar.YEAR) + "-" + (ret.get(Calendar.MONTH) + 1)
							+ "-" + ret.get(Calendar.DAY_OF_MONTH) + "\' and date.date_to >= \'"
							+ ret.get(Calendar.YEAR) + "-" + (ret.get(Calendar.MONTH) + 1) + "-"
							+ ret.get(Calendar.DAY_OF_MONTH) + "\' and date.month_from  <=\'"
							+ (ret.get(Calendar.MONTH) + 1) + "\' and date.month_to  >=\'";

					int dayOfWeek = ret.get(Calendar.DAY_OF_WEEK) - 1;
					if (dayOfWeek == 0)
						dayOfWeek = 7;
					actions2 = (ret.get(Calendar.MONTH) + 1) + "\' and date.day_from  <=\'" + (dayOfWeek)
							+ "\' and date.day_to  >=\'" + (dayOfWeek) + "\' and date.time_from  <=\'"
							+ (ret.get(Calendar.HOUR_OF_DAY)) + ":" + (ret.get(Calendar.MINUTE))
							+ "\' and date.time_to  >=\'" + (ret.get(Calendar.HOUR_OF_DAY)) + ":"
							+ (ret.get(Calendar.MINUTE) + "\'");
				} else if (action.equalsIgnoreCase("future")) {
					actions1 = " date.date_from > \'" + ret.get(Calendar.YEAR) + "-" + (ret.get(Calendar.MONTH) + 1)
							+ "-" + ret.get(Calendar.DAY_OF_MONTH) + "\'";
				} else if (action.equalsIgnoreCase("archive")) {
					actions1 = " date.date_to < \'" + ret.get(Calendar.YEAR) + "-" + (ret.get(Calendar.MONTH) + 1)
							+ "-" + ret.get(Calendar.DAY_OF_MONTH) + "\'";
				}

				if (actions1 != null) {
					if (CONDITIONS11.toString().equals(""))
						CONDITIONS11.append(" where ");
					else
						CONDITIONS11.append(" and ");
					CONDITIONS11.append(actions1);
				}
				if (actions2 != null) {
					CONDITIONS33.append(actions2);
				}
			} else {
 
				
				Calendar ret = Calendar.getInstance();
				ret.setFirstDayOfWeek(Calendar.MONDAY);
				String actions1 = " date.date_to >= \'" + ret.get(Calendar.YEAR) + "-" + (ret.get(Calendar.MONTH) + 1)
						+ "-" + ret.get(Calendar.DAY_OF_MONTH) + "\'";
				if (CONDITIONS11.toString().equals(""))
					CONDITIONS11.append(" where ");
				else
					CONDITIONS11.append(" and ");
				CONDITIONS11.append(actions1);
			}
 
			String SELECT_BY_PROP_CALL_TO_CALL = EXECUTE_SELECT_ALL_CALL; 

			log.info("SQL to Execute : " + SELECT_BY_PROP_CALL_TO_CALL);
			selectAllStat = conn.prepareCall(SELECT_BY_PROP_CALL_TO_CALL);

			selectAllStat.setString(1, CONDITIONS11.toString()); 
			selectAllStat.setString(2, CONDITIONS22.toString()); 
			selectAllStat.setString(3, CONDITIONS33.toString()); 
			selectAllStat.registerOutParameter(4, java.sql.Types.INTEGER);

			selectAllStat.execute();

			if(count!=null)
			count[0] = selectAllStat.getInt(4);
			selectAllResultSet = selectAllStat.getResultSet();
			log.info("CallableStatement : " + SELECT_BY_PROP_CALL_TO_CALL + " was execute!");

			createItems(selectAllResultSet, entitys);

		} catch (SQLException e) {
			log.error("Error description", e);
			throw new DaoSystemException(e);
		} finally {
			log.debug("Close SELECT_ALL_SQL ResultSet and SELECT_ALL_SQL Statement");
			try {
				closeQuaetly(selectAllResultSet, selectAllStat);
			} catch (Exception e1) {
				log.error(e1);
				throw new DaoSystemException(e1);
			}
		}

		return entitys;
	}


//	@Override
//	public CopyOnWriteArraySet<ShowedItem> executeSelectAll(String city, String date, String section,
//			String section_view, String user, String from, String limit, String tag, String action)
//			throws NoSuchEntityException, NumberFormatException, DaoSystemException {
//
//		CallableStatement selectAllStat = null;
//		ResultSet selectAllResultSet = null;
//		CopyOnWriteArraySet<ShowedItem> entitys = new CopyOnWriteArraySet<ShowedItem>();
//		StringBuilder CONDITIONS11 = new StringBuilder("\'");
//		StringBuilder CONDITIONS22 = new StringBuilder("\'");
//		StringBuilder CONDITIONS33 = new StringBuilder("\'");
//		try {
//			Connection conn = dataSource.getConnection();
//			if (city != null && !city.equals("") && !city.equals("-1")) {
//
//				if (CONDITIONS11.toString().equals("\'"))
//					CONDITIONS11.append(" where ");
//				else
//					CONDITIONS11.append(" and ");
//				CONDITIONS11.append(" city.city_id = " + city);
//			}
//			if (date != null && !date.equals("")) {
//
//				Calendar ret = Calendar.getInstance();
//				ret.setTimeInMillis(Long.parseLong(date));
//				Formatter month = new Formatter().format("%1$02d", (1 + ret.get(Calendar.MONTH)));
//				Formatter dates = new Formatter().format("%1$02d", ret.get(Calendar.DATE));
//				String id1 = +ret.get(Calendar.YEAR) + "" + month + "" + dates + "000000  AND  "
//						+ ret.get(Calendar.YEAR) + "" + month + "" + dates + "235959 ";
//				month.close();
//
//				if (CONDITIONS11.toString().equals("\'"))
//					CONDITIONS11.append(" where ");
//				else
//					CONDITIONS11.append(" and ");
//
//				CONDITIONS11.append(" entity.date BETWEEN " + id1);
//			}
//			if (section != null && !section.equals("")) {
//
//				String id1 = "(";
//				List<Section> s = sectionDao.getSectionById(new Integer(section)).getAllChilds();
//				int i = s.size();
//				for (Section id0 : s) {
//					id1 += id0.getSectionId();
//					if (--i > 0)
//						id1 += ", ";
//				}
//				id1 += ")";
//
//				if (CONDITIONS11.toString().equals("\'"))
//					CONDITIONS11.append(" where ");
//				else
//					CONDITIONS11.append(" and ");
//
//				CONDITIONS11.append(" section.section_id in " + id1);
//			}
//			if (user != null && !user.equals("")) {
//
//				if (CONDITIONS11.toString().equals("\'"))
//					CONDITIONS11.append(" where ");
//				else
//					CONDITIONS11.append(" and ");
//
//				CONDITIONS11.append(" user.user_id = " + user);
//			}
//			if (limit != null && !limit.equals("")) {
//
//				CONDITIONS22.append(" limit ");
//				if (from != null && !from.equals("") && !from.equals("0")) {
//					CONDITIONS22.append(from + ", ");
//				}
//				CONDITIONS22.append(limit);
//			}
//			if (tag != null && !tag.equals("")) {
//				if (CONDITIONS11.toString().equals("\'"))
//					CONDITIONS11.append(" where ");
//				else
//					CONDITIONS11.append(" and ");
//
//				CONDITIONS11.append(" tags.tag_id = " + tag);
//			}
//			if (section_view != null && !section_view.equals("")) {
//				if (CONDITIONS11.toString().equals("\'"))
//					CONDITIONS11.append(" where ");
//				else
//					CONDITIONS11.append(" and ");
//
//				CONDITIONS11.append(" section.section_view in (" + section_view + ")");
//			}
//
//			if (action != null && !action.equals("")) {
//
//				Calendar ret = Calendar.getInstance();
//				ret.setFirstDayOfWeek(Calendar.MONDAY);
//				String actions1 = null;
//				String actions2 = null;
//				if (action.equalsIgnoreCase("active")) {
//					actions1 = " date.date_from <= \\\'" + ret.get(Calendar.YEAR) + "-" + (ret.get(Calendar.MONTH) + 1)
//							+ "-" + ret.get(Calendar.DAY_OF_MONTH) + "\\\' and date.date_to >= \\\'"
//							+ ret.get(Calendar.YEAR) + "-" + (ret.get(Calendar.MONTH) + 1) + "-"
//							+ ret.get(Calendar.DAY_OF_MONTH) + "\\\'";
//				} else if (action.equalsIgnoreCase("now")) {
//					actions1 = " date.date_from <= \\\'" + ret.get(Calendar.YEAR) + "-" + (ret.get(Calendar.MONTH) + 1)
//							+ "-" + ret.get(Calendar.DAY_OF_MONTH) + "\\\' and date.date_to >= \\\'"
//							+ ret.get(Calendar.YEAR) + "-" + (ret.get(Calendar.MONTH) + 1) + "-"
//							+ ret.get(Calendar.DAY_OF_MONTH) + "\\\' and date.month_from  <=\\\'"
//							+ (ret.get(Calendar.MONTH) + 1) + "\\\' and date.month_to  >=\\\'";
//
//					int dayOfWeek = ret.get(Calendar.DAY_OF_WEEK) - 1;
//					if (dayOfWeek == 0)
//						dayOfWeek = 7;
//					actions2 = (ret.get(Calendar.MONTH) + 1) + "\\\' and date.day_from  <=\\\'" + (dayOfWeek)
//							+ "\\\' and date.day_to  >=\\\'" + (dayOfWeek) + "\\\' and date.time_from  <=\\\'"
//							+ (ret.get(Calendar.HOUR_OF_DAY)) + ":" + (ret.get(Calendar.MINUTE))
//							+ "\\\' and date.time_to  >=\\\'" + (ret.get(Calendar.HOUR_OF_DAY)) + ":"
//							+ (ret.get(Calendar.MINUTE) + "\\\'");
//				} else if (action.equalsIgnoreCase("future")) {
//					actions1 = " date.date_from > \\\'" + ret.get(Calendar.YEAR) + "-" + (ret.get(Calendar.MONTH) + 1)
//							+ "-" + ret.get(Calendar.DAY_OF_MONTH) + "\\\'";
//				} else if (action.equalsIgnoreCase("archive")) {
//					actions1 = " date.date_to < \\\'" + ret.get(Calendar.YEAR) + "-" + (ret.get(Calendar.MONTH) + 1)
//							+ "-" + ret.get(Calendar.DAY_OF_MONTH) + "\\\'";
//				}
//
//				if (actions1 != null) {
//					if (CONDITIONS11.toString().equals("\'"))
//						CONDITIONS11.append(" where ");
//					else
//						CONDITIONS11.append(" and ");
//					CONDITIONS11.append(actions1);
//				}
//				if (actions2 != null) {
//					CONDITIONS33.append(actions2);
//				}
//			} else {
//
//				Calendar ret = Calendar.getInstance();
//				ret.setFirstDayOfWeek(Calendar.MONDAY);
//				String actions1 = " date.date_to >= \\\'" + ret.get(Calendar.YEAR) + "-" + (ret.get(Calendar.MONTH) + 1)
//						+ "-" + ret.get(Calendar.DAY_OF_MONTH) + "\\\'";
//				if (CONDITIONS11.toString().equals("\'"))
//					CONDITIONS11.append(" where ");
//				else
//					CONDITIONS11.append(" and ");
//				CONDITIONS11.append(actions1);
//			}
//
//			CONDITIONS11.append("\'");
//			CONDITIONS22.append("\'");
//			CONDITIONS33.append("\'");
//			String SELECT_BY_PROP_CALL_TO_CALL = EXECUTE_SELECT_ALL_CALL
//					.replace("@" + CONDITIONS1, CONDITIONS11.toString())
//					.replace("@" + CONDITIONS2, CONDITIONS22.toString())
//					.replace("@" + CONDITIONS3, CONDITIONS33.toString());
//
//			log.info("SQL to Execute : " + SELECT_BY_PROP_CALL_TO_CALL);
//			selectAllStat = conn.prepareCall(SELECT_BY_PROP_CALL_TO_CALL);
//			selectAllStat.execute();
//			selectAllResultSet = selectAllStat.getResultSet();
//			log.info("CallableStatement : " + SELECT_BY_PROP_CALL_TO_CALL + " was execute!");
//
//			createItems(selectAllResultSet, entitys);
//
//		} catch (SQLException e) {
//			log.error("Error description", e);
//			throw new DaoSystemException(e);
//		} finally {
//			log.debug("Close SELECT_ALL_SQL ResultSet and SELECT_ALL_SQL Statement");
//			try {
//				closeQuaetly(selectAllResultSet, selectAllStat);
//			} catch (Exception e1) {
//				log.error(e1);
//				throw new DaoSystemException(e1);
//			}
//		}
//
//		return entitys;
//	}

	@Override
	public ShowedItem selectById(String id) throws DaoSystemException, NoSuchEntityException {

		CallableStatement selectByIdStat = null;
		ResultSet selectByIdResultSet = null;

		try {

			Connection conn = dataSource.getConnection();
			log.debug("SQL to Execute : " + SELECT_BY_ID_CALL.replaceAll("@" + ID, id));
			selectByIdStat = conn.prepareCall(SELECT_BY_ID_CALL.replaceAll("@" + ID, id));
			selectByIdStat.execute();
			selectByIdResultSet = selectByIdStat.getResultSet();
			log.debug("CallableStatement : " + SELECT_BY_ID_CALL.replaceAll("@" + ID, id) + " was execute!");

			if (selectByIdResultSet.next()) {
				printItem(selectByIdResultSet);
				return createItem(selectByIdResultSet);

			} else {
				throw new NoSuchEntityException("Wrong ID number: \'" + id + "\' is  incorrect!");
			}

		} catch (SQLException e) {
			throw new DaoSystemException(e);
		} finally {
			try {
				closeQuaetly(selectByIdResultSet, selectByIdStat);
			} catch (Exception e1) {
				throw new DaoSystemException(e1);
			}
		}
	}

	@Override
	public CopyOnWriteArraySet<ShowedItem> selectAll()
			throws DaoSystemException, NoSuchEntityException, NumberFormatException {
		CallableStatement selectAllStat = null;
		ResultSet selectAllResultSet = null;
		CopyOnWriteArraySet<ShowedItem> entitys = new CopyOnWriteArraySet<ShowedItem>();
		try {
			Connection conn = dataSource.getConnection();

			log.info("SQL to Execute : " + SELECT_ALL_CALL);
			selectAllStat = conn.prepareCall(SELECT_ALL_CALL);
			selectAllStat.execute();
			selectAllResultSet = selectAllStat.getResultSet();
			log.debug("CallableStatement : " + SELECT_ALL_CALL + " was execute!");

			createItems(selectAllResultSet, entitys);

		} catch (SQLException e) {
			log.error("Error description", e);
			throw new DaoSystemException(e);
		} finally {
			log.debug("Close SELECT_ALL_SQL ResultSet and SELECT_ALL_SQL Statement");
			try {
				closeQuaetly(selectAllResultSet, selectAllStat);
			} catch (Exception e1) {
				log.error(e1);
				throw new DaoSystemException(e1);
			}
		}

		return entitys;
	}

	/*
	 * @Override public CopyOnWriteArraySet<ShowedItem> selectAllLimit(String
	 * from, String count) throws NoSuchEntityException, NumberFormatException,
	 * DaoSystemException { CallableStatement selectByPropStat = null; ResultSet
	 * selectByPropResultSet = null; CopyOnWriteArraySet<ShowedItem> entitys =
	 * new CopyOnWriteArraySet<ShowedItem>(); Connection conn = null; try {
	 * 
	 * String SELECT_BY_PROP_CALL_TO_CALL = SELECT_LIMIT_CALL.replace("@" +
	 * COL_LIMIT, from + ", " + count);
	 * 
	 * log.info("SQL to Execute : " + SELECT_BY_PROP_CALL_TO_CALL); conn =
	 * dataSource.getConnection(); selectByPropStat =
	 * conn.prepareCall(SELECT_BY_PROP_CALL_TO_CALL);
	 * selectByPropStat.execute(); selectByPropResultSet =
	 * selectByPropStat.getResultSet(); log.debug("CallableStatement : " +
	 * SELECT_BY_PROP_CALL_TO_CALL + " was execute!");
	 * 
	 * createItems(selectByPropResultSet, entitys);
	 * 
	 * } catch (SQLException e) { throw new DaoSystemException(e); } finally {
	 * try { closeQuaetly(selectByPropResultSet, selectByPropStat, conn); }
	 * catch (Exception e1) { throw new DaoSystemException(e1); } } return
	 * entitys; }
	 * 
	 * @Override public CopyOnWriteArraySet<ShowedItem>
	 * selectAllLimitCity(String column_name, String id, String limit, String
	 * city_id) throws NoSuchEntityException, NumberFormatException,
	 * DaoSystemException { CallableStatement selectByPropStat = null; ResultSet
	 * selectByPropResultSet = null; CopyOnWriteArraySet<ShowedItem> entitys =
	 * new CopyOnWriteArraySet<ShowedItem>(); Connection conn = null; try {
	 * 
	 * String SELECT_BY_PROP_CALL_TO_CALL =
	 * SELECT_LIMIT_PROP_CITY_CALL.replace("@" + COL_LIMIT, limit)
	 * .replaceAll("@" + CITY_ID_COL_NAME, city_id); SELECT_BY_PROP_CALL_TO_CALL
	 * = createPrepareCall(column_name, id, SELECT_BY_PROP_CALL_TO_CALL);
	 * 
	 * log.info("SQL to Execute : " + SELECT_BY_PROP_CALL_TO_CALL); conn =
	 * dataSource.getConnection(); selectByPropStat =
	 * conn.prepareCall(SELECT_BY_PROP_CALL_TO_CALL);
	 * selectByPropStat.execute(); selectByPropResultSet =
	 * selectByPropStat.getResultSet(); log.debug("CallableStatement : " +
	 * SELECT_BY_PROP_CALL_TO_CALL + " was execute!");
	 * 
	 * createItems(selectByPropResultSet, entitys);
	 * 
	 * } catch (SQLException e) { throw new DaoSystemException(e); } finally {
	 * try { closeQuaetly(selectByPropResultSet, selectByPropStat, conn); }
	 * catch (Exception e1) { throw new DaoSystemException(e1); } } return
	 * entitys; }
	 * 
	 * @Override public CopyOnWriteArraySet<ShowedItem> selectAllLimit(String
	 * column_name, String id, String limit) throws NoSuchEntityException,
	 * NumberFormatException, DaoSystemException { CallableStatement
	 * selectByPropStat = null; ResultSet selectByPropResultSet = null;
	 * CopyOnWriteArraySet<ShowedItem> entitys = new
	 * CopyOnWriteArraySet<ShowedItem>(); Connection conn = null; try {
	 * 
	 * String SELECT_BY_PROP_CALL_TO_CALL = SELECT_LIMIT_PROP_CALL.replace("@" +
	 * COL_LIMIT, limit); SELECT_BY_PROP_CALL_TO_CALL =
	 * createPrepareCall(column_name, id, SELECT_BY_PROP_CALL_TO_CALL);
	 * 
	 * log.info("SQL to Execute : " + SELECT_BY_PROP_CALL_TO_CALL); conn =
	 * dataSource.getConnection(); selectByPropStat =
	 * conn.prepareCall(SELECT_BY_PROP_CALL_TO_CALL);
	 * selectByPropStat.execute(); selectByPropResultSet =
	 * selectByPropStat.getResultSet(); log.debug("CallableStatement : " +
	 * SELECT_BY_PROP_CALL_TO_CALL + " was execute!");
	 * 
	 * createItems(selectByPropResultSet, entitys);
	 * 
	 * } catch (SQLException e) { throw new DaoSystemException(e); } finally {
	 * try { closeQuaetly(selectByPropResultSet, selectByPropStat, conn); }
	 * catch (Exception e1) { throw new DaoSystemException(e1); } } return
	 * entitys; }
	 * 
	 * @Override public CopyOnWriteArraySet<ShowedItem> selectAllByCity(String
	 * column_name, String id, String city_id) throws NoSuchEntityException,
	 * NumberFormatException, DaoSystemException { CallableStatement
	 * selectByPropStat = null; ResultSet selectByPropResultSet = null;
	 * CopyOnWriteArraySet<ShowedItem> entitys = new
	 * CopyOnWriteArraySet<ShowedItem>(); Connection conn = null; try {
	 * 
	 * String SELECT_BY_PROP_CALL_TO_CALL =
	 * SELECT_BY_PROP_CALL_CITY.replaceAll("@" + CITY_ID_COL_NAME, city_id);
	 * SELECT_BY_PROP_CALL_TO_CALL = createPrepareCall(column_name, id,
	 * SELECT_BY_PROP_CALL_TO_CALL);
	 * 
	 * log.info("SQL to Execute : " + SELECT_BY_PROP_CALL_TO_CALL); conn =
	 * dataSource.getConnection(); selectByPropStat =
	 * conn.prepareCall(SELECT_BY_PROP_CALL_TO_CALL);
	 * selectByPropStat.execute(); selectByPropResultSet =
	 * selectByPropStat.getResultSet(); log.debug("CallableStatement : " +
	 * SELECT_BY_PROP_CALL_TO_CALL + " was execute!");
	 * 
	 * createItems(selectByPropResultSet, entitys);
	 * 
	 * } catch (SQLException e) { log.warn("print error", e); throw new
	 * DaoSystemException(e); } finally { try {
	 * closeQuaetly(selectByPropResultSet, selectByPropStat, conn); } catch
	 * (Exception e1) { throw new DaoSystemException(e1); } } return entitys; }
	 * 
	 * @Override public CopyOnWriteArraySet<ShowedItem> selectAll(String
	 * column_name, String id) throws DaoSystemException, NoSuchEntityException,
	 * NumberFormatException { CallableStatement selectByPropStat = null;
	 * ResultSet selectByPropResultSet = null; CopyOnWriteArraySet<ShowedItem>
	 * entitys = new CopyOnWriteArraySet<ShowedItem>(); Connection conn = null;
	 * 
	 * try {
	 * 
	 * String SELECT_BY_PROP_CALL_TO_CALL = SELECT_BY_PROP_CALL;
	 * SELECT_BY_PROP_CALL_TO_CALL = createPrepareCall(column_name, id,
	 * SELECT_BY_PROP_CALL);
	 * 
	 * log.info("SQL to Execute : " + SELECT_BY_PROP_CALL_TO_CALL); conn =
	 * dataSource.getConnection(); selectByPropStat =
	 * conn.prepareCall(SELECT_BY_PROP_CALL_TO_CALL);
	 * selectByPropStat.execute(); selectByPropResultSet =
	 * selectByPropStat.getResultSet(); log.debug("CallableStatement : " +
	 * SELECT_BY_PROP_CALL_TO_CALL + " was execute!");
	 * 
	 * createItems(selectByPropResultSet, entitys);
	 * 
	 * } catch (SQLException e) { throw new DaoSystemException(e); } finally {
	 * try { closeQuaetly(selectByPropResultSet, selectByPropStat, conn); }
	 * catch (Exception e1) { throw new DaoSystemException(e1); } } return
	 * entitys; }
	 * 
	 */
	private void createItems(ResultSet selectResultSet, CopyOnWriteArraySet<ShowedItem> entitys)
			throws SQLException, NoSuchEntityException, DaoSystemException {
		while (selectResultSet.next()) {
			printItem(selectResultSet);

			ShowedItem item = createItem(selectResultSet);

			entitys.add(item);
		}
		log.debug("Paper proporties table\n" + entitys);
	}

	private ShowedItem createItem(ResultSet selectResultSet)
			throws SQLException, NoSuchEntityException, DaoSystemException {
		ShowedUser user = createUser(selectResultSet);
		int ent_id = selectResultSet.getInt(ENTITY_ID_COL_NAME);
		List<City> city_list = createCityList(selectResultSet);
		List<Section> sections_list = createSectionList(selectResultSet);
		List<String> url_list = createURLList(selectResultSet);
		List<Map> map_list = createMapList(selectResultSet);
		List<Adress> adress_list = createAdressList(selectResultSet);
		int [] activ = new int[1];
		SimpleWorkFactoryImpl work = createWork(selectResultSet, activ);
		List<Tag> tag_list = createTagList(selectResultSet);
		Rating rating = null;
		String imgage = createImage(selectResultSet);
		List<String> img_list = createImgList(ent_id);

		ShowedItem item = new SimpleShowedItem(ent_id,
				selectResultSet.getString(ENTITY_HEADER_COL_NAME),
				selectResultSet.getString(ENTITY_DESCRIPTION_COL_NAME), selectResultSet.getString(ENTITY_DATE_COL_NAME),
				user, city_list, sections_list, url_list, map_list, work.getWork(), adress_list, tag_list, rating,
				imgage, img_list, activ[0]);
		return item;
	}

	@Override
	public CopyOnWriteArraySet<ShowedItem> search(String str) throws DaoSystemException {
		throw new NullPointerException();
	}

	@SuppressWarnings("resource")
	private String createPrepareCall(String column_name, String id, String SELECT_BY_PROP_CALL_TO_CALL)
			throws NoSuchEntityException, DaoSystemException {
		if (column_name.equals("user")) {
			SELECT_BY_PROP_CALL_TO_CALL = SELECT_BY_PROP_CALL_TO_CALL.replaceAll("@" + COL_NAME, ENTITY_USER_COL_NAME)
					.replaceAll("@" + COL_VAL, id).replaceAll("@" + OPERATION, OPERATION);
		} else if (column_name.equals("tag")) {
			SELECT_BY_PROP_CALL_TO_CALL = SELECT_BY_PROP_CALL_TO_CALL.replaceAll("@" + COL_NAME, TAG_TAG_ID_COL_NAME)
					.replaceAll("@" + COL_VAL, id).replaceAll("@" + OPERATION, OPERATION);
		} else if (column_name.equals("entity.entity_id")) {
			SELECT_BY_PROP_CALL_TO_CALL = SELECT_BY_PROP_CALL_TO_CALL.replaceAll("@" + COL_NAME, "entity.entity_id")
					.replaceAll("@" + COL_VAL, id).replaceAll("@" + OPERATION, OPERATION);
		} else if (column_name.equals("city")) {
			SELECT_BY_PROP_CALL_TO_CALL = SELECT_BY_PROP_CALL_TO_CALL.replaceAll("@" + COL_NAME, CITY_ID_COL_NAME)
					.replaceAll("@" + COL_VAL, id).replaceAll("@" + OPERATION, OPERATION);
		} else if (column_name.equals("date")) {
			String oper = "' BETWEEN '";
			Calendar ret = Calendar.getInstance();
			ret.setTimeInMillis(Long.parseLong(id));
			Formatter month = new Formatter().format("%1$02d", (1 + ret.get(Calendar.MONTH)));
			String id1 = "\'\\\\'" + ret.get(Calendar.YEAR) + "" + month + "" + ret.get(Calendar.DATE)
					+ "000000\\\\' AND \\\\'" + ret.get(Calendar.YEAR) + "" + month + "" + ret.get(Calendar.DATE)
					+ "235959\\\\'\'";
			month.close();
			SELECT_BY_PROP_CALL_TO_CALL = SELECT_BY_PROP_CALL_TO_CALL.replaceAll("@" + COL_NAME, ENTITY_DATE_COL_NAME)
					.replaceAll("@" + OPERATION, oper).replaceAll("@" + COL_VAL, id1);
		} else if (column_name.equals("section")) {
			String oper = "' in '";
			Calendar ret = Calendar.getInstance();
			ret.setTimeInMillis(Long.parseLong(id));
			String id1 = "'(";
			List<Section> s = sectionDao.getSectionById(new Integer(id)).getAllChilds();
			int i = s.size();
			for (Section id0 : s) {
				id1 += id0.getSectionId();
				if (--i > 0)
					id1 += ", ";
			}
			id1 += ")'";
			SELECT_BY_PROP_CALL_TO_CALL = SELECT_BY_PROP_CALL_TO_CALL.replaceAll("@" + COL_NAME, SECTION_IDs_COL_NAME)
					.replaceAll("@" + OPERATION, oper).replaceAll("@" + COL_VAL, id1);
		} else
			throw new NoSuchEntityException("Properties " + column_name + " or value " + id + " not found");
		return SELECT_BY_PROP_CALL_TO_CALL;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setSectionDao(SectionDao sectionDao) {
		this.sectionDao = sectionDao;
	}

	private List<City> createCityList(ResultSet selectAllResultSet)
			throws SQLException, NoSuchEntityException, NumberFormatException, DaoSystemException {
		List<City> city_list = new LinkedList<>();
		String cit = selectAllResultSet.getString(CITY_IDs_COL_NAME);
		if (cit == null)
			return city_list;
		String[] cutyIds = selectAllResultSet.getString(CITY_IDs_COL_NAME).split(",");

		for (String cityId : cutyIds) {
			city_list.add(cityDao.getCityById(new Integer(cityId)));
		}

		log.debug("City to add:");
		for (City c : city_list)
			log.debug("City list to showed item : " + c.getName());
		return city_list;
	}

	private List<Section> createSectionList(ResultSet selectAllResultSet)
			throws SQLException, NoSuchEntityException, DaoSystemException {
		List<Section> sections_list = new LinkedList<Section>();

		String section = selectAllResultSet.getString(SECTION_IDs_COL_NAME);
		if (section == null)
			return sections_list;
		String[] sectionsIds = section.split(",");

		for (String sectionId : sectionsIds) {
			sections_list.add(sectionDao.getSectionById(new Integer(sectionId)));
		}

		log.debug("Sections to add:");
		for (Section c : sections_list)
			log.debug("Sections list to showed item : " + c.getSectionName());
		return sections_list;
	}

	private List<String> createURLList(ResultSet selectByIdResultSet) throws SQLException {
		List<String> url_list = new LinkedList<>();
		String urls = selectByIdResultSet.getString(URL_LINK_COL_NAME);
		if (urls == null)
			return url_list;
		for (String url : urls.split(",")) {
			url_list.add(url);
		}

		log.debug("URL to add:");
		for (String url : url_list)
			log.debug("URLs list to showed item : " + url);
		return url_list;
	}

	private List<Map> createMapList(ResultSet selectByIdResultSet) throws SQLException {
		List<Map> map_list = new LinkedList<>();

		if (selectByIdResultSet.getString(MAP_LINK_COL_NAME) == null
				|| selectByIdResultSet.getString(MAP_LINK_COL_NAME).equals(""))
			return map_list;
		for (String url : selectByIdResultSet.getString(MAP_LINK_COL_NAME).split(";")) {
			log.error("MAP to from request" + url);
			if (!url.startsWith(",,"))
				// map_list.add(new
				// SimpleMapImpl(selectByIdResultSet.getInt(ENTITY_ID_COL_NAME),
				// Double.parseDouble(url.split(",")[0]),
				// Double.parseDouble(url.split(",")[1])));

				map_list.add(new SimpleMapImpl(Integer.parseInt(url.split(",")[2]),
						Double.parseDouble(url.split(",")[0]), Double.parseDouble(url.split(",")[1])));
		}

		log.debug("MAP to add:");
		for (Map url : map_list)
			log.debug("Map list to showed item : " + url);
		return map_list;
	}

	private List<Adress> createAdressList(ResultSet selectByIdResultSet) throws SQLException {
		List<Adress> adress_list = new LinkedList<>();

		if (selectByIdResultSet.getString(ADRESS_ADRESS_COL_NAME) == null)
			return null;
		for (String adres : selectByIdResultSet.getString(ADRESS_ADRESS_COL_NAME).split(";")) {
			String[] adr = adres.split("@");
			log.trace("Address from result set: " + adres + " " + Arrays.toString(adr));

			if (adr.length == 2)
				adress_list.add(new SimpleAdressImpl(adr[0], adr[1]));
			if (adr.length == 1)
				adress_list.add(new SimpleAdressImpl(adr[0], ""));

		}

		log.debug("Adress to add:");
		for (Adress url : adress_list)
			log.debug("URLs list to showed item : " + url);

		return adress_list;
	}

	private List<Tag> createTagList(ResultSet selectByIdResultSet) throws SQLException {
		List<Tag> tag_list = new LinkedList<>();

		if (selectByIdResultSet.getString(TAG_TAG_NAME_COL_NAME) == null)
			return null;

		for (String tag : selectByIdResultSet.getString(TAG_TAG_NAME_COL_NAME).split(";")) {
			String[] tag_fields = tag.split(",");
			tag_list.add(new SimpleTagImpl(Integer.parseInt(tag_fields[0]), tag_fields[1]));
		}

		log.debug("Tags to add:");
		for (Tag tag : tag_list)
			log.debug("URLs list to showed item : " + tag);

		return tag_list;
	}

	private List<String> createImgList(int entityId) throws SQLException, NoSuchEntityException, DaoSystemException {
		log.trace("imgDao to execute request : "  + imgDao);
		log.trace("id to get request from img dao "  + entityId);
	 		return imgDao.getImgListByEntity(entityId);
	}

	private String createImage(ResultSet selectByIdResultSet) throws SQLException {
		String image = null;
		if (selectByIdResultSet.getString(IMG_COL_NAME) == null)
			return null;
		image = selectByIdResultSet.getString(IMG_COL_NAME);
		log.debug("Img  to showed item : " + image);
		return image;
	}

	private SimpleWorkFactoryImpl createWork(ResultSet selectByIdResultSet, int[] b) throws SQLException {
		SimpleWorkFactoryImpl work = new SimpleWorkFactoryImpl();

		
		if (selectByIdResultSet.getString(WORK_COL_NAME) == null)
			return work;

		int action = -1;
		for (String adres : selectByIdResultSet.getString(WORK_COL_NAME).split(";")) {
			String[] adress = adres.split(",");

			String date_from = adress[0];
			String date_to = adress[1];
			int month_from = Integer.parseInt(adress[2]);
			int month_to = Integer.parseInt(adress[3]);
			int day_from = Integer.parseInt(adress[4]);
			int day_to = Integer.parseInt(adress[5]);
			String time_from = adress[6];
			String time_to = adress[7];
			
			 
			int activ= 0; 
			int future= 1;
			int archiv= 2; 
			int now=3; 
			
			Calendar curr = Calendar.getInstance();
			curr.setFirstDayOfWeek(Calendar.MONDAY); 
			curr.setTimeInMillis(System.currentTimeMillis());

			String[] dateFrom = adress[0].split("-"); 
			String[] dateTo = adress[1].split("-"); 
			String[] timefrom = adress[6].split(":"); 
			String[] timeTo = adress[7].split(":"); 

			int dayOfWeek = curr.get(Calendar.DAY_OF_WEEK) - 1;
			if (dayOfWeek == 0)
				dayOfWeek = 7;
			
			if(curr.get(Calendar.YEAR )<=Integer.parseInt(dateTo[2]) &&
					curr.get(Calendar.MONTH)+1 <=Integer.parseInt(dateTo[1]) &&
					curr.get(Calendar.DAY_OF_MONTH )<=Integer.parseInt(dateTo[0]) &&
					curr.get(Calendar.HOUR_OF_DAY )<=Integer.parseInt(timeTo[0])&& 
					curr.get(Calendar.MINUTE )<=Integer.parseInt(timeTo[1])&&
							(curr.get(Calendar.MONTH )+1)>=month_from&& 
									dayOfWeek>=day_from&& 
					
					
					
					
					
					curr.get(Calendar.YEAR )>=Integer.parseInt(dateFrom[2]) &&
					curr.get(Calendar.MONTH )+1>=Integer.parseInt(dateFrom[1]) && 
					curr.get(Calendar.DAY_OF_MONTH )>=Integer.parseInt(dateFrom[0]) && 
					curr.get(Calendar.HOUR_OF_DAY )>=Integer.parseInt(timefrom[0])&& 
					curr.get(Calendar.MINUTE )>=Integer.parseInt(timefrom[1])&&
					
 
							(curr.get(Calendar.MONTH )+1)<=month_to&&  
									dayOfWeek<=day_to
					
					
					)
				action=action>now?action:now;
			else if(		curr.get(Calendar.YEAR )>=Integer.parseInt(dateTo[2]) &&
					curr.get(Calendar.MONTH+1 )>=Integer.parseInt(dateTo[1]) &&
					curr.get(Calendar.DAY_OF_MONTH )> Integer.parseInt(dateTo[0]))
				 	action=action>archiv?action:archiv;
			else if(curr.get(Calendar.YEAR )<Integer.parseInt(dateTo[2]))
				action=activ>archiv?action:activ;
			else if (curr.get(Calendar.YEAR )==Integer.parseInt(dateTo[2]) && curr.get(Calendar.MONTH )+1<Integer.parseInt(dateTo[1]))
				action=activ>archiv?action:activ;
			else if (curr.get(Calendar.YEAR )==Integer.parseInt(dateTo[2]) && curr.get(Calendar.MONTH )+1==Integer.parseInt(dateTo[1]) && curr.get(Calendar.DAY_OF_MONTH )<Integer.parseInt(dateTo[0]))
				action=activ>archiv?action:activ;
			else if(curr.get(Calendar.YEAR )<Integer.parseInt(dateFrom[2]))
				action=activ>future?action:future;
			else if(curr.get(Calendar.YEAR )==Integer.parseInt(dateTo[2]) && curr.get(Calendar.MONTH )+1<Integer.parseInt(dateFrom[1])) 
				action=activ>future?action:future;
			else if(curr.get(Calendar.YEAR )==Integer.parseInt(dateTo[2]) && curr.get(Calendar.MONTH )+1==Integer.parseInt(dateTo[1]) && curr.get(Calendar.DAY_OF_MONTH )<Integer.parseInt(dateFrom[0]))
				action=activ>future?action:future;
			 


 
			
			
			work.addDate(date_from, date_to, month_from, month_to,
					day_from, day_to, time_from, time_to);
		}
		b[0]=action;
		return work;
	}

	private ShowedUser createUser(ResultSet selectAllResultSet) throws SQLException {
		ShowedUser user = new SimpleShowedUser(selectAllResultSet.getString(USER_LOGIN_COL_NAME),
				selectAllResultSet.getString(USER_FIRST_NAME_COL_NAME),
				selectAllResultSet.getString(USER_LAST_NAME_COL_NAME),
				selectAllResultSet.getString(USER_EMAIL_COL_NAME), selectAllResultSet.getInt(USER_ID_COL_NAME));
		return user;
	}

	private Rating createReting(ResultSet selectAllResultSet) throws SQLException {

		String res = selectAllResultSet.getString(RATING_COL_NAME);
		if (res == null)
			return null;
		String[] retings = res.split(",");
		Rating reting = new SimpleRatingImpl(Integer.parseInt(retings[0]), Integer.parseInt(retings[1]));
		return reting;
	}

	private void printItem(ResultSet selectAllResultSet) throws SQLException {
		log.debug("result set item " + selectAllResultSet);
		log.debug("result " + ENTITY_ID_COL_NAME + " set item " + selectAllResultSet.getInt(ENTITY_ID_COL_NAME));
		log.debug("result " + ENTITY_HEADER_COL_NAME + " set item "
				+ selectAllResultSet.getString(ENTITY_HEADER_COL_NAME));
		log.debug("result " + ENTITY_DESCRIPTION_COL_NAME + " set item "
				+ selectAllResultSet.getString(ENTITY_DESCRIPTION_COL_NAME));
		log.debug("result " + ENTITY_DATE_COL_NAME + " set item "
				+ selectAllResultSet.getTimestamp(ENTITY_DATE_COL_NAME));

		log.debug("result " + USER_FIRST_NAME_COL_NAME + " set item "
				+ selectAllResultSet.getString(USER_FIRST_NAME_COL_NAME));
		log.debug("result " + USER_LAST_NAME_COL_NAME + " set item "
				+ selectAllResultSet.getString(USER_LAST_NAME_COL_NAME));
		log.debug("result " + USER_EMAIL_COL_NAME + " set item " + selectAllResultSet.getString(USER_EMAIL_COL_NAME));
		log.debug("result " + USER_LOGIN_COL_NAME + " set item " + selectAllResultSet.getString(USER_LOGIN_COL_NAME));
		log.debug("result " + USER_ID_COL_NAME + " set item " + selectAllResultSet.getInt(USER_ID_COL_NAME));

		log.debug("result " + CITY_IDs_COL_NAME + " set item " + selectAllResultSet.getString(CITY_IDs_COL_NAME));
		log.debug("result " + CITY_NAMEs_COL_NAME + " set item " + selectAllResultSet.getString(CITY_NAMEs_COL_NAME));

		log.debug("result " + SECTION_IDs_COL_NAME + " set item " + selectAllResultSet.getString(SECTION_IDs_COL_NAME));
	}

}
