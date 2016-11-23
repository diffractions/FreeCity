package listeners;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * Application Lifecycle Listener implementation class RequestListener
 *
 */
@WebListener
public class RequestListener implements ServletRequestListener, ServletRequestAttributeListener, AsyncListener {

	

	public static Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	
	
    /**
     * Default constructor. 
     */
    public RequestListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see AsyncListener#onComplete(AsyncEvent)
     */
    public void onComplete(AsyncEvent event) throws java.io.IOException { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletRequestListener#requestDestroyed(ServletRequestEvent)
     */
    public void requestDestroyed(ServletRequestEvent sre)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletRequestAttributeListener#attributeRemoved(ServletRequestAttributeEvent)
     */
    public void attributeRemoved(ServletRequestAttributeEvent srae)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see AsyncListener#onError(AsyncEvent)
     */
    public void onError(AsyncEvent event) throws java.io.IOException { 
         // TODO Auto-generated method stub
    }

	/**
     * @see AsyncListener#onStartAsync(AsyncEvent)
     */
    public void onStartAsync(AsyncEvent event) throws java.io.IOException { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletRequestListener#requestInitialized(ServletRequestEvent)
     */
    public void requestInitialized(ServletRequestEvent sre)  { 

		HttpServletRequest arg0 = (HttpServletRequest) sre.getServletRequest();
//		log.debug("New request"
//				+ " --> Addr: "
//				+ arg0.getRemoteAddr() + " --> Host : "
//				+ arg0.getRemoteHost() + " --> Port : "
//				+ arg0.getRemotePort() + " --> User : "
//				+ arg0.getRemoteUser() + " --> auth : " + arg0.getAuthType()
//				+ " --> PathInfo : " + arg0.getPathInfo()
//				+ " --> ServerName : " + arg0.getServerName());

		
		log.info("Request from"
				+ " --> Addr: " + arg0.getRemoteAddr() 
				+ " --> Host : "+ arg0.getRemoteHost() 
				+ " --> requ : " + arg0.getRequestURL());
		
		
		StringBuilder sb = new StringBuilder("Reqest Header");
		Enumeration<String> names = arg0.getHeaderNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			sb.append(" --> " + name + " : " + arg0.getHeader(name));
		}
		log.debug(sb);

		log.debug("Request URI " + arg0.getRequestURI()
				+ " --> Query String :"
				+  arg0.getQueryString()
				+ " --> Context Path : " 
				+ arg0.getContextPath());

		Map<String, String[]> parameters = arg0.getParameterMap();
		if (parameters.size() > 0) {
			StringBuilder sb1 = new StringBuilder(
					"PARAMETERS IN  REQUEST");
			for (Entry<String, String[]> parameter : parameters.entrySet()) {
				sb1.append("--> " + parameter.getKey() + " : "
						+ Arrays.toString(parameter.getValue()));
			}
			log.debug(sb1);
		}
    }

	/**
     * @see ServletRequestAttributeListener#attributeAdded(ServletRequestAttributeEvent)
     */
    public void attributeAdded(ServletRequestAttributeEvent srae)  { 
		log.debug("Request attribute added --> "
		+ "Atribute name:" + srae.getName()
		+ "-->  Atribute value:" + srae.getValue());

    }

	/**
     * @see AsyncListener#onTimeout(AsyncEvent)
     */
    public void onTimeout(AsyncEvent event) throws java.io.IOException { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletRequestAttributeListener#attributeReplaced(ServletRequestAttributeEvent)
     */
    public void attributeReplaced(ServletRequestAttributeEvent srae)  { 
         // TODO Auto-generated method stub
    }
	
}
