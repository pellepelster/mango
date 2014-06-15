package io.pelle.mango.server.util;

import io.pelle.mango.client.base.util.XPathUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.user.server.rpc.SerializationPolicy;
import com.google.gwt.user.server.rpc.SerializationPolicyLoader;

public class ProxyServletAwareRemoteServiceServlet extends RemoteServiceServlet {

	private static final long serialVersionUID = -7065922031715639514L;

	/**
	 * Attempt to load the RPC serialization based on the path given by the
	 * proxy servlet
	 */
	@Override
	public SerializationPolicy doGetSerializationPolicy(HttpServletRequest request, String moduleBaseURL, String strongName) {
		String newModuleBaseUrl = moduleBaseURL;
		String originalModuleBaseURL = moduleBaseURL;

		if (request.getHeader(ProxyServlet.PROXY_SERVLET_URL_HEADER_NAME) != null) {
			try {
				newModuleBaseUrl = new URL(moduleBaseURL).getPath();
			} catch (MalformedURLException e) {
				throw new RuntimeException(String.format("invalid url '%s'", moduleBaseURL));
			}

			String proxyServletUrl = request.getHeader(ProxyServlet.PROXY_SERVLET_URL_HEADER_NAME);
			newModuleBaseUrl = XPathUtil.combine(proxyServletUrl, newModuleBaseUrl);
		}

		if (request.getHeader(ProxyServlet.PROXY_SERVLET_ORIGINAL_BASE_URL_HEADER_NAME) != null) {
			try {
				originalModuleBaseURL = new URL(moduleBaseURL).getPath();
				originalModuleBaseURL = originalModuleBaseURL.replaceFirst(request.getServletPath(), "");
			} catch (MalformedURLException e) {
				throw new RuntimeException(String.format("invalid url '%s'", moduleBaseURL));
			}

			String originalBaseUrl = request.getHeader(ProxyServlet.PROXY_SERVLET_ORIGINAL_BASE_URL_HEADER_NAME);
			originalModuleBaseURL = XPathUtil.combine(originalBaseUrl, originalModuleBaseURL);
		}

		try {
			return super.doGetSerializationPolicy(request, newModuleBaseUrl, strongName);
		} catch (Exception e) {
			return loadSerializationPolicy(this, request, originalModuleBaseURL, strongName);
		}
	}

	private SerializationPolicy loadSerializationPolicy(HttpServlet servlet, HttpServletRequest request, String moduleBaseURL, String strongName) {

		SerializationPolicy serializationPolicy = null;

		String serializationPolicyFilePath = SerializationPolicyLoader.getSerializationPolicyFileName(moduleBaseURL + strongName);

		// Open the RPC resource file and read its contents.
		InputStream is = null;
		try {
			is = new URL(serializationPolicyFilePath).openStream();

			if (is != null) {
				try {
					serializationPolicy = SerializationPolicyLoader.loadFromStream(is, null);
				} catch (ParseException e) {
					servlet.log("ERROR: Failed to parse the policy file '" + serializationPolicyFilePath + "'", e);
				} catch (IOException e) {
					servlet.log("ERROR: Could not read the policy file '" + serializationPolicyFilePath + "'", e);
				}
			} else {
				String message = "ERROR: The serialization policy file '" + serializationPolicyFilePath + "' was not found; did you forget to include it in this deployment?";
				servlet.log(message);
			}
		} catch (Exception e) {
			servlet.log("ERROR: Failed to open policy file '" + serializationPolicyFilePath + "'", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// Ignore this error
				}
			}
		}

		return serializationPolicy;
	}

}