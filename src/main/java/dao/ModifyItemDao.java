package dao;

import dao.exceptions.DaoSystemException;
import dao.exceptions.NoSuchEntityException;

public interface ModifyItemDao {
	public void deleteItemById(String id) throws DaoSystemException, NoSuchEntityException;
 

	public int createItem(String header, String description, String date, String user_id, String url_link,
			String adress, String contacts, String map_link_lat, String map_link_lng, String city_id, String section_id,
			String date_from, String date_to, String month_from, String month_to, String days_from, String days_to,
			String time_from, String time_to) throws DaoSystemException, NoSuchEntityException;

	public void updateItem(String id, String header, String description, String date, String user_id, String url_link,
			String adress, String contacts, String map_link_lat, String map_link_lng, String city_id, String section_id,
			String date_from, String date_to, String month_from, String month_to, String days_from, String days_to,
			String time_from, String time_to, String c_header, String c_description, String c_date, String c_url_link,
			String c_adress, String c_contacts, String c_map_link_lat, String c_map_link_lng, String c_city_id,
			String c_section_id, String c_date_from, String c_date_to, String c_month_from, String c_month_to,
			String c_days_from, String c_days_to, String c_time_from, String c_time_to) throws DaoSystemException, NoSuchEntityException;

}
