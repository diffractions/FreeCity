package listeners;

import static inject.ApplicationContextHolder.getClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import controllers.CityController;
import dao.CityDao;
import dao.exceptions.DaoSystemException;
import entity.City;

/**
 * Application Lifecycle Listener implementation class CityMappingListener
 *
 */
@WebListener
public class CityMappingListener implements ServletContextListener {

	/**
	 * Default constructor.
	 */
	public CityMappingListener() {
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent sce) {
	}

	public static Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	public static final String APP_CTX_PATH = "project_context";
	public static final String CITY_DAO_BEAN_NAME = "cityDao";
	private static ApplicationContext context;

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {

		String path = sce.getServletContext().getInitParameter(APP_CTX_PATH);
		if (path == null) {
		}
		log.debug(">>>  APP_CTX_PATH IN CityMappingListener : " + APP_CTX_PATH + ">>>  Path : " + path);
		context = getClassPathXmlApplicationContext(path);

		try {
			for (final City city : ((CityDao) context.getBean(CITY_DAO_BEAN_NAME)).getCityAll()) {
				ServletRegistration.Dynamic registration = sce.getServletContext().addServlet(city.getName(),
						new CityController(city));
				registration.addMapping("/" + city.getShortName() + "/*");
			}
		} catch (BeansException | DaoSystemException e) {
			e.printStackTrace();
		}
	}
}
