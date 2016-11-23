package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.ShowedItemDao;
import dao.exceptions.DaoException;
import entity.City;
import entity.ShowedItem;
import inject.Inject;

/**
 * Servlet implementation class ItemAllController
 */
public class ItemByIdController extends RootController {
	private static final long serialVersionUID = 1L;

	public static Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	public static final String PAGE_OK = "/jsp/EntityOne.jsp";
	public static final String PAGE_ERROR = "/jsp/index.jsp";
	public static final String PARAM_ID = "id";
	public static final String ATTRIBUTE_ITEM_MODEL_TO_VIEW = "item";
	public static final String ATTRIBUTE_ERR_STR = "errorString";
	public static final String ATTRIBUTE_ERR_CODE = "errorCode";

	@Inject("entityShowDao")
	public ShowedItemDao entityDao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			if (entityDao == null) {
				throw new DaoException("EntityDAO not found");
			}
 
			int id = Integer.parseInt(request.getParameter(PARAM_ID));
		 
			
			int status = -1;
			
			
			ShowedItem itemModel = entityDao.selectById(id);

			if ((request.getAttribute("city_selected") != null)
					&& itemModel.getCities().get(0).getId() == ((City) request.getAttribute("city_selected")).getId()) {
				log.info(">>>  Add " + ATTRIBUTE_ITEM_MODEL_TO_VIEW + " to request attribute");
				request.setAttribute(ATTRIBUTE_ITEM_MODEL_TO_VIEW, itemModel);
				log.debug(">>> " + ATTRIBUTE_ITEM_MODEL_TO_VIEW + " MODEL:" + itemModel);
			}

			int section = -1;
			if (itemModel.getSections().size() != 0)
				section = itemModel.getSections().iterator().next().getSectionId();

			addStandartModels(request, section);

			log.info(">>>  Redirect to :" + PAGE_OK);

			log.debug("ATTRIBUTE_MODEL_TO_VIEW : + " + ATTRIBUTE_ITEM_MODEL_TO_VIEW + " befor JSP : "
					+ request.getAttribute(ATTRIBUTE_ITEM_MODEL_TO_VIEW));
			log.debug("ATTRIBUTE_MODEL_TO_VIEW : + " + ATTRIBUTE_MENU_MODEL_TO_VIEW + " befor JSP : "
					+ request.getAttribute(ATTRIBUTE_MENU_MODEL_TO_VIEW));

			getServletContext().getRequestDispatcher(PAGE_OK).include(request, response);

			log.debug("ATTRIBUTE_MODEL_TO_VIEW : + " + ATTRIBUTE_ITEM_MODEL_TO_VIEW + " after JSP : "
					+ request.getAttribute(ATTRIBUTE_ITEM_MODEL_TO_VIEW));
			log.debug("ATTRIBUTE_MODEL_TO_VIEW : + " + ATTRIBUTE_MENU_MODEL_TO_VIEW + " after JSP : "
					+ request.getAttribute(ATTRIBUTE_MENU_MODEL_TO_VIEW));

			return;

		} catch (DaoException | NumberFormatException e) {
			log.error(e);
			request.setAttribute(ATTRIBUTE_ERR_CODE, "404");
			request.setAttribute(ATTRIBUTE_ERR_STR, e.getMessage());
		}

		log.warn(">>>  Redirect to :" + PAGE_ERROR);
		getServletContext().getRequestDispatcher(PAGE_ERROR).include(request, response);
	}

}
