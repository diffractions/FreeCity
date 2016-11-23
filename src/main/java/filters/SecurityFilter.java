package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static utils.HTTP_URL_Utils.encode;

import org.apache.log4j.Logger;

/**
 * Servlet Filter implementation class SecurityFilter
 */
public class SecurityFilter implements Filter {

	public static Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	public static final String USER_ATTR = "user";

	/**
	 * Default constructor.
	 */
	public SecurityFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession(false);
		if (session != null && session.getAttribute(USER_ATTR) != null) {
			log.trace("request from register user");
			chain.doFilter(request, response);
		} else {

			log.info("request from UN register user, QUERY:" + ((HttpServletRequest) request).getQueryString() + " req:" +encode(((HttpServletRequest) request).getQueryString()) );
			((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath()
					+ "/login?request=" + ((HttpServletRequest) request).getRequestURI()
					+ encode("?"+((HttpServletRequest) request).getQueryString()));
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
