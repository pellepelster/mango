package io.pelle.mango.server.base.util;

public interface ProxyServletHeaders {

	/**
	 * Key for redirect location header.
	 */
	static final String STRING_LOCATION_HEADER_NAME = "Location";

	/**
	 * Key for content type header.
	 */
	static final String STRING_CONTENT_ENGINE_HEADER_NAME = "Content-Type";

	/**
	 * Key for content length header.
	 */
	static final String STRING_CONTENT_LENGTH_HEADER_NAME = "Content-Length";

	/**
	 * Key for host header
	 */
	static final String STRING_HOST_HEADER_NAME = "Host";

	/**
	 * Key for proxy servlet information
	 */
	static final String PROXY_SERVLET_URL_HEADER_NAME = "X-ProxyServlet-Path";

	static final String GWT_FORWARDED_TO = "x-gwt-forwarded-to";

	/**
	 * Original url
	 */
	static final String PROXY_SERVLET_ORIGINAL_BASE_URL_HEADER_NAME = "X-ProxyServlet-OriginalPath";

}