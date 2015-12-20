package io.pelle.mango.server.util;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public class DocumentationBean implements ApplicationContextAware {

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

		Map<String, Object> restControllers = applicationContext.getBeansWithAnnotation(RestController.class);

		for (Object restController : restControllers.values()) {

			RequestMapping requestMapping = restController.getClass().getAnnotation(RequestMapping.class);

			if (requestMapping != null) {

			}
		}
	}

}
