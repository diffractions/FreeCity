package controllers;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;

import dao.ImgDao;
import dao.exceptions.DaoException;
import entity.Img;
import inject.Inject;

/**
 * Servlet implementation class ItemAllController
 */
public class UserImgController extends RootController {
	private static final long serialVersionUID = 1L;

	public static Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	public static final String PAGE_OK = "/jsp/EntityAll.jsp";
	public static final String ATTRIBUTE_ITEM_MODEL_TO_VIEW = "items";

	public static final String PAGE_ERROR = "/index.jsp";
	public static final String ATTRIBUTE_ERR_STR = "errorString";
	public static final String ATTRIBUTE_ERR_CODE = "errorCode";

	public static final String INPUT_REQUEST_PATH = "/user/img/";
	public static final String UPDTE_REQUEST_PATH = "/user/img/update/";

	private final static String PARAM_PHOTO = "photo";
	private final static String PARAM_ID = "id";
	/**
	 * @see HttpServlet#HttpServlet()
	 */

	@Inject("imgDao")
	public ImgDao imgDao;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			if (imgDao == null) {
				throw new DaoException("EntityDAO not found");
			}

			OutputStream out = response.getOutputStream();
			String fileNmae = request.getPathInfo().substring(1);

			log.trace("IMG FILE NAME - " + fileNmae);

			Img imgs = imgDao.getImgByName(fileNmae);

			if (imgs != null) {
				byte[] img = imgs.getImg(); 
				response.setContentLength(img.length);
				response.setContentType(imgs.getContentType());
				out.write(img);
				out.flush();
				out.close();
			}
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