package io.pelle.mango.client.web.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class I18NProxy {

	public static <I> I create(Class<I> interfaceClass) {

		InvocationHandler handler = new InvocationHandler() {

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				return "xxx";
			}
		};

		@SuppressWarnings("unchecked")
		I proxy = (I) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[] { interfaceClass }, handler);

		return proxy;
	}

}
