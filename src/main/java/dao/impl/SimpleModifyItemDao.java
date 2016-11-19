package dao.impl;

import static utils.JBDCUtil.closeQuaetly;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import dao.ModifyItemDao;
import dao.exceptions.DaoSystemException;
import dao.exceptions.NoSuchEntityException;

public class SimpleModifyItemDao implements ModifyItemDao {

	public static Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	private final static String ID = "ID";
	private final static String DELETE_ITEM_BY_ID = "{call delItemsById(\'@" + ID + "\')}";

	private final static String ID_COL_NAME = "id-new-s";
	private final static String HEADER_COL_NAME = "header";
	private final static String DESCRIPTION_COL_NAME = "description";
	private final static String DATE_COL_NAME = "date-create";
	private final static String USER_ID_COL_NAME = "user-id-user";
	private final static String URL_COL_NAME = "url-link";
	private final static String CITY_ID_COL_NAME = "city-id";
	private final static String IMG_ID_COL_NAME = "img-id";
	private final static String SECTION_ID_COL_NAME = "section-id";
	private final static String MAP_LAT_COL_NAME = "map-lat-link";
	private final static String MAP_LNG_COL_NAME = "map-lng-link";
	private final static String ADRESS_COL_NAME = "adress-link";
	private final static String CONTACTS_COL_NAME = "contacts-link";
	private final static String DATE_FROM_COL_NAME = "date-from";
	private final static String DATE_TO_COL_NAME = "date-to";
	private final static String MONTH_FROM_COL_NAME = "month-from";
	private final static String MONTH_TO_COL_NAME = "month-to";
	private final static String DAYS_FROM_COL_NAME = "days-from";
	private final static String DAYS_TO_COL_NAME = "days-to";
	private final static String TIME_FROM_COL_NAME = "time-from";
	private final static String TIME_TO_COL_NAME = "time-to";
	
	
	private final static String CREATE_SIMPLE_ENTITY = "{call createSimpleEntity(" 
			+ "\'@" + HEADER_COL_NAME + "\', " 
			+ "\'@"	+ DESCRIPTION_COL_NAME + "\', " 
			+ "\'@" + DATE_COL_NAME + "\', " 
			+ "\'@" + USER_ID_COL_NAME + "\', " 
			+ "\'@" + URL_COL_NAME + "\', " 
			+ "\'@"	+ ADRESS_COL_NAME + "\', " 
			+ "\'@"	+ CONTACTS_COL_NAME + "\', "  
			+ "\'@" + MAP_LAT_COL_NAME + "\', " 
			+ "\'@" + MAP_LNG_COL_NAME + "\', "  
			+ "\'@" + CITY_ID_COL_NAME + "\', "  
			+ "\'@" + SECTION_ID_COL_NAME + "\', "  
			+ "\'@" + DATE_FROM_COL_NAME + "\', "  
			+ "\'@" + DATE_TO_COL_NAME + "\', "  
			+ "\'@" + MONTH_FROM_COL_NAME + "\', "  
			+ "\'@" + MONTH_TO_COL_NAME + "\', "  
			+ "\'@" + DAYS_FROM_COL_NAME + "\', "  
			+ "\'@" + DAYS_TO_COL_NAME + "\', "  
			+ "\'@" + TIME_FROM_COL_NAME + "\', "  
			+ "\'@" + TIME_TO_COL_NAME 
			+ "\') " + "}";
	
	private final static String UPDATE_SIMPLE_ENTITY_TEST = "{call updateSimpleEntity(" 
			+ "\'@" + ID_COL_NAME + "\', " 
			+ "\'@" + HEADER_COL_NAME + "\', " 
			+ "\'@"	+ DESCRIPTION_COL_NAME + "\', " 
			+ "\'@" + DATE_COL_NAME + "\', " 
			+ "\'@" + USER_ID_COL_NAME + "\', " 
			+ "\'@" + URL_COL_NAME + "\', " 
			+ "\'@"	+ ADRESS_COL_NAME + "\', " 
			+ "\'@"	+ CONTACTS_COL_NAME + "\', " 
			+ "\'@" + MAP_LAT_COL_NAME + "\', " 
			+ "\'@" + MAP_LNG_COL_NAME + "\', "  
			+ "\'@" + CITY_ID_COL_NAME + "\', "  
			+ "\'@" + SECTION_ID_COL_NAME + "\', "  
			+ "\'@" + DATE_FROM_COL_NAME + "\', "  
			+ "\'@" + DATE_TO_COL_NAME + "\', "  
			+ "\'@" + MONTH_FROM_COL_NAME + "\', "  
			+ "\'@" + MONTH_TO_COL_NAME + "\', "  
			+ "\'@" + DAYS_FROM_COL_NAME + "\', "  
			+ "\'@" + DAYS_TO_COL_NAME + "\', "  
			+ "\'@" + TIME_FROM_COL_NAME + "\', "  
			+ "\'@" + TIME_TO_COL_NAME 	+ "\', "  		
					+ "\'@c_" + HEADER_COL_NAME + "\', " 
					+ "\'@c_"	+ DESCRIPTION_COL_NAME + "\', " 
					+ "\'@c_" + DATE_COL_NAME + "\', "  
					+ "\'@c_" + URL_COL_NAME + "\', " 
					+ "\'@c_"	+ ADRESS_COL_NAME + "\', " 
					+ "\'@c_"	+ CONTACTS_COL_NAME + "\', " 
					+ "\'@c_" + MAP_LAT_COL_NAME + "\', " 
					+ "\'@c_" + MAP_LNG_COL_NAME + "\', "  
					+ "\'@c_" + CITY_ID_COL_NAME + "\', "  
					+ "\'@c_" + SECTION_ID_COL_NAME + "\', "  
					+ "\'@c_" + DATE_FROM_COL_NAME + "\', "  
					+ "\'@c_" + DATE_TO_COL_NAME + "\', "  
					+ "\'@c_" + MONTH_FROM_COL_NAME + "\', "  
					+ "\'@c_" + MONTH_TO_COL_NAME + "\', "  
					+ "\'@c_" + DAYS_FROM_COL_NAME + "\', "  
					+ "\'@c_" + DAYS_TO_COL_NAME + "\', "  
					+ "\'@c_" + TIME_FROM_COL_NAME + "\', "  
					+ "\'@c_" + TIME_TO_COL_NAME 
			+ "\') " + "}";
	
	   
	private DataSource dataSource;
 

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void deleteItemById(String id) throws DaoSystemException, NoSuchEntityException {

		CallableStatement delByIdStat = null;
		ResultSet delBuIdResultSet = null;
		try {
			Connection conn = dataSource.getConnection();

			log.debug("ID : " + id);
			String DELETE_ITEM_BY_ID_cal = DELETE_ITEM_BY_ID.replaceAll("@" + ID, id);
			log.debug("SQL to Execute : " + DELETE_ITEM_BY_ID_cal);
			delByIdStat = conn.prepareCall(DELETE_ITEM_BY_ID_cal);
			delByIdStat.execute();
			delBuIdResultSet = delByIdStat.getResultSet();
			log.debug("CallableStatement : " + DELETE_ITEM_BY_ID_cal + " was execute!");

			log.debug("Result set of " + DELETE_ITEM_BY_ID + ": " + delBuIdResultSet);

		} catch (SQLException e) {
			log.error("Error description", e);
			throw new DaoSystemException(e);
		} finally {
			log.debug("Close SELECT_ALL_SQL ResultSet and SELECT_ALL_SQL Statement");
			try {
				closeQuaetly(delBuIdResultSet, delByIdStat);
			} catch (Exception e1) {
				log.error(e1);
				throw new DaoSystemException(e1);
			}
		}

	}
  

	@Override
	public int createItem(String header, String description, String date, String user_id, String url_link,
			String adress, String contacts, String map_link_lat, String map_link_lng, String city_id, String section_id,
			String date_from, String date_to, String month_from, String month_to, String days_from, String days_to,
			String time_from, String time_to) throws DaoSystemException, NoSuchEntityException {
		CallableStatement creatItemStat = null;
		ResultSet createItemResultSet = null;
		Connection conn = null;
		try {

			conn = dataSource.getConnection();
			String CREATE_ITEM_CAL = CREATE_SIMPLE_ENTITY
					
					.replaceAll( "@" + HEADER_COL_NAME ,header)
					.replaceAll( "@" + DESCRIPTION_COL_NAME ,description)
					.replaceAll( "@" + DATE_COL_NAME ,date)
					.replaceAll( "@" + USER_ID_COL_NAME,user_id)
					.replaceAll( "@" + URL_COL_NAME,url_link)
					.replaceAll( "@" + ADRESS_COL_NAME ,adress)
					.replaceAll( "@" + CONTACTS_COL_NAME ,contacts)
					.replaceAll( "@" + MAP_LAT_COL_NAME,map_link_lat)
					.replaceAll( "@" + MAP_LNG_COL_NAME,map_link_lng)
					.replaceAll( "@" + CITY_ID_COL_NAME ,city_id)
					.replaceAll( "@" + SECTION_ID_COL_NAME ,  section_id)
					.replaceAll( "@" + DATE_FROM_COL_NAME , date_from)
					.replaceAll( "@" + DATE_TO_COL_NAME , date_to)
					.replaceAll( "@" + MONTH_FROM_COL_NAME ,  month_from)
					.replaceAll( "@" + MONTH_TO_COL_NAME , month_to)
					.replaceAll( "@" + DAYS_FROM_COL_NAME ,days_from)
					.replaceAll( "@" + DAYS_TO_COL_NAME, days_to)
					.replaceAll( "@" + TIME_FROM_COL_NAME, time_from)
					.replaceAll( "@" + TIME_TO_COL_NAME ,time_to) ;
			log.info("SQL to Execute : " + CREATE_ITEM_CAL);
			creatItemStat = conn.prepareCall(CREATE_ITEM_CAL);
			creatItemStat.execute();
			createItemResultSet = creatItemStat.getResultSet();
			
			log.info("CallableStatement : " + CREATE_ITEM_CAL + " was execute!");

			log.debug("Result set of " + CREATE_ITEM_CAL + ": " + createItemResultSet);

			if(createItemResultSet.next()){
				int idr = createItemResultSet.getInt(ID);
				log.debug("ID of new entity - " + idr );
				return (idr);
			}
			throw new DaoSystemException("ENTITY ID IS not FOUND");
		} catch (SQLException e) {
			log.error("Error description", e);
			throw new DaoSystemException(e);
		} finally {
			log.debug("Close SELECT_ALL_SQL ResultSet and SELECT_ALL_SQL Statement");
			try {
				closeQuaetly(conn,createItemResultSet, creatItemStat);
			} catch (Exception e1) {
				log.error(e1);
				throw new DaoSystemException(e1);
			}
		}
		
	}

	@Override
	public void updateItem(String id, String header, String description, String date, String user_id, String url_link,
			String adress, String contacts, String map_link_lat, String map_link_lng, String city_id, String section_id,
			String date_from, String date_to, String month_from, String month_to, String days_from, String days_to,
			String time_from, String time_to, String c_header, String c_description, String c_date, String c_url_link,
			String c_adress, String c_contacts, String c_map_link_lat, String c_map_link_lng, String c_city_id,
			String c_section_id, String c_date_from, String c_date_to, String c_month_from, String c_month_to,
			String c_days_from, String c_days_to, String c_time_from, String c_time_to)
			throws DaoSystemException, NoSuchEntityException {
		
		CallableStatement updateItemStat = null;
		ResultSet cupdateItemResultSet = null;
		Connection conn  = null;
		try {
			 conn = dataSource.getConnection();
			String UPDATE_ITEM_CAL = UPDATE_SIMPLE_ENTITY_TEST

				.replaceAll( "@" + ID_COL_NAME ,id)
				.replaceAll( "@c_" + HEADER_COL_NAME ,c_header)
				.replaceAll( "@c_" + DESCRIPTION_COL_NAME ,c_description)
				.replaceAll( "@c_" + DATE_COL_NAME ,c_date)
				.replaceAll( "@c_" + URL_COL_NAME,c_url_link)
				.replaceAll( "@c_" + ADRESS_COL_NAME ,c_adress)
				.replaceAll( "@c_" + CONTACTS_COL_NAME ,c_contacts)
				.replaceAll( "@c_" + MAP_LAT_COL_NAME,c_map_link_lat)
				.replaceAll( "@c_" + MAP_LNG_COL_NAME,c_map_link_lng)
				.replaceAll( "@c_" + CITY_ID_COL_NAME ,c_city_id)
				.replaceAll( "@c_" + SECTION_ID_COL_NAME ,  c_section_id)
				.replaceAll( "@c_" + DATE_FROM_COL_NAME , c_date_from)
				.replaceAll( "@c_" + DATE_TO_COL_NAME , c_date_to)
				.replaceAll( "@c_" + MONTH_FROM_COL_NAME ,  c_month_from)
				.replaceAll( "@c_" + MONTH_TO_COL_NAME , c_month_to)
				.replaceAll( "@c_" + DAYS_FROM_COL_NAME ,c_days_from)
				.replaceAll( "@c_" + DAYS_TO_COL_NAME, c_days_to)
				.replaceAll( "@c_" + TIME_FROM_COL_NAME, c_time_from)
				.replaceAll( "@c_" + TIME_TO_COL_NAME ,c_time_to) 
				.replaceAll( "@" + USER_ID_COL_NAME,user_id)
				.replaceAll( "@" + HEADER_COL_NAME ,header)
				.replaceAll( "@" + DESCRIPTION_COL_NAME ,description)
				.replaceAll( "@" + DATE_COL_NAME ,date)
				.replaceAll( "@" + URL_COL_NAME,url_link)
				.replaceAll( "@" + ADRESS_COL_NAME ,adress)
				.replaceAll( "@" + CONTACTS_COL_NAME ,contacts)
				.replaceAll( "@" + MAP_LAT_COL_NAME,map_link_lat)
				.replaceAll( "@" + MAP_LNG_COL_NAME,map_link_lng)
				.replaceAll( "@" + CITY_ID_COL_NAME ,city_id)
				.replaceAll( "@" + SECTION_ID_COL_NAME ,  section_id)
				.replaceAll( "@" + DATE_FROM_COL_NAME , date_from)
				.replaceAll( "@" + DATE_TO_COL_NAME , date_to)
				.replaceAll( "@" + MONTH_FROM_COL_NAME ,  month_from)
				.replaceAll( "@" + MONTH_TO_COL_NAME , month_to)
				.replaceAll( "@" + DAYS_FROM_COL_NAME ,days_from)
				.replaceAll( "@" + DAYS_TO_COL_NAME, days_to)
				.replaceAll( "@" + TIME_FROM_COL_NAME, time_from)
				.replaceAll( "@" + TIME_TO_COL_NAME ,time_to) ;
		log.info("SQL to Execute : " + UPDATE_ITEM_CAL);
		updateItemStat = conn.prepareCall(UPDATE_ITEM_CAL);
		updateItemStat.execute();
		cupdateItemResultSet = updateItemStat.getResultSet();
		log.info("CallableStatement : " + UPDATE_ITEM_CAL + " was execute!");

		log.debug("Result set of " + UPDATE_ITEM_CAL + ": " + cupdateItemResultSet);

	} catch (SQLException e) {
		log.error("Error description", e);
		throw new DaoSystemException(e);
	} finally {
		log.debug("Close SELECT_ALL_SQL ResultSet and SELECT_ALL_SQL Statement");
		try {
			closeQuaetly(conn, cupdateItemResultSet, updateItemStat);
		} catch (Exception e1) {
			log.error(e1);
			throw new DaoSystemException(e1);
		}
	}
		
	}

}
 
