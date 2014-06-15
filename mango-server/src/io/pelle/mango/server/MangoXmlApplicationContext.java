package io.pelle.mango.server;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class MangoXmlApplicationContext extends XmlWebApplicationContext {

	@Override
	protected void initBeanDefinitionReader(XmlBeanDefinitionReader beanDefinitionReader) {
		super.initBeanDefinitionReader(beanDefinitionReader);
		beanDefinitionReader.setValidating(false);
	}
}