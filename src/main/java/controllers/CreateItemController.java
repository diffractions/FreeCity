package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;

import dao.CityDao;
import dao.ImgDao;
import dao.ModifyItemDao;
import dao.SectionDao;
import dao.exceptions.DaoException;
import entity.City;
import entity.ShowedItem;
import entity.ShowedUser;
import inject.Inject;
import utils.EncodeUtils;

public class CreateItemController extends RootController {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	public static Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	public static final String USER_ATTR = "user";

	public static final String PAGE_OK = "/jsp/AddItem.jsp";
	public static final String PAGE_ERROR = "/jsp/AddItem.jsp";

	private final static String PARAM_TYPE = "type";
	private final static String PARAM_VALUE_TYPE_SIMPLE = "simple";

	private final static String PARAM_HEADER = "header";
	private final static String PARAM_DESCRIPTION = "description";
	private final static String PARAM_URL = "url-link";
	private final static String PARAM_CITY_ID = "city-id";
	private final static String PARAM_IMG_ID = "img-id";
	private final static String PARAM_SECTION_ID = "section-id";
	private final static String PARAM_MAP_LAT = "map-link-lat";
	private final static String PARAM_MAP_LNG = "map-link-lng";
	private final static String PARAM_ADRESS = "adress-link";
	private final static String PARAM_CONTACTS = "contacts-link";
	private final static String PARAM_DATE_FROM = "date-from";
	private final static String PARAM_DATE_TO = "date-to";
	private final static String PARAM_MONTH_FROM = "month-from";
	private final static String PARAM_MONTH_TO = "month-to";
	private final static String PARAM_DAYS_FROM = "days-from";
	private final static String PARAM_DAYS_TO = "days-to";
	private final static String PARAM_TIME_FROM = "time-from";
	private final static String PARAM_TIME_TO = "time-to";

	private final static String PARAM_PHOTO = "photo";
	private final static String PARAM_TAG = "it-tag";
	

	public static final String ATTRIBUTE_ERR_STR = "errorString";
	public static final String ATTRIBUTE_ERR_CODE = "errorCode";

	private static final String ATTRIBUTE_CITY_MODEL_TO_VIEW = "atr_city";

	private static final String PARAM_NAME_ID = "id";
	private static final String PAGE_OK_AFTER_DELETE = "list";
	private static final String PAGE_OK_AFTER_CREATE = "list";

	public static final String PARAM_ID = "id";
	public static final String ATTRIBUTE_ITEM_MODEL_TO_VIEW = "item";

	@Inject("modifyItemDao")
	public ModifyItemDao modifyItemDao;

	@Inject("cityDao")
	public CityDao cityDao;

	@Inject("sectionDao")
	public SectionDao menu;

	@Inject("imgDao")
	public ImgDao imgDao;

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

//		request.setCharacterEncoding("UTF-8");
		
		if (request.getRequestURI().contains("create")) {
			try {

				int city = -1;
				int entityId = -1;
				if (request.getParameter(PARAM_TYPE).equalsIgnoreCase(PARAM_VALUE_TYPE_SIMPLE)) {


//					log.error("-----null----->" + new String(request.getParameter(PARAM_HEADER)));
//					log.error("-----cp1251--->" + new String(request.getParameter(PARAM_HEADER).getBytes(EncodeUtils.CP1251_CODE), EncodeUtils.UTF_8_CODE));
//					log.error("-----UTF-8---->" + new String(request.getParameter(PARAM_HEADER).getBytes(EncodeUtils.UTF_8_CODE), EncodeUtils.UTF_8_CODE));
//					log.error("---iso-8859--1>" + new String(request.getParameter(PARAM_HEADER).getBytes("iso-8859-1"), EncodeUtils.UTF_8_CODE));
//					log.error("-windows-1251->" + new String(request.getParameter(PARAM_HEADER).getBytes("windows-1251"), EncodeUtils.UTF_8_CODE));
//					log.error("---- utf-16--->" + new String(request.getParameter(PARAM_HEADER).getBytes(" utf-16"), EncodeUtils.UTF_8_CODE));
					
					
					String header = new String(request.getParameter(PARAM_HEADER).getBytes(EncodeUtils.ISO_8859_1), EncodeUtils.UTF_8_CODE);
					String description = new String(request.getParameter(PARAM_DESCRIPTION).getBytes(EncodeUtils.ISO_8859_1), EncodeUtils.UTF_8_CODE);
					String user_id = null;
					if (request.getSession().getAttribute(USER_ATTR) != null) {
						user_id = "" + ((ShowedUser) request.getSession().getAttribute(USER_ATTR)).getUserId();
					}

					String date = dateFormat.format(new Date(System.currentTimeMillis()));
					String url_link = new String(request.getParameter(PARAM_URL).getBytes(EncodeUtils.ISO_8859_1), EncodeUtils.UTF_8_CODE);
					String map_link_lat = request.getParameter(PARAM_MAP_LAT);
					String map_link_lng = request.getParameter(PARAM_MAP_LNG);
					String city_id = request.getParameter(PARAM_CITY_ID);
					String adress = new String(request.getParameter(PARAM_ADRESS).getBytes(EncodeUtils.ISO_8859_1), EncodeUtils.UTF_8_CODE);
					String contacts = new String(request.getParameter(PARAM_CONTACTS).getBytes(EncodeUtils.ISO_8859_1), EncodeUtils.UTF_8_CODE);
					String section_id = request.getParameter(PARAM_SECTION_ID);
					String date_from = request.getParameter(PARAM_DATE_FROM);
					String date_to = request.getParameter(PARAM_DATE_TO);
					String month_from = request.getParameter(PARAM_MONTH_FROM);
					String month_to = request.getParameter(PARAM_MONTH_TO);
					String days_from = request.getParameter(PARAM_DAYS_FROM);
					String days_to = request.getParameter(PARAM_DAYS_TO);
					String time_from = request.getParameter(PARAM_TIME_FROM);
					String time_to = request.getParameter(PARAM_TIME_TO);

					entityId = modifyItemDao.createItem(header, description, date, user_id, url_link, adress, contacts,
							map_link_lat, map_link_lng, city_id, section_id, date_from, date_to, month_from, month_to,
							days_from, days_to, time_from, time_to);


					String tag = new String(request.getParameter(PARAM_TAG).getBytes(EncodeUtils.ISO_8859_1), EncodeUtils.UTF_8_CODE);
					modifyItemDao.addTagsToItem(tag, entityId);

					
					
					try {
						Part filePart = request.getPart(PARAM_PHOTO);
						if (filePart.getName() != null && !filePart.getName().equals("")) {
							imgDao.addImg(entityId, filePart);
						}
					} catch (DaoException e) {
						log.error(e);
						request.setAttribute(ATTRIBUTE_ERR_STR, "Будьласка зменшіть розмір зображення <1мб");
					}
					city = Integer.parseInt(city_id);

				}

				if (city != -1 || entityId != -1)
					response.sendRedirect(cityDao.getCityById(city).getShortName() + "/entity?id=" + entityId);
				else
					doGet(request, response);
				return;
			} catch (DaoException e) {
				log.error(e);
				request.setAttribute(ATTRIBUTE_ERR_CODE, "404");
				request.setAttribute(ATTRIBUTE_ERR_STR, e.getMessage());
			}
		} else if (request.getRequestURI().contains("update")) {

			try {

				String user_id = null;
				if (request.getSession().getAttribute(USER_ATTR) != null) {
					user_id = "" + ((ShowedUser) request.getSession().getAttribute(USER_ATTR)).getUserId();

				}
				if (user_id == null) {
					throw new ServletException("pleaser register");
				}

				String id = new String(request.getParameter(PARAM_ID));
				String header = new String(request.getParameter(PARAM_HEADER).getBytes(EncodeUtils.ISO_8859_1), EncodeUtils.UTF_8_CODE);
				String description = new String(request.getParameter(PARAM_DESCRIPTION).getBytes(EncodeUtils.ISO_8859_1), EncodeUtils.UTF_8_CODE);
				String date = dateFormat.format(new Date(System.currentTimeMillis()));
				String url_link = new String(request.getParameter(PARAM_URL).getBytes(EncodeUtils.ISO_8859_1), EncodeUtils.UTF_8_CODE);
				String map_link_lat = request.getParameter(PARAM_MAP_LAT);
				String map_link_lng = request.getParameter(PARAM_MAP_LNG);
				String city_id = request.getParameter(PARAM_CITY_ID);
				String adress = new String(request.getParameter(PARAM_ADRESS).getBytes(EncodeUtils.ISO_8859_1), EncodeUtils.UTF_8_CODE);
				String contacts = new String(request.getParameter(PARAM_CONTACTS).getBytes(EncodeUtils.ISO_8859_1), EncodeUtils.UTF_8_CODE);
				String section_id = request.getParameter(PARAM_SECTION_ID);
				String date_from = request.getParameter(PARAM_DATE_FROM);
				String date_to = request.getParameter(PARAM_DATE_TO);
				String month_from = request.getParameter(PARAM_MONTH_FROM);
				String month_to = request.getParameter(PARAM_MONTH_TO);
				String days_from = request.getParameter(PARAM_DAYS_FROM);
				String days_to = request.getParameter(PARAM_DAYS_TO);
				String time_from = request.getParameter(PARAM_TIME_FROM);
				String time_to = request.getParameter(PARAM_TIME_TO);

				String c_header = new String(request.getParameter("c_" + PARAM_HEADER).getBytes(EncodeUtils.ISO_8859_1), EncodeUtils.UTF_8_CODE);
				String c_description = new String(request.getParameter("c_" + PARAM_DESCRIPTION).getBytes(EncodeUtils.ISO_8859_1), EncodeUtils.UTF_8_CODE);
				String c_date = dateFormat.format(new Date(System.currentTimeMillis()));
				String c_url_link = new String(request.getParameter("c_" + PARAM_URL).getBytes(EncodeUtils.ISO_8859_1), EncodeUtils.UTF_8_CODE);
				String c_map_link_lat = request.getParameter("c_" + PARAM_MAP_LAT);
				String c_map_link_lng = request.getParameter("c_" + PARAM_MAP_LNG);
				String c_city_id = request.getParameter("c_" + PARAM_CITY_ID);
				String c_adress = new String(request.getParameter("c_" + PARAM_ADRESS).getBytes(EncodeUtils.ISO_8859_1), EncodeUtils.UTF_8_CODE);
				String c_contacts = new String(request.getParameter("c_" + PARAM_CONTACTS).getBytes(EncodeUtils.ISO_8859_1), EncodeUtils.UTF_8_CODE);
				String c_section_id = request.getParameter("c_" + PARAM_SECTION_ID);
				String c_date_f = request.getParameter("c_" + PARAM_DATE_FROM);
				String c_date_from = c_date_f.substring(6) + "-" + c_date_f.substring(3, 5) + "-"
						+ c_date_f.substring(0, 2);
				String c_date_t = request.getParameter("c_" + PARAM_DATE_TO);
				String c_date_to = c_date_t.substring(6) + "-" + c_date_t.substring(3, 5) + "-"
						+ c_date_t.substring(0, 2);
				String c_month_from = request.getParameter("c_" + PARAM_MONTH_FROM);
				String c_month_to = request.getParameter("c_" + PARAM_MONTH_TO);
				String c_days_from = request.getParameter("c_" + PARAM_DAYS_FROM);
				String c_days_to = request.getParameter("c_" + PARAM_DAYS_TO);
				String c_time_from = request.getParameter("c_" + PARAM_TIME_FROM);
				String c_time_to = request.getParameter("c_" + PARAM_TIME_TO);

				modifyItemDao.updateItem(id, header, description, date, user_id, url_link, adress, contacts,
						map_link_lat, map_link_lng, city_id, section_id, date_from, date_to, month_from, month_to,
						days_from, days_to, time_from, time_to, c_header, c_description, c_date, c_url_link, c_adress,
						c_contacts, c_map_link_lat, c_map_link_lng, c_city_id, c_section_id, c_date_from, c_date_to,
						c_month_from, c_month_to, c_days_from, c_days_to, c_time_from, c_time_to);

				
				int entityId = Integer.parseInt(id);
				String tag = new String(request.getParameter(PARAM_TAG).getBytes(EncodeUtils.ISO_8859_1), EncodeUtils.UTF_8_CODE);
				modifyItemDao.updItemTags(tag, entityId);
				
				try {

					Part filePart = request.getPart(PARAM_PHOTO);
					if (filePart != null) {
						imgDao.updImg(entityId, filePart);

					}
				} catch (DaoException e) {
					log.error(e);
					request.setAttribute(ATTRIBUTE_ERR_STR, "Будьласка зменшіть розмір зображення <1мб");

				}

				doGet(request, response);
				return;
			} catch (DaoException e) {
				log.error(e);
				request.setAttribute(ATTRIBUTE_ERR_CODE, "404");
				request.setAttribute(ATTRIBUTE_ERR_STR, e.getMessage());
			}

		} else if (request.getRequestURI().contains("delete")) {
			try {
				String id = request.getParameter(PARAM_NAME_ID);
				log.warn("ID to delete " + id);
				Integer enti_id = Integer.parseInt(id);
				imgDao.delImgByEntityId(enti_id);
				modifyItemDao.deleteTagsById(enti_id);
				modifyItemDao.deleteItemById(id);

				response.sendRedirect(PAGE_OK_AFTER_DELETE);
				return;
			} catch (Exception e) {
				log.error(e);
				request.setAttribute(ATTRIBUTE_ERR_CODE, "404");
				request.setAttribute(ATTRIBUTE_ERR_STR, e.getMessage());
			}
		}
		log.warn(">>> Redirect to :" + PAGE_ERROR);
		getServletContext().getRequestDispatcher(PAGE_ERROR).include(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getRequestURI().contains("update")) {
			try {
				addStandartModels(request, -1);

				log.info(">>>  Redirect to :" + PAGE_OK);

				log.debug("ATTRIBUTE_MODEL_TO_VIEW :  " + ATTRIBUTE_CITY_MODEL_TO_VIEW + " befor JSP : "
						+ request.getAttribute(ATTRIBUTE_CITY_MODEL_TO_VIEW));
				log.debug("ATTRIBUTE_MODEL_TO_VIEW :  " + ATTRIBUTE_MENU_MODEL_TO_VIEW + " befor JSP : "
						+ request.getAttribute(ATTRIBUTE_MENU_MODEL_TO_VIEW));

				int id = Integer.parseInt(request.getParameter(PARAM_ID));

				int status = -1;
				ShowedItem itemModel = entityDao.selectById(id);

				log.info(">>>  Add " + ATTRIBUTE_ITEM_MODEL_TO_VIEW + " to request attribute");
				request.setAttribute(ATTRIBUTE_ITEM_MODEL_TO_VIEW, itemModel);
				log.debug(">>> " + ATTRIBUTE_ITEM_MODEL_TO_VIEW + " MODEL:" + itemModel);

				getServletContext().getRequestDispatcher(PAGE_OK).include(request, response);

				log.debug("ATTRIBUTE_MODEL_TO_VIEW :  " + ATTRIBUTE_CITY_MODEL_TO_VIEW + " after JSP : "
						+ request.getAttribute(ATTRIBUTE_CITY_MODEL_TO_VIEW));
				log.debug("ATTRIBUTE_MODEL_TO_VIEW :  " + ATTRIBUTE_MENU_MODEL_TO_VIEW + " after JSP : "
						+ request.getAttribute(ATTRIBUTE_MENU_MODEL_TO_VIEW));
				return;
			} catch (Exception e) {
				log.error(e);
				request.setAttribute(ATTRIBUTE_ERR_CODE, "404");
				request.setAttribute(ATTRIBUTE_ERR_STR, e.getMessage());
			}
		}

		else {
			try {

				addStandartModels(request);

				log.info(">>>  Redirect to :" + PAGE_OK);

				log.debug("ATTRIBUTE_MODEL_TO_VIEW :  " + ATTRIBUTE_CITY_MODEL_TO_VIEW + " befor JSP : "
						+ request.getAttribute(ATTRIBUTE_CITY_MODEL_TO_VIEW));
				log.debug("ATTRIBUTE_MODEL_TO_VIEW :  " + ATTRIBUTE_MENU_MODEL_TO_VIEW + " befor JSP : "
						+ request.getAttribute(ATTRIBUTE_MENU_MODEL_TO_VIEW));

				getServletContext().getRequestDispatcher(PAGE_OK).include(request, response);

				log.debug("ATTRIBUTE_MODEL_TO_VIEW :  " + ATTRIBUTE_CITY_MODEL_TO_VIEW + " after JSP : "
						+ request.getAttribute(ATTRIBUTE_CITY_MODEL_TO_VIEW));
				log.debug("ATTRIBUTE_MODEL_TO_VIEW :  " + ATTRIBUTE_MENU_MODEL_TO_VIEW + " after JSP : "
						+ request.getAttribute(ATTRIBUTE_MENU_MODEL_TO_VIEW));
				return;

			} catch (DaoException e) {
				log.error(e);
				request.setAttribute(ATTRIBUTE_ERR_CODE, "404");
				request.setAttribute(ATTRIBUTE_ERR_STR, e.getMessage());
			}
		}

		log.warn(">>>  Redirect to :" + PAGE_ERROR);
		getServletContext().getRequestDispatcher(PAGE_ERROR).include(request, response);
	}
}