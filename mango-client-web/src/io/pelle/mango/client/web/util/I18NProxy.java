package io.pelle.mango.client.web.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.i18n.client.Messages.DefaultMessage;

public class I18NProxy {

	public static <I> I create(Class<I> interfaceClass) {

		final Map<String, String> i18nTexts = new HashMap<String, String>();

		for (Method method : interfaceClass.getMethods()) {

			DefaultMessage defaultMessage = method.getAnnotation(DefaultMessage.class);

			if (defaultMessage != null) {
				i18nTexts.put(method.getName(), defaultMessage.value());
			}
		}

		InvocationHandler handler = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				String i18nText = i18nTexts.get(method.getName());
				return MessageFormat.format(i18nText, args);
			}
		};

		@SuppressWarnings("unchecked")
		I proxy = (I) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[] { interfaceClass }, handler);

		return proxy;
	}

}
