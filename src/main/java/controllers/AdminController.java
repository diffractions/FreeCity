package controllers;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.CityDao;
import dao.SectionDao;
import dao.exceptions.DaoException;
import entity.City;
import inject.Inject;

public class AdminController extends DependencyInjectionServlet {
	private static final long serialVersionUID = 1L;

	public static Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
//
//	public static final String PAGE_OK = "/AdminPage.jsp";
//	public static final String PAGE_ERROR = "/index.jsp";
//
//	private final static String PARAM_NEW_CITY = "city";
//	private final static String PARAM_NEW_SECTION = "section";
//	private final static String PARAM_DEL_CITY = "del_city";
//	private final static String PARAM_DEL_SECTION = "del_section";
//	private final static String PARAM_NEW_SECTION_PARENT = "section_id";
//
//	public static final String ATTRIBUTE_ERR_STR = "errorString";
//	public static final String ATTRIBUTE_ERR_CODE = "errorCode";
//
//	private static final String ATTRIBUTE_CITY_MODEL_TO_VIEW = "atr_city"; 
//
//	@Inject("cityDao")
//	public CityDao cityDao;
//
//	@Inject("sectionDao")
//	public SectionDao menu;
//
//	@Override
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		try {
//
//			if (request.getParameter(PARAM_NEW_CITY) != null) {
//				log.debug("TRY to add new city :" + request.getParameter(PARAM_NEW_CITY));
//				cityDao.addCity(request.getParameter(PARAM_NEW_CITY));
//			}
//			if (request.getParameter(PARAM_NEW_SECTION) != null) {
//				log.debug("TRY to add new section :" + request.getParameter(PARAM_NEW_SECTION) + ", "
//						+ request.getParameter(PARAM_NEW_SECTION_PARENT));
//				menu.addSection(request.getParameter(PARAM_NEW_SECTION),
//						request.getParameter(PARAM_NEW_SECTION_PARENT).trim());
//			}
//
//			if (request.getParameter(PARAM_DEL_CITY) != null) {
//				log.debug("TRY to del  city :" + request.getParameter(PARAM_DEL_CITY));
//				cityDao.delCity(request.getParameter(PARAM_DEL_CITY));
//			}
//
//			if (request.getParameter(PARAM_DEL_SECTION) != null) {
//				log.debug("TRY to del section :" + request.getParameter(PARAM_NEW_SECTION_PARENT));
//				menu.delSection(request.getParameter(PARAM_NEW_SECTION_PARENT).trim());
//			}
//
//			log.debug("redirect response and request to do get");
//			doGet(request, response);
//			return;
//
//		} catch (DaoException e) {
//			log.error(e);
//			request.setAttribute(ATTRIBUTE_ERR_CODE, "404");
//			request.setAttribute(ATTRIBUTE_ERR_STR, e.getMessage());
//		}
//
//		log.warn(">>> Redirect to :" + PAGE_ERROR);
//		getServletContext().getRequestDispatcher(PAGE_ERROR).include(request, response);
//	}
//
//	@Override
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		try {
//
//			addStandartModels(request, menu, cityDao);
//			CopyOnWriteArrayList<City> cityModel =   cityDao.getCityAll(); 
//	 
//			
//			log.info(">>>  Add " + ATTRIBUTE_CITY_MODEL_TO_VIEW + " to request attribute");
//			request.setAttribute(ATTRIBUTE_CITY_MODEL_TO_VIEW, cityModel);
//			log.debug(">>> "+ATTRIBUTE_CITY_MODEL_TO_VIEW+" MODEL:" + cityModel); 
//
//			log.debug("ATTRIBUTE_MODEL_TO_VIEW :  " + ATTRIBUTE_CITY_MODEL_TO_VIEW + " befor JSP : "
//					+ request.getAttribute(ATTRIBUTE_CITY_MODEL_TO_VIEW));
//			log.debug("ATTRIBUTE_MODEL_TO_VIEW :  " + ATTRIBUTE_MENU_MODEL_TO_VIEW + " befor JSP : "
//					+ request.getAttribute(ATTRIBUTE_MENU_MODEL_TO_VIEW));
//
//			getServletContext().getRequestDispatcher(PAGE_OK).include(request, response);
//
//			log.debug("ATTRIBUTE_MODEL_TO_VIEW :  " + ATTRIBUTE_CITY_MODEL_TO_VIEW + " after JSP : "
//					+ request.getAttribute(ATTRIBUTE_CITY_MODEL_TO_VIEW));
//			log.debug("ATTRIBUTE_MODEL_TO_VIEW :  " + ATTRIBUTE_MENU_MODEL_TO_VIEW + " after JSP : "
//					+ request.getAttribute(ATTRIBUTE_MENU_MODEL_TO_VIEW));
//			return;
//
//		} catch (DaoException e) {
//			log.error(e);
//			request.setAttribute(ATTRIBUTE_ERR_CODE, "404");
//			request.setAttribute(ATTRIBUTE_ERR_STR, e.getMessage());
//		}
//
//		log.warn(">>>  Redirect to :" + PAGE_ERROR);
//		getServletContext().getRequestDispatcher(PAGE_ERROR).include(request, response);
//	}

}
