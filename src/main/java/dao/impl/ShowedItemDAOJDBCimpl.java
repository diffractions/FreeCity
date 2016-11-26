package dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
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
import entity.impl.SimpleMapImpl;
import entity.impl.SimpleRatingImpl;
import entity.impl.SimpleShowedItem;
import entity.impl.SimpleShowedUser;
import entity.impl.SimpleTagImpl;
import entity.work.impl.SimpleWorkFactoryImpl;

import static utils.JBDCUtil.closeQuaetly;

public class ShowedItemDAOJDBCimpl implements ShowedItemDao {

	public static Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	@SuppressWarnings("unused")
	private final static String ENTITY_TABL_NAME = "freecity.entity";
	private final static String ENTITY_ID_COL_NAME = "entity_id";
	private final static String ENTITY_HEADER_COL_NAME = "header";
	private final static String ENTITY_DESCRIPTION_COL_NAME = "description";
	private final static String ENTITY_DATE_COL_NAME = "entity.date";
	@SuppressWarnings("unused")
	private final static String ENTITY_USER_COL_NAME = "entity.user_id";
	private final static String ENTITY_STATUS_COL_NAME = "entity.status";

	@SuppressWarnings("unused")
	private final static String USER_TABL_NAME = "freecity.user";
	private final static String USER_ID_COL_NAME = "user_id";
	private final static String USER_FIRST_NAME_COL_NAME = "first_name";
	private final static String USER_LAST_NAME_COL_NAME = "last_name";
	private final static String USER_EMAIL_COL_NAME = "email";
	private final static String USER_LOGIN_COL_NAME = "login";

	@SuppressWarnings("unused")
	private final static String CITY_TABL_NAME = "freecity.city";
	@SuppressWarnings("unused")
	private final static String CITY_ID_COL_NAME = "city.city_id";
	@SuppressWarnings("unused")
	private final static String CITY_NAME_COL_NAME = "name";
	private final static String CITY_IDs_COL_NAME = "city_ids";
	private final static String CITY_NAMEs_COL_NAME = "city_names";

	@SuppressWarnings("unused")
	private final static String SECTION_TABL_NAME = "freecity.section";
	private final static String SECTION_IDs_COL_NAME = "section_id";
	@SuppressWarnings("unused")
	private final static String SECTION_NAME_COL_NAME = "name";

	@SuppressWarnings("unused")
	private final static String URL_TABL_NAME = "freecity.url";
	@SuppressWarnings("unused")
	private final static String URL_IDs_COL_NAME = "entity_id";
	private final static String URL_LINK_COL_NAME = "url_link";

	@SuppressWarnings("unused")
	private final static String MAP_TABL_NAME = "freecity.map";
	@SuppressWarnings("unused")
	private final static String MAP_IDs_COL_NAME = "entity_id";
	private final static String MAP_LINK_COL_NAME = "map_url";

	@SuppressWarnings("unused")
	private final static String TAG_TABL_NAME = "freecity.tags";
	@SuppressWarnings("unused")
	private final static String TAG_TAG_ID_COL_NAME = "tags.tag_id";
	private final static String TAG_TAG_NAME_COL_NAME = "tag_name";

	@SuppressWarnings("unused")
	private final static String RATING_TABL_NAME = "freecity.rating";
	private final static String RATING_COL_NAME = "rating";
	@SuppressWarnings("unused")
	private final static String RATING_TAG_ID_COL_NAME = "rating.voice";
	@SuppressWarnings("unused")
	private final static String RATING_TAG_NAME_COL_NAME = "rating.stars";

	private final static String ADRESS_ADRESS_COL_NAME = "adress";
	private final static String WORK_COL_NAME = "work_date";
	@SuppressWarnings("unused")
	private final static String IMG_LIST_COL_NAME = "img_list";
	private final static String IMG_COL_NAME = "img";

	@SuppressWarnings("unused")
	private final static String ID = "ID";
//	private final static String SELECT_ALL_CALL = "{call selectAll()}";
	private final static String SELECT_BY_ID_CALL = "{call selectById(?)}";
	@SuppressWarnings("unused")
	private final static String COL_NAME = "column_name ";
	@SuppressWarnings("unused")
	private final static String COL_VAL = "column_val ";
	@SuppressWarnings("unused")
	private final static String COL_LIMIT = "column_limit ";
	@SuppressWarnings("unused")
	private final static String OPERATION = "\' = \'";

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

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setSectionDao(SectionDao sectionDao) {
		this.sectionDao = sectionDao;
	}

	@Override
	public CopyOnWriteArraySet<ShowedItem> executeSelectAll(String city, String date, String section,
			String section_view, String user, String from, String limit, String tag, String action, int[] count)
			throws NoSuchEntityException, NumberFormatException, DaoSystemException {

		Connection conn = null;
		CallableStatement selectAllStat = null;
		ResultSet selectAllResultSet = null;
		CopyOnWriteArraySet<ShowedItem> entitys = new CopyOnWriteArraySet<ShowedItem>();
		StringBuilder CONDITIONS11 = new StringBuilder("");
		StringBuilder CONDITIONS22 = new StringBuilder("");
		StringBuilder CONDITIONS33 = new StringBuilder("");
		try {
			conn = dataSource.getConnection();
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
				@SuppressWarnings("resource")
				Formatter month = new Formatter().format("%1$02d", (1 + ret.get(Calendar.MONTH)));
				@SuppressWarnings("resource")
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
							+ "-" + ret.get(Calendar.DAY_OF_MONTH);

					int dayOfWeek = ret.get(Calendar.DAY_OF_WEEK) - 1;
					if (dayOfWeek == 0)
						dayOfWeek = 7;
					actions2 = "\' and date.date_to >= \'" + ret.get(Calendar.YEAR) + "-"
							+ (ret.get(Calendar.MONTH) + 1) + "-" + ret.get(Calendar.DAY_OF_MONTH)
							+ "\' and date.month_from  <=\'" + (ret.get(Calendar.MONTH) + 1)
							+ "\' and date.month_to  >=\'" + (ret.get(Calendar.MONTH) + 1)
							+ "\' and date.day_from  <=\'" + (dayOfWeek) + "\' and date.day_to  >=\'" + (dayOfWeek)
							+ "\' and date.time_from  <=\'" + (ret.get(Calendar.HOUR_OF_DAY)) + ":"
							+ (ret.get(Calendar.MINUTE)) + "\' and date.time_to  >=\'" + (ret.get(Calendar.HOUR_OF_DAY))
							+ ":" + (ret.get(Calendar.MINUTE) + "\'");
				} else if (action.equalsIgnoreCase("future")) {
					actions1 = " date.date_from > \'" + ret.get(Calendar.YEAR) + "-" + (ret.get(Calendar.MONTH) + 1)
							+ "-" + ret.get(Calendar.DAY_OF_MONTH) + "\'";
				} else if (action.equalsIgnoreCase("archive")) {
					actions1 = " date.date_to < \'" + ret.get(Calendar.YEAR) + "-" + (ret.get(Calendar.MONTH) + 1) + "-"
							+ ret.get(Calendar.DAY_OF_MONTH) + "\'";
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

			log.trace("CONDITIONS11 = " + CONDITIONS11.toString());
			log.trace("CONDITIONS22 = " + CONDITIONS22.toString());
			log.trace("CONDITIONS33 = " + CONDITIONS33.toString());

			if (count != null)
				count[0] = selectAllStat.getInt(4);
			selectAllResultSet = selectAllStat.getResultSet();
			log.trace("CallableStatement : " + SELECT_BY_PROP_CALL_TO_CALL + " was execute!");

			createItems(selectAllResultSet, entitys);

		} catch (SQLException e) {
			log.error("Error description", e);
			throw new DaoSystemException(e);
		} finally {
			try {
				closeQuaetly(selectAllResultSet, selectAllStat, conn);
			} catch (Exception e1) {
				log.error("Error description",e1);
				throw new DaoSystemException(e1);
			}
		}

		return entitys;
	}

	@Override
	public ShowedItem selectById(int id) throws DaoSystemException, NoSuchEntityException {

		CallableStatement selectByIdStat = null;
		ResultSet selectByIdResultSet = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			selectByIdStat = conn.prepareCall(SELECT_BY_ID_CALL);
			selectByIdStat.setInt(1, id);
			selectByIdStat.execute();
			selectByIdResultSet = selectByIdStat.getResultSet();
			log.debug("CallableStatement : " + SELECT_BY_ID_CALL + " was execute!");

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
				closeQuaetly(selectByIdResultSet, selectByIdStat, conn);
			} catch (Exception e1) {
				throw new DaoSystemException(e1);
			}
		}
	}

//	@Override
//	public CopyOnWriteArraySet<ShowedItem> selectAll()
//			throws DaoSystemException, NoSuchEntityException, NumberFormatException {
//		CallableStatement selectAllStat = null;
//		ResultSet selectAllResultSet = null;
//		CopyOnWriteArraySet<ShowedItem> entitys = new CopyOnWriteArraySet<ShowedItem>();
//		Connection conn = null;
//		try {
//			conn = dataSource.getConnection();
//
//			log.info("SQL to Execute : " + SELECT_ALL_CALL);
//			selectAllStat = conn.prepareCall(SELECT_ALL_CALL);
//			selectAllStat.execute();
//			selectAllResultSet = selectAllStat.getResultSet();
//			log.debug("CallableStatement : " + SELECT_ALL_CALL + " was execute!");
//
//			createItems(selectAllResultSet, entitys);
//
//		} catch (SQLException e) {
//			log.error("Error description", e);
//			throw new DaoSystemException(e);
//		} finally {
//			try {
//				closeQuaetly(selectAllResultSet, selectAllStat, conn);
//			} catch (Exception e1) {
//				log.error("Error description", e1);
//				throw new DaoSystemException(e1);
//			}
//		}
//
//		return entitys;
//	}

	private void createItems(ResultSet selectResultSet, CopyOnWriteArraySet<ShowedItem> entitys)
			throws SQLException, NoSuchEntityException, DaoSystemException {
		while (selectResultSet.next()) {
			printItem(selectResultSet); 
			ShowedItem item = createItem(selectResultSet); 
			entitys.add(item);
		} 
	}

	private ShowedItem createItem(ResultSet selectResultSet)
			throws SQLException, NoSuchEntityException, DaoSystemException {
		ShowedUser user = createUser(selectResultSet);
		int ent_id = selectResultSet.getInt(ENTITY_ID_COL_NAME);
		int status = selectResultSet.getInt(ENTITY_STATUS_COL_NAME);
		List<City> city_list = createCityList(selectResultSet);
		List<Section> sections_list = createSectionList(selectResultSet);
		List<String> url_list = createURLList(selectResultSet);
		List<Map> map_list = createMapList(selectResultSet);
		List<Adress> adress_list = createAdressList(selectResultSet);
		int[] activ = new int[1];
		SimpleWorkFactoryImpl work = createWork(selectResultSet, activ);
		List<Tag> tag_list = createTagList(selectResultSet);
		Rating rating = createReting(selectResultSet);
		String imgage = createImage(selectResultSet);
		List<String> img_list = createImgList(ent_id);

		ShowedItem item = new SimpleShowedItem(ent_id, selectResultSet.getString(ENTITY_HEADER_COL_NAME),
				selectResultSet.getString(ENTITY_DESCRIPTION_COL_NAME), selectResultSet.getString(ENTITY_DATE_COL_NAME),
				user, city_list, sections_list, url_list, map_list, work.getWork(), adress_list, tag_list, rating,
				imgage, img_list, activ[0], status);
		return item;
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

		log.trace("City to add:");
		for (City c : city_list)
			log.trace("City list to showed item : " + c.getName());
		
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

		log.trace("Sections to add:");
		for (Section c : sections_list)
			log.trace("Sections list to showed item : " + c.getSectionName());
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

		log.trace("URL to add:");
		for (String url : url_list)
			log.trace("URLs list to showed item : " + url);
		
		return url_list;
	}

	private List<Map> createMapList(ResultSet selectByIdResultSet) throws SQLException {
		List<Map> map_list = new LinkedList<>();

		if (selectByIdResultSet.getString(MAP_LINK_COL_NAME) == null
				|| selectByIdResultSet.getString(MAP_LINK_COL_NAME).equals(""))
			return map_list;
		for (String url : selectByIdResultSet.getString(MAP_LINK_COL_NAME).split(";")) {
			
			log.trace("MAP to from request" + url);
			if (!url.startsWith(",,")) 
				map_list.add(new SimpleMapImpl(Integer.parseInt(url.split(",")[2]),
						Double.parseDouble(url.split(",")[0]), Double.parseDouble(url.split(",")[1])));
		}

		log.trace("MAP to add:");
		for (Map url : map_list)
			log.trace("Map list to showed item : " + url);
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

		log.trace("Adress to add:");
		for (Adress url : adress_list)
			log.trace("URLs list to showed item : " + url);

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

		log.trace("Tags to add:");
		for (Tag tag : tag_list)
			log.trace("URLs list to showed item : " + tag);

		return tag_list;
	}

	private List<String> createImgList(int entityId) throws SQLException, NoSuchEntityException, DaoSystemException {
		log.trace("imgDao to execute request : " + imgDao);
		log.trace("id to get request from img dao " + entityId);
		return imgDao.getImgListByEntity(entityId);
	}

	private String createImage(ResultSet selectByIdResultSet) throws SQLException {
		String image = null;
		if (selectByIdResultSet.getString(IMG_COL_NAME) == null)
			return null;
		image = selectByIdResultSet.getString(IMG_COL_NAME);
		log.trace("Img  to showed item : " + image);
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

			int activ = 0;
			int future = 1;
			int archiv = 2;
			int now = 3;

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

			if (curr.get(Calendar.YEAR) <= Integer.parseInt(dateTo[2])
					&& curr.get(Calendar.MONTH) + 1 <= Integer.parseInt(dateTo[1])
					&& curr.get(Calendar.DAY_OF_MONTH) <= Integer.parseInt(dateTo[0])
					&& curr.get(Calendar.HOUR_OF_DAY) <= Integer.parseInt(timeTo[0]) &&

					((curr.get(Calendar.MINUTE) <= Integer.parseInt(timeTo[1])
							|| (curr.get(Calendar.HOUR_OF_DAY) < Integer.parseInt(timeTo[0]))))
					&&

					(curr.get(Calendar.MONTH) + 1) >= month_from && dayOfWeek >= day_from &&

					curr.get(Calendar.YEAR) >= Integer.parseInt(dateFrom[2])
					&& curr.get(Calendar.MONTH) + 1 >= Integer.parseInt(dateFrom[1])
					&& curr.get(Calendar.DAY_OF_MONTH) >= Integer.parseInt(dateFrom[0])
					&& curr.get(Calendar.HOUR_OF_DAY) >= Integer.parseInt(timefrom[0])
					&& curr.get(Calendar.MINUTE) >= Integer.parseInt(timefrom[1]) &&

					(curr.get(Calendar.MONTH) + 1) <= month_to && dayOfWeek <= day_to

			)
				action = action > now ? action : now;
			else if (curr.get(Calendar.YEAR) >= Integer.parseInt(dateTo[2])
					&& curr.get(Calendar.MONTH + 1) >= Integer.parseInt(dateTo[1])
					&& curr.get(Calendar.DAY_OF_MONTH) > Integer.parseInt(dateTo[0]))
				action = action > archiv ? action : archiv;
			else if (curr.get(Calendar.YEAR) < Integer.parseInt(dateTo[2]))
				action = activ > archiv ? action : activ;
			else if (curr.get(Calendar.YEAR) == Integer.parseInt(dateTo[2])
					&& curr.get(Calendar.MONTH) + 1 < Integer.parseInt(dateTo[1]))
				action = activ > archiv ? action : activ;
			else if (curr.get(Calendar.YEAR) == Integer.parseInt(dateTo[2])
					&& curr.get(Calendar.MONTH) + 1 == Integer.parseInt(dateTo[1])
					&& curr.get(Calendar.DAY_OF_MONTH) < Integer.parseInt(dateTo[0]))
				action = activ > archiv ? action : activ;
			else if (curr.get(Calendar.YEAR) < Integer.parseInt(dateFrom[2]))
				action = activ > future ? action : future;
			else if (curr.get(Calendar.YEAR) == Integer.parseInt(dateTo[2])
					&& curr.get(Calendar.MONTH) + 1 < Integer.parseInt(dateFrom[1]))
				action = activ > future ? action : future;
			else if (curr.get(Calendar.YEAR) == Integer.parseInt(dateTo[2])
					&& curr.get(Calendar.MONTH) + 1 == Integer.parseInt(dateTo[1])
					&& curr.get(Calendar.DAY_OF_MONTH) < Integer.parseInt(dateFrom[0]))
				action = activ > future ? action : future;
 

			work.addDate(date_from, date_to, month_from, month_to, day_from, day_to, time_from, time_to);
		}
		b[0] = action;
		return work;
	}

	private ShowedUser createUser(ResultSet selectAllResultSet) throws SQLException {
		ShowedUser user = new SimpleShowedUser(selectAllResultSet.getString(USER_LOGIN_COL_NAME), selectAllResultSet.getString(USER_FIRST_NAME_COL_NAME), selectAllResultSet.getString(USER_LAST_NAME_COL_NAME), selectAllResultSet.getString(USER_EMAIL_COL_NAME), selectAllResultSet.getInt(USER_ID_COL_NAME));
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
		log.debug("result " + ENTITY_HEADER_COL_NAME + " set item " + selectAllResultSet.getString(ENTITY_HEADER_COL_NAME));
		log.debug("result " + ENTITY_DESCRIPTION_COL_NAME + " set item " + selectAllResultSet.getString(ENTITY_DESCRIPTION_COL_NAME));
		log.debug("result " + ENTITY_DATE_COL_NAME + " set item " + selectAllResultSet.getTimestamp(ENTITY_DATE_COL_NAME));

		log.debug("result " + USER_FIRST_NAME_COL_NAME + " set item " + selectAllResultSet.getString(USER_FIRST_NAME_COL_NAME));
		log.debug("result " + USER_LAST_NAME_COL_NAME + " set item " + selectAllResultSet.getString(USER_LAST_NAME_COL_NAME));
		log.debug("result " + USER_EMAIL_COL_NAME + " set item " + selectAllResultSet.getString(USER_EMAIL_COL_NAME));
		log.debug("result " + USER_LOGIN_COL_NAME + " set item " + selectAllResultSet.getString(USER_LOGIN_COL_NAME));
		log.debug("result " + USER_ID_COL_NAME + " set item " + selectAllResultSet.getInt(USER_ID_COL_NAME));

		log.debug("result " + CITY_IDs_COL_NAME + " set item " + selectAllResultSet.getString(CITY_IDs_COL_NAME));
		log.debug("result " + CITY_NAMEs_COL_NAME + " set item " + selectAllResultSet.getString(CITY_NAMEs_COL_NAME));

		log.debug("result " + SECTION_IDs_COL_NAME + " set item " + selectAllResultSet.getString(SECTION_IDs_COL_NAME));
	}

	@Override
	public CopyOnWriteArraySet<ShowedItem> executeSearch(String city, String from, String limit, int[] count,
			String serach) throws NoSuchEntityException, NumberFormatException, DaoSystemException {

		CallableStatement selectAllStat = null;
		ResultSet selectAllResultSet = null;
		Connection conn = null;
		CopyOnWriteArraySet<ShowedItem> entitys = new CopyOnWriteArraySet<ShowedItem>();
		StringBuilder CONDITIONS11 = new StringBuilder("");
		StringBuilder CONDITIONS22 = new StringBuilder("");
		StringBuilder CONDITIONS33 = new StringBuilder("");
		try {
			conn = dataSource.getConnection();

			log.trace("city = " + city);
			log.trace("from = " + from);
			log.trace("limit = " + limit);
			log.trace("serach = " + serach);

			if (city != null && !city.equals("") && !city.equals("-1")) {

				if (CONDITIONS11.toString().equals(""))
					CONDITIONS11.append(" where ");
				else
					CONDITIONS11.append(" and ");
				CONDITIONS11.append(" city.city_id = " + city);
			}

			if (CONDITIONS11.toString().equals(""))
				CONDITIONS11.append(" where ");
			else
				CONDITIONS11.append(" and ");

			CONDITIONS11.append(" section.section_view in(1,2,3,4,5,6,7,8,9)");

			if (limit != null && !limit.equals("")) {

				CONDITIONS22.append(" limit ");
				if (from != null && !from.equals("") && !from.equals("0")) {
					CONDITIONS22.append(from + ", ");
				}
				CONDITIONS22.append(limit);
			}
			if (serach != null && !serach.equals("")) {
				if (CONDITIONS11.toString().equals(""))
					CONDITIONS11.append(" where ");
				else
					CONDITIONS11.append(" and ");

				serach = serach.trim();
				CONDITIONS11.append("(header like '%" + serach + "%' or description like '%" + serach + "%')"); 
			}

			int status = 4;
			CONDITIONS11.append("and entity.status >=" + status);
 
			String SELECT_BY_PROP_CALL_TO_CALL = EXECUTE_SELECT_ALL_CALL;

			log.info("SQL to Execute : " + SELECT_BY_PROP_CALL_TO_CALL);
			selectAllStat = conn.prepareCall(SELECT_BY_PROP_CALL_TO_CALL);

			selectAllStat.setNString(1, CONDITIONS11.toString());
			selectAllStat.setNString(2, CONDITIONS22.toString());
			selectAllStat.setNString(3, CONDITIONS33.toString());
			selectAllStat.registerOutParameter(4, java.sql.Types.INTEGER);

			log.trace("CONDITIONS11 = " + CONDITIONS11.toString());
			log.trace("CONDITIONS22 = " + CONDITIONS22.toString());
			log.trace("CONDITIONS33 = " + CONDITIONS33.toString());

			selectAllStat.execute();

			if (count != null)
				count[0] = selectAllStat.getInt(4);
			
			selectAllResultSet = selectAllStat.getResultSet();
			log.trace("CallableStatement : " + SELECT_BY_PROP_CALL_TO_CALL + " was execute!");

			if (count[0] > 0) { 
				createItems(selectAllResultSet, entitys);
			}

		} catch (SQLException e) {
			log.error("Error description", e);
			throw new DaoSystemException(e);
		} finally { 
			try {
				closeQuaetly(selectAllResultSet, selectAllStat, conn);
			} catch (Exception e1) {
				log.error("Error description",e1);
				throw new DaoSystemException(e1);
			}
		}

		return entitys;
	}
}
