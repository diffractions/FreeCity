package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.UserDao;
import dao.exceptions.DaoSystemException;
import entity.ShowedUser;
import inject.Inject;
import utils.EncodeUtils;

public class CreateUserController extends RootController {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	public static Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	public static final String PAGE_GET_OK = "/jsp/CreateUser.jsp";
	public static final String PAGE_ERROR_LOGIN = "login#registration";
	public static final String INDEX_PAGE = "";
	public static final String PAGE_NEW_USER_OK = "/";

	private final static String PARAM_USER_FIRST_NAME = "first_name";
	private final static String PARAM_USER_LAST_NAME = "last_name";
	private final static String PARAM_USER_EMAIL = "email";
	private final static String PARAM_USER_LOGIN = "login";
	private final static String PARAM_USER_PASSWORD = "password";

	public static final String ATTR_ERR_STR = "errorString";
	public static final String ATTR_ERR_CODE = "errorCode";

	public static final String ATTR_USER = "user";

	public static final String ATTR_ERR_MSG_REGISTRATION = "errorRegistrationString";
	public static final String ATTR_ERR_MSG_REGISTRATION_VALUE_USER_EXIST = "Користувач з логіном \'@login\' вже зареєстрований! </br> Встановіть новий логін.";
	public static final String ATTR_ERR_MSG_REGISTRATION_VALUE_ALL_FILDS_SHULD_BE_FULL = "Всі поля повині бути заповнені";
	public static final String ATTR_ERR_MSG_REGISTRATION_VALUE_IN_EXCEPTION = "Вибачте, виникла помилка при створенні нового користувача.</br> Спробуйте ще раз через кілька хвилин.";

	@Inject("userDao")
	public UserDao userDao;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String login = null;
		try {

			login = request.getParameter(PARAM_USER_LOGIN);
			String first_name = new String(request.getParameter(PARAM_USER_FIRST_NAME).getBytes(EncodeUtils.CP1251_CODE), EncodeUtils.UTF_8_CODE);
			String last_name = new String(request.getParameter(PARAM_USER_LAST_NAME).getBytes(EncodeUtils.CP1251_CODE), EncodeUtils.UTF_8_CODE);
			String email = request.getParameter(PARAM_USER_EMAIL);
			String password = request.getParameter(PARAM_USER_PASSWORD);
			ShowedUser user = null;
			log.debug("login " + login + ", " + " first_name " + first_name + ", " + " last_name " + last_name + ", "
					+ " email " + email + ", " + " password " + password);

			if (login.equals("") || first_name.equals("") || email.equals("") || password.equals("")) {
				request.setAttribute(ATTR_ERR_MSG_REGISTRATION,
						ATTR_ERR_MSG_REGISTRATION_VALUE_ALL_FILDS_SHULD_BE_FULL);
				response.sendRedirect(PAGE_ERROR_LOGIN);
				return;
			} else if (last_name == null || last_name.equals("")) {
				log.info("USER_LAST_NAME" + last_name);
				user = userDao.createUser(login, first_name, email, password);
			} else {
				user = userDao.createUser(login, first_name, last_name, email, password);
			}

			if (user != null) {
				log.info("Register new USER : " + user);
				request.getSession().setAttribute(ATTR_USER, user);
				response.sendRedirect(PAGE_NEW_USER_OK);
				return;
			}

			doGet(request, response);
			return;
		} catch (DaoSystemException e) {
			log.error(e); 
			if (e.getMessage().endsWith("UNIQUE'")) {
				request.getSession().setAttribute(ATTR_ERR_MSG_REGISTRATION,
						ATTR_ERR_MSG_REGISTRATION_VALUE_USER_EXIST.replaceAll("@login", login));
				response.sendRedirect(PAGE_ERROR_LOGIN);
				return;
			}else{
				request.getSession().setAttribute(ATTR_ERR_MSG_REGISTRATION,
						ATTR_ERR_MSG_REGISTRATION_VALUE_IN_EXCEPTION);
				response.sendRedirect(PAGE_ERROR_LOGIN);
				return;
			}
		}

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			if (request.getSession().getAttribute(ATTR_USER) != null) {
				log.info("User was register");
				response.sendRedirect(PAGE_NEW_USER_OK);
				return;
			}

			addStandartModels(request);
			log.info(">>>  Redirect to :" + PAGE_GET_OK);
			log.debug("ATTRIBUTE_MODEL_TO_VIEW :  " + ATTRIBUTE_MENU_MODEL_TO_VIEW + " befor JSP : "
					+ request.getAttribute(ATTRIBUTE_MENU_MODEL_TO_VIEW));
			getServletContext().getRequestDispatcher(PAGE_GET_OK).include(request, response);
			log.debug("ATTRIBUTE_MODEL_TO_VIEW :  " + ATTRIBUTE_MENU_MODEL_TO_VIEW + " after JSP : "
					+ request.getAttribute(ATTRIBUTE_MENU_MODEL_TO_VIEW));
			return;

		} catch (Exception e) {
			log.error(e);
			request.setAttribute(ATTR_ERR_CODE, "404");
			request.setAttribute(ATTR_ERR_STR, e.getMessage());
		}
		log.warn(">>>  Redirect to :" + INDEX_PAGE);
		response.sendRedirect(INDEX_PAGE);

	}

}
