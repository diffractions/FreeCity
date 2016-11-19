package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.City;

public class CityController extends RootController {

	private static final long serialVersionUID = 1L;
	private static final String REQUEST_URI_LIST = "/list";
	private static final String REQUEST_URI_ENTITY = "/entity";

	private City city;

	public CityController(City city) {
		super();
		this.city = city;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("city_selected", city);
		if (req.getRequestURI().contains(REQUEST_URI_LIST)) {

			log.trace("include response from " + REQUEST_URI_LIST);
			req.getRequestDispatcher(REQUEST_URI_LIST).include(req, resp);

		} else if (req.getRequestURI().contains(REQUEST_URI_ENTITY)) {

			log.trace("include response from " + REQUEST_URI_ENTITY);
			req.getRequestDispatcher(REQUEST_URI_ENTITY).include(req, resp);

		} else if (req.getRequestURI().endsWith("/" + city.getShortName())) {
			log.trace("include response from " + "empty city");
			req.getRequestDispatcher(REQUEST_URI_LIST).include(req, resp);

		} else if (req.getRequestURI().endsWith("/" + city.getShortName() + "/")) {
			log.trace("replace uri " + req.getRequestURI() + " --> " + req.getRequestURL().toString()
					.replaceAll("/" + city.getShortName() + "/", "/" + city.getShortName()));
			resp.sendRedirect(req.getRequestURL().toString().replaceAll("/" + city.getShortName() + "/",
					"/" + city.getShortName()));

		}

		else {
			log.trace("replace uri " + req.getRequestURI() + " --> "
					+ req.getRequestURL().toString().replaceAll("/" + city.getShortName() + "/", "/"));
			resp.sendRedirect(req.getRequestURL().toString().replaceAll("/" + city.getShortName() + "/", "/"));
		}
	}
}
