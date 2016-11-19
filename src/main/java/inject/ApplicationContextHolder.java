package inject;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextHolder {
	private static final Map<String, ApplicationContext> pathToContexteRepository = new HashMap<String, ApplicationContext>();
	public static synchronized ApplicationContext getClassPathXmlApplicationContext(String path){
		if(!pathToContexteRepository.containsKey(path)){
			pathToContexteRepository.put(path, new ClassPathXmlApplicationContext(path));
		}
		return pathToContexteRepository.get(path);
	} 
}
