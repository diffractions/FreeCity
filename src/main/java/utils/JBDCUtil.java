package utils;

import org.apache.log4j.Logger;

public class JBDCUtil {

	public static Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	public static void closeQuaetly(AutoCloseable resources) throws Exception {
		if (resources != null) {
			log.debug("close " + resources.toString());
			resources.close();
		}
	}

	public static void closeQuaetly(AutoCloseable... resources) throws Exception {
		log.trace("close " + resources.toString());
		for (AutoCloseable res : resources) {
			closeQuaetly(res);
		}
	}

}
