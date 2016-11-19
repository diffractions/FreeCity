package controllers;

import java.io.IOException; 
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.ServletException;  
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.exceptions.DaoException;
import entity.City;
import entity.ShowedItem;
 
public class ItemAllController extends RootController {
	private static final long serialVersionUID = 1L;

	public static Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	public static final String PAGE_OK = "/jsp/EntityAll.jsp";
	public static final String ATTRIBUTE_ITEM_MODEL_TO_VIEW = "items";
	public static final String ATTRIBUTE_ITEM_COUNT_TO_VIEW = "items_count";

	public static final String PAGE_ERROR = "/jsp/index.jsp";
	public static final String ATTRIBUTE_ERR_STR = "errorString";
	public static final String ATTRIBUTE_ERR_CODE = "errorCode";
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// log.info("add new cookie " + "city" + " : " + city1);

		try {

			if (entityDao == null) {
				throw new DaoException("EntityDAO not found");
			}

			CopyOnWriteArraySet<ShowedItem> itemModel = null;
			int cookie_city = -1;
			int section = -1;

			// String city1 = request.getAttribute("city_selected") != null
			// ? "" + ((City) request.getAttribute("city_selected")).getId() :
			// null;

			String city1 = (request.getAttribute("city_selected") != null
					? "" + ((City) request.getAttribute("city_selected")).getId() : null);
			city1 = city1 == null ? request.getParameter("city") : city1;

			log.debug("City in attribute " + "city_selected" + " : " + city1 + " "
					+ request.getAttribute("city_selected"));

			String date1 = request.getParameter("date");
			String section1 = request.getParameter("section");
			if (section1 != null) {
				section = Integer.parseInt(section1);

			}

			String user1 = request.getParameter("user");
			int page = 0;
			if (request.getParameter("page") != null) {
				page = Integer.parseInt(request.getParameter("page"));
				log.info(">>> Add " + "page" + " to request attribute");
				request.setAttribute("page", page);
				log.info(">>> " + "page" + " MODEL:" + page);

			}

			String tag = request.getParameter("tag");

			String action = "";
			if (request.getParameter("action") != null) {
				action = request.getParameter("action");
				log.info(">>> Add " + "action" + " to request attribute");
				request.setAttribute("action", action);
				log.info(">>> " + "action" + " MODEL:" + action);

			}

			int entityToGet = 10;
			long start = System.currentTimeMillis();

			String sectionView = section1 != null
					? menu.getSectionById(Integer.parseInt(section1)).getSectionView() == 10 ? "9, 10"
							: " 1, 2, 3, 4, 5, 6, 7, 8, 9"
					: " 1, 2, 3, 4, 5, 6, 7, 8, 9";
			int [] count = new int [1];
			itemModel = entityDao.executeSelectAll(city1, date1, section1, sectionView, user1,
					"" + (page * entityToGet), "" + entityToGet, tag, action, count);

			log.info(">>>  Add " + ATTRIBUTE_ITEM_MODEL_TO_VIEW + " to request attribute");

			request.setAttribute(ATTRIBUTE_ITEM_MODEL_TO_VIEW, itemModel);
			request.setAttribute(ATTRIBUTE_ITEM_COUNT_TO_VIEW, count[0]);
			log.info(">>> " + ATTRIBUTE_ITEM_MODEL_TO_VIEW + " MODEL:" + itemModel);
			log.info(">>> " + ATTRIBUTE_ITEM_MODEL_TO_VIEW + " was created in : " + (System.currentTimeMillis() - start)
					+ "ms");

			long from = System.currentTimeMillis();
			addStandartModels(request, section);
			log.info(">>> StandRT MODEL TO WIREW was created in : " + (System.currentTimeMillis() - from) + "ms");
			log.info(">>> DATABASE SEARCH : " + (System.currentTimeMillis() - start) + "ms");

			log.info(">>>  Redirect to :" + PAGE_OK);

			log.debug("ATTRIBUTE_MODEL_TO_VIEW : + " + ATTRIBUTE_ITEM_MODEL_TO_VIEW + " befor JSP : "
					+ request.getAttribute(ATTRIBUTE_ITEM_MODEL_TO_VIEW));

			getServletContext().getRequestDispatcher(PAGE_OK).include(request, response);

			log.debug("ATTRIBUTE_MODEL_TO_VIEW : + " + ATTRIBUTE_ITEM_MODEL_TO_VIEW + " after JSP : "
					+ request.getAttribute(ATTRIBUTE_ITEM_MODEL_TO_VIEW));
			return;

		} catch (

		DaoException e) {
			log.error("print error", e);
			request.setAttribute(ATTRIBUTE_ERR_CODE, "404");
			request.setAttribute(ATTRIBUTE_ERR_STR, e.getMessage());
		}

		log.warn(">>>  Redirect to :" + PAGE_ERROR);

		getServletContext().getRequestDispatcher(PAGE_ERROR).include(request, response);
	}

}