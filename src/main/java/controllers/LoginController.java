package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.UserDao;
import dao.exceptions.DaoException;
import dao.exceptions.NoSuchEntityException;
import entity.ShowedUser;
import inject.Inject;
import static utils.HTTP_URL_Utils.decode;

public class LoginController extends RootController {

	private static final long serialVersionUID = 1L;

	public static Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	public static final String PAGE_AFTER_REGISTER_OK = "";
	public static final String PAGE_AFTER_EXIT_OK = "/";
	public static final String PAGE_LOGIN = "/jsp/Login.jsp";

	public static final String ATTR_USER = "user";
	public static final String PARAM_REDIRECT = "request";
	public static final String PARAM_LOGIN = "login";
	public static final String PARAM_PASSWORD = "password";

	public static final String ATTR_ERR_STR = "errorLoginString";
	public static final String ATTR_ERR_CODE = "errorCode";

	public static final String ATTR_ERR_MSG_LOGIN_VALUE_VRONG_NAME = "Не вірне ім'я або пароль.<br> Будь-ласка, введіть  дані для входу";
	public static final String ATTR_ERR_MSG_EXCEPTION = "Помилка входу";

	public final static String PARAM_ID = "id";
	public final static String PARAM_USER = "user";

	public static final String PARAM_NAME_EXIT = "logout";
	public static final String PARAM_NAME_EXIT_VALUE = "exit";

	@Inject("userDao")
	public UserDao userDao;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			String login = request.getParameter(PARAM_LOGIN);
			String rediresct_to = request.getParameter(PARAM_REDIRECT);
			String password = request.getParameter(PARAM_PASSWORD);

			ShowedUser user = null;
			if (login != null && password != null && (user = userDao.getUser(login, password)) != null) {

				log.info("Register new USER : " + user);
				request.getSession().setAttribute(ATTR_USER, user);

				log.debug("user last page : " + rediresct_to);
				rediresct_to = rediresct_to.equals("/login")
						? (PAGE_AFTER_REGISTER_OK + "?" + PARAM_USER + "=" + user.getUserId() ): decode(rediresct_to);
						
						
				log.info(">>>  Redirect to :" + rediresct_to);
				response.sendRedirect(rediresct_to);
				return;
			}

		} catch (NoSuchEntityException e) {
			log.error("user not found ", e);
			request.setAttribute(ATTR_ERR_STR, ATTR_ERR_MSG_LOGIN_VALUE_VRONG_NAME);
			doGet(request, response);
			return;
		} catch (DaoException e) {
			log.error(e);
			request.getSession().setAttribute(ATTR_ERR_CODE, "404");
			request.getSession().setAttribute(ATTR_ERR_STR, ATTR_ERR_MSG_EXCEPTION);
		}

		log.info(">>>  Redirect to :" + PAGE_AFTER_EXIT_OK);
		response.sendRedirect(PAGE_AFTER_EXIT_OK);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			if (request.getParameter(PARAM_NAME_EXIT) != null
					&& request.getParameter(PARAM_NAME_EXIT).equals(PARAM_NAME_EXIT_VALUE)
					&& request.getSession().getAttribute(ATTR_USER) != null) {
				request.getSession().removeAttribute(ATTR_USER);

				log.info(">>>  Redirect to :" + PAGE_AFTER_EXIT_OK);
				response.sendRedirect(PAGE_AFTER_EXIT_OK);
			}

			addStandartModels(request);
			log.debug("ATTRIBUTE_MODEL_TO_VIEW : + " + ATTRIBUTE_MENU_MODEL_TO_VIEW + " befor JSP : "
					+ request.getAttribute(ATTRIBUTE_MENU_MODEL_TO_VIEW));

			getServletContext().getRequestDispatcher(PAGE_LOGIN).include(request, response);

			log.debug("ATTRIBUTE_MODEL_TO_VIEW : + " + ATTRIBUTE_MENU_MODEL_TO_VIEW + " after JSP : "
					+ request.getAttribute(ATTRIBUTE_MENU_MODEL_TO_VIEW));

			return;

		} catch (Exception e) {
			log.error(e);
			request.getSession().setAttribute(ATTR_ERR_CODE, "404");
			request.getSession().setAttribute(ATTR_ERR_STR, ATTR_ERR_MSG_EXCEPTION);
		}

		log.info(">>>  Redirect to :" + PAGE_AFTER_EXIT_OK);
		response.sendRedirect(PAGE_AFTER_EXIT_OK);

	}

}
