package io.pelle.mango.server.gwt;

import io.pelle.mango.client.base.util.XPathUtil;
import io.pelle.mango.server.base.util.ProxyServletHeaders;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.gwtwidgets.server.spring.GWTRPCServiceExporter;

import com.google.gwt.user.server.rpc.SerializationPolicy;
import com.google.gwt.user.server.rpc.SerializationPolicyLoader;

@SuppressWarnings("serial")
public class MangoGWTRPCServiceExporter extends GWTRPCServiceExporter {

	@Override
	protected SerializationPolicy doGetSerializationPolicy(HttpServletRequest request, String moduleBaseURL, String strongName) {

		if (request.getHeader(ProxyServletHeaders.GWT_FORWARDED_TO) != null) {

			String gwtForwardedTo = request.getHeader(ProxyServletHeaders.GWT_FORWARDED_TO);

			try {
				URL url = new URL(moduleBaseURL);
				String newModuleBaseURl = gwtForwardedTo + url.getFile();
				SerializationPolicy serializationPolicy = super.doGetSerializationPolicy(request, newModuleBaseURl, strongName);

				if (serializationPolicy != null) {
					return serializationPolicy;
				}

			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}

		}

		String newModuleBaseUrl = moduleBaseURL;
		String originalModuleBaseURL = moduleBaseURL;

		if (request.getHeader(ProxyServletHeaders.PROXY_SERVLET_URL_HEADER_NAME) != null) {
			try {
				newModuleBaseUrl = new URL(moduleBaseURL).getPath();
			} catch (MalformedURLException e) {
				throw new RuntimeException(String.format("invalid url '%s'", moduleBaseURL));
			}

			String proxyServletUrl = request.getHeader(ProxyServletHeaders.PROXY_SERVLET_URL_HEADER_NAME);
			newModuleBaseUrl = XPathUtil.combine(proxyServletUrl, newModuleBaseUrl);
		}

		if (request.getHeader(ProxyServletHeaders.PROXY_SERVLET_ORIGINAL_BASE_URL_HEADER_NAME) != null) {
			try {
				originalModuleBaseURL = new URL(moduleBaseURL).getPath();
				originalModuleBaseURL = originalModuleBaseURL.replaceFirst(request.getServletPath(), "");
			} catch (MalformedURLException e) {
				throw new RuntimeException(String.format("invalid url '%s'", moduleBaseURL));
			}

			String originalBaseUrl = request.getHeader(ProxyServletHeaders.PROXY_SERVLET_ORIGINAL_BASE_URL_HEADER_NAME);
			originalModuleBaseURL = XPathUtil.combine(originalBaseUrl, originalModuleBaseURL);

			try {
				URL url = new URL(originalModuleBaseURL);

				String newUrl = originalBaseUrl + url.getFile();
				String fullUrl = SerializationPolicyLoader.getSerializationPolicyFileName(newUrl + strongName);
				SerializationPolicy serializationPolicy = loadPolicyFromCodeServer(fullUrl);

				if (serializationPolicy != null) {
					return serializationPolicy;
				}

			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}

		}

		return super.doGetSerializationPolicy(request, moduleBaseURL, strongName);

	}
}
