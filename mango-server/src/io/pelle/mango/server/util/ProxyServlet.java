package io.pelle.mango.server.util;

import io.pelle.mango.client.base.util.XPathUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.log4j.Logger;

/**
 * ProxyServlet, based on the ProxyServlet from
 * https://code.google.com/p/jpublish/
 *
 * ProxyServlet from
 * http://edwardstx.net/wiki/attach/HttpProxyServlet/ProxyServlet.java (This
 * seems to be a derivative of Noodle -- http://noodle.tigris.org/)
 *
 * Patched to skip "Transfer-Encoding: chunked" headers, avoid double slashes in
 * proxied URLs, handle GZip and allow GWT RPC.
 */
public class ProxyServlet extends HttpServlet {

	private final static Logger LOG = Logger.getLogger(ProxyServlet.class);

	private static final int FOUR_KB = 4196;

	/**
	 * Serialization UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Key for redirect location header.
	 */
	private static final String STRING_LOCATION_HEADER_NAME = "Location";

	/**
	 * Key for content type header.
	 */
	private static final String STRING_CONTENT_ENGINE_HEADER_NAME = "Content-Type";

	/**
	 * Key for content length header.
	 */
	private static final String STRING_CONTENT_LENGTH_HEADER_NAME = "Content-Length";

	/**
	 * Key for host header
	 */
	private static final String STRING_HOST_HEADER_NAME = "Host";

	/**
	 * The directory to use to temporarily store uploaded files
	 */
	private static final File FILE_UPLOAD_TEMP_DIRECTORY = new File(System.getProperty("java.io.tmpdir"));

	/**
	 * Key for proxy servlet information
	 */
	public static final String PROXY_SERVLET_URL_HEADER_NAME = "X-ProxyServlet-Path";

	/**
	 * Original url
	 */
	public static final String PROXY_SERVLET_ORIGINAL_BASE_URL_HEADER_NAME = "X-ProxyServlet-OriginalPath";

	/**
	 * The host to which we are proxying requests. Default value is "localhost".
	 */
	private String proxyHost = "localhost";

	/**
	 * The port on the proxy host to wihch we are proxying requests. Default
	 * value is 80.
	 */
	private int proxyPort = 80;

	/**
	 * The (optional) path on the proxy host to wihch we are proxying requests.
	 * Default value is "".
	 */
	private String proxyPath = "";

	/**
	 * Setting that allows removing the initial path from client. Allows
	 * specifying /twitter/* as synonym for twitter.com.
	 */
	private boolean removePrefix;

	/**
	 * The maximum size for uploaded files in bytes. Default value is 5MB.
	 */
	private int intMaxFileUploadSize = 5 * 1024 * 1024;
	private boolean isSecure;
	private boolean followRedirects;

	/**
	 * Performs an HTTP GET request
	 *
	 * @param httpServletRequest
	 *            The {@link HttpServletRequest} object passed in by the servlet
	 *            engine representing the client request to be proxied
	 * @param httpServletResponse
	 *            The {@link HttpServletResponse} object by which we can send a
	 *            proxied response to the client
	 */
	@Override
	public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
		// Create a GET request
		String proxyUrl = getProxyURL(httpServletRequest);

		logHttpServletRequest("GET", httpServletRequest);

		GetMethod getMethodProxyRequest = new GetMethod(proxyUrl);

		// Forward the request headers
		setProxyRequestHeaders(httpServletRequest, getMethodProxyRequest);
		setProxyRequestCookies(httpServletRequest, getMethodProxyRequest);

		// Execute the proxy request
		this.executeProxyRequest(getMethodProxyRequest, httpServletRequest, httpServletResponse);
	}

	/**
	 * Performs an HTTP POST request
	 *
	 * @param httpServletRequest
	 *            The {@link HttpServletRequest} object passed in by the servlet
	 *            engine representing the client request to be proxied
	 * @param httpServletResponse
	 *            The {@link HttpServletResponse} object by which we can send a
	 *            proxied response to the client
	 */
	@Override
	public void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
		// Create a standard POST request
		String contentType = httpServletRequest.getContentType();
		String proxyUrl = getProxyURL(httpServletRequest);

		logHttpServletRequest("POST", httpServletRequest);

		PostMethod postMethodProxyRequest = new PostMethod(proxyUrl);

		// Forward the request headers
		setProxyRequestHeaders(httpServletRequest, postMethodProxyRequest);
		setProxyRequestCookies(httpServletRequest, postMethodProxyRequest);

		// Check if this is a mulitpart (file upload) POST
		if (ServletFileUpload.isMultipartContent(httpServletRequest)) {
			handleMultipartPost(postMethodProxyRequest, httpServletRequest);
		} else {
			if (contentType == null || PostMethod.FORM_URL_ENCODED_CONTENT_TYPE.equals(contentType)) {
				handleStandardPost(postMethodProxyRequest, httpServletRequest);
			} else {
				handleContentPost(postMethodProxyRequest, httpServletRequest);
			}
		}

		// Execute the proxy request
		this.executeProxyRequest(postMethodProxyRequest, httpServletRequest, httpServletResponse);
	}

	/**
	 * Executes the {@link HttpMethod} passed in and sends the proxy response
	 * back to the client via the given {@link HttpServletResponse}
	 *
	 * @param httpMethodProxyRequest
	 *            An object representing the proxy request to be made
	 * @param httpServletResponse
	 *            An object by which we can send the proxied response back to
	 *            the client
	 * @throws IOException
	 *             Can be thrown by the {@link HttpClient}.executeMethod
	 * @throws ServletException
	 *             Can be thrown to indicate that another error has occurred
	 */
	private void executeProxyRequest(HttpMethod httpMethodProxyRequest, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
		// Create a default HttpClient
		HttpClient httpClient = new HttpClient();
		httpMethodProxyRequest.setFollowRedirects(false);

		// Execute the request
		int proxyResponseCode = httpClient.executeMethod(httpMethodProxyRequest);
		String response = httpMethodProxyRequest.getResponseBodyAsString();

		// Check if the proxy response is a redirect
		// The following code is adapted from
		// org.tigris.noodle.filters.CheckForRedirect
		// Hooray for open source software
		if (proxyResponseCode >= HttpServletResponse.SC_MULTIPLE_CHOICES /* 300 */&& proxyResponseCode < HttpServletResponse.SC_NOT_MODIFIED /* 304 */) {
			String statusCode = Integer.toString(proxyResponseCode);
			String location = httpMethodProxyRequest.getResponseHeader(STRING_LOCATION_HEADER_NAME).getValue();

			if (location == null) {
				throw new ServletException("Received status code: " + statusCode + " but no " + STRING_LOCATION_HEADER_NAME + " header was found in the response");
			}
			// Modify the redirect to go to this proxy servlet rather that the
			// proxied host
			String hostName = httpServletRequest.getServerName();
			if (httpServletRequest.getServerPort() != 80) {
				hostName += ":" + httpServletRequest.getServerPort();
			}
			hostName += httpServletRequest.getContextPath();

			if (this.followRedirects) {
				if (location.contains("jsessionid")) {
					Cookie cookie = new Cookie("JSESSIONID", location.substring(location.indexOf("jsessionid=") + 11));
					cookie.setPath("/");
					httpServletResponse.addCookie(cookie);
				} else if (httpMethodProxyRequest.getResponseHeader("Set-Cookie") != null) {
					Header header = httpMethodProxyRequest.getResponseHeader("Set-Cookie");
					String[] cookieDetails = header.getValue().split(";");
					String[] nameValue = cookieDetails[0].split("=");

					Cookie cookie = new Cookie(nameValue[0], nameValue[1]);
					cookie.setPath("/");
					httpServletResponse.addCookie(cookie);
				}
				httpServletResponse.sendRedirect(location.replace(getProxyHostAndPort() + getProxyPath(), hostName));

				return;
			}
		} else if (proxyResponseCode == HttpServletResponse.SC_NOT_MODIFIED) {
			// 304 needs special handling. See:
			// http://www.ics.uci.edu/pub/ietf/http/rfc1945.html#Code304
			// We get a 304 whenever passed an 'If-Modified-Since'
			// header and the data on disk has not changed; server
			// responds w/ a 304 saying I'm not going to send the
			// body because the file has not changed.
			httpServletResponse.setIntHeader(STRING_CONTENT_LENGTH_HEADER_NAME, 0);
			httpServletResponse.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return;
		}

		// Pass the response code back to the client
		httpServletResponse.setStatus(proxyResponseCode);

		// Pass response headers back to the client
		Header[] headerArrayResponse = httpMethodProxyRequest.getResponseHeaders();
		for (Header header : headerArrayResponse) {
			if (header.getName().equals("Transfer-Encoding") && header.getValue().equals("chunked") || header.getName().equals("Content-Encoding") && header.getValue().equals("gzip") || // don't
					// copy
					// gzip
					// header
					header.getName().equals("WWW-Authenticate")) { // don't copy
				// WWW-Authenticate
				// header so
				// browser
				// doesn't
				// prompt on
				// failed
				// basic
				// auth
				// proxy
				// servlet
				// does not
				// support
				// chunked
				// encoding
			} else {
				httpServletResponse.setHeader(header.getName(), header.getValue());
			}
		}

		List<Header> responseHeaders = Arrays.asList(headerArrayResponse);

		if (isBodyParameterGzipped(responseHeaders)) {
			if (!this.followRedirects && proxyResponseCode == HttpServletResponse.SC_MOVED_TEMPORARILY) {
				response = httpMethodProxyRequest.getResponseHeader(STRING_LOCATION_HEADER_NAME).getValue();
				httpServletResponse.setStatus(HttpServletResponse.SC_OK);
				proxyResponseCode = HttpServletResponse.SC_OK;
				httpServletResponse.setHeader(STRING_LOCATION_HEADER_NAME, response);
			} else {
				response = new String(ungzip(httpMethodProxyRequest.getResponseBody()));
			}
			httpServletResponse.setContentLength(response.length());
		}

		// Send the content to the client
		// debug("Received status code: " + proxyResponseCode, "Response: " +
		// response);

		httpServletResponse.getWriter().write(response);
	}

	private int getMaxFileUploadSize() {
		return this.intMaxFileUploadSize;
	}

	private String getProxyHost() {
		return this.proxyHost;
	}

	private String getProxyHostAndPort() {
		if (getProxyPort() == 80) {
			return getProxyHost();
		} else {
			return String.format("%s:%d", getProxyHost(), getProxyPort());
		}
	}

	private String getProxyPath() {
		return this.proxyPath;
	}

	private int getProxyPort() {
		return this.proxyPort;
	}

	// Accessors
	private String getProxyURL() {
		String protocol = (this.isSecure) ? "https://" : "http://";
		String proxyURL = protocol + getProxyHostAndPort();

		proxyURL = XPathUtil.combine(proxyURL, getProxyPath());

		return proxyURL;
	}

	private String getProxyURL(HttpServletRequest httpServletRequest) {
		String proxyURL = getProxyURL();

		// simply use whatever servlet path that was part of the request as
		// opposed to getting a preset/configurable proxy path
		if (!this.removePrefix) {
			proxyURL = XPathUtil.combine(proxyURL, httpServletRequest.getServletPath());
		}

		proxyURL = XPathUtil.combine(proxyURL, httpServletRequest.getPathInfo());

		// Handle the query string
		if (httpServletRequest.getQueryString() != null) {
			proxyURL += "?" + httpServletRequest.getQueryString();
		}

		return proxyURL;
	}

	@Override
	public String getServletInfo() {
		return "GWT Proxy Servlet";
	}

	/**
	 * Sets up the given {@link PostMethod} to send the same content POST data
	 * (JSON, XML, etc.) as was sent in the given {@link HttpServletRequest}
	 *
	 * @param postMethodProxyRequest
	 *            The {@link PostMethod} that we are configuring to send a
	 *            standard POST request
	 * @param httpServletRequest
	 *            The {@link HttpServletRequest} that contains the POST data to
	 *            be sent via the {@link PostMethod}
	 */
	private void handleContentPost(PostMethod postMethodProxyRequest, HttpServletRequest httpServletRequest) throws IOException, ServletException {
		StringBuilder content = new StringBuilder();
		BufferedReader reader = httpServletRequest.getReader();
		for (;;) {
			String line = reader.readLine();
			if (line == null) {
				break;
			}
			content.append(line);
		}

		String contentType = httpServletRequest.getContentType();
		String postContent = content.toString();

		if (contentType.startsWith("text/x-gwt-rpc")) {
			String clientHost = httpServletRequest.getLocalName();
			if (clientHost.equals("127.0.0.1")) {
				clientHost = "localhost";
			}

			int clientPort = httpServletRequest.getLocalPort();
			String clientUrl = clientHost + ((clientPort != 80) ? ":" + clientPort : "");
			String serverUrl = this.proxyHost + ((this.proxyPort != 80) ? ":" + this.proxyPort : "") + httpServletRequest.getServletPath();

			postContent = postContent.replace(clientUrl, serverUrl);
		}

		String encoding = httpServletRequest.getCharacterEncoding();
		// debug("POST Content Type: " + contentType + " Encoding: " + encoding,
		// "Content: " + postContent);
		StringRequestEntity entity;
		try {
			entity = new StringRequestEntity(postContent, contentType, encoding);
		} catch (UnsupportedEncodingException e) {
			throw new ServletException(e);
		}
		// Set the proxy request POST data
		postMethodProxyRequest.setRequestEntity(entity);
	}

	/**
	 * Sets up the given {@link PostMethod} to send the same multipart POST data
	 * as was sent in the given {@link HttpServletRequest}
	 *
	 * @param postMethodProxyRequest
	 *            The {@link PostMethod} that we are configuring to send a
	 *            multipart POST request
	 * @param httpServletRequest
	 *            The {@link HttpServletRequest} that contains the mutlipart
	 *            POST data to be sent via the {@link PostMethod}
	 */
	@SuppressWarnings("unchecked")
	private void handleMultipartPost(PostMethod postMethodProxyRequest, HttpServletRequest httpServletRequest) throws ServletException {
		// Create a factory for disk-based file items
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		// Set factory constraints
		diskFileItemFactory.setSizeThreshold(this.getMaxFileUploadSize());
		diskFileItemFactory.setRepository(FILE_UPLOAD_TEMP_DIRECTORY);
		// Create a new file upload handler
		ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
		// Parse the request
		try {
			// Get the multipart items as a list
			List<FileItem> fileItems = servletFileUpload.parseRequest(httpServletRequest);
			// Create a list to hold all of the parts
			List<Part> parts = new ArrayList<Part>();
			// Iterate the multipart items list
			for (FileItem currentFileItem : fileItems) {
				// If the current item is a form field, then create a string
				// part
				if (currentFileItem.isFormField()) {
					StringPart stringPart = new StringPart(currentFileItem.getFieldName(), // The
							// field
							// name
							currentFileItem.getString() // The field value
					);
					// Add the part to the list
					parts.add(stringPart);
				} else {
					// The item is a file upload, so we create a FilePart
					FilePart filePart = new FilePart(currentFileItem.getFieldName(), // The
							// field
							// name
							new ByteArrayPartSource(currentFileItem.getName(), // The
									// uploaded
									// file
									// name
									currentFileItem.get() // The uploaded file
							// contents
							));
					// Add the part to the list
					parts.add(filePart);
				}
			}

			MultipartRequestEntity multipartRequestEntity = new MultipartRequestEntity(parts.toArray(new Part[] {}), postMethodProxyRequest.getParams());
			postMethodProxyRequest.setRequestEntity(multipartRequestEntity);
			// The current content-type header (received from the client) IS of
			// type "multipart/form-data", but the content-type header also
			// contains the chunk boundary string of the chunks. Currently, this
			// header is using the boundary of the client request, since we
			// blindly copied all headers from the client request to the proxy
			// request. However, we are creating a new request with a new chunk
			// boundary string, so it is necessary that we re-set the
			// content-type string to reflect the new chunk boundary string
			postMethodProxyRequest.setRequestHeader(STRING_CONTENT_ENGINE_HEADER_NAME, multipartRequestEntity.getContentType());
		} catch (FileUploadException fileUploadException) {
			throw new ServletException(fileUploadException);
		}
	}

	/**
	 * Sets up the given {@link PostMethod} to send the same standard POST data
	 * as was sent in the given {@link HttpServletRequest}
	 *
	 * @param postMethodProxyRequest
	 *            The {@link PostMethod} that we are configuring to send a
	 *            standard POST request
	 * @param httpServletRequest
	 *            The {@link HttpServletRequest} that contains the POST data to
	 *            be sent via the {@link PostMethod}
	 */
	@SuppressWarnings("unchecked")
	private void handleStandardPost(PostMethod postMethodProxyRequest, HttpServletRequest httpServletRequest) {
		// Get the client POST data as a Map
		Map<String, String[]> postParameters = httpServletRequest.getParameterMap();

		// Create a List to hold the NameValuePairs to be passed to the
		// PostMethod
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		// Iterate the parameter names
		for (String parameterName : postParameters.keySet()) {
			// Iterate the values for each parameter name
			String[] parameterValues = postParameters.get(parameterName);
			for (String paramterValue : parameterValues) {
				// Create a NameValuePair and store in list
				NameValuePair nameValuePair = new NameValuePair(parameterName, paramterValue);
				nameValuePairs.add(nameValuePair);
			}
		}
		// Set the proxy request POST data
		postMethodProxyRequest.setRequestBody(nameValuePairs.toArray(new NameValuePair[] {}));
	}

	/**
	 * Initialize the <code>ProxyServlet</code>
	 *
	 * @param servletConfig
	 *            The Servlet configuration passed in by the servlet container
	 */
	@Override
	public void init(ServletConfig servletConfig) {
		// Get the proxy host
		String stringProxyHostNew = servletConfig.getInitParameter("proxyHost");
		if (stringProxyHostNew == null || stringProxyHostNew.length() == 0) {
			throw new IllegalArgumentException("Proxy host not set, please set init-param 'proxyHost' in web.xml");
		}
		this.setProxyHost(stringProxyHostNew);
		// Get the proxy port if specified
		String stringProxyPortNew = servletConfig.getInitParameter("proxyPort");
		if (stringProxyPortNew != null && stringProxyPortNew.length() > 0) {
			this.setProxyPort(Integer.parseInt(stringProxyPortNew));
		}
		// Get the proxy path if specified
		String stringProxyPathNew = servletConfig.getInitParameter("proxyPath");
		if (stringProxyPathNew != null && stringProxyPathNew.length() > 0) {
			this.setProxyPath(stringProxyPathNew);
		}
		// Get the maximum file upload size if specified
		String stringMaxFileUploadSize = servletConfig.getInitParameter("maxFileUploadSize");
		if (stringMaxFileUploadSize != null && stringMaxFileUploadSize.length() > 0) {
			this.setMaxFileUploadSize(Integer.parseInt(stringMaxFileUploadSize));
		}
	}

	/**
	 * The response body will be assumed to be gzipped if the GZIP header has
	 * been set.
	 *
	 * @param responseHeaders
	 *            of response headers
	 * @return true if the body is gzipped
	 */
	private boolean isBodyParameterGzipped(List<Header> responseHeaders) {
		for (Header header : responseHeaders) {
			if (header.getValue().equals("gzip")) {
				return true;
			}
		}
		return false;
	}

	private void logHttpServletRequest(String requestType, HttpServletRequest httpServletRequest) {
		LOG.info(String.format("%s %s", requestType, httpServletRequest.getRequestURL().toString()));
	}

	protected void setFollowRedirects(boolean followRedirects) {
		this.followRedirects = followRedirects;
	}

	protected void setMaxFileUploadSize(int intMaxFileUploadSizeNew) {
		this.intMaxFileUploadSize = intMaxFileUploadSizeNew;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public void setProxyPath(String proxyPath) {
		this.proxyPath = proxyPath;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	/**
	 * Retrieves all of the cookies from the servlet request and sets them on
	 * the proxy request
	 *
	 * @param httpServletRequest
	 *            The request object representing the client's request to the
	 *            servlet engine
	 * @param httpMethodProxyRequest
	 *            The request that we are about to send to the proxy host
	 */
	private void setProxyRequestCookies(HttpServletRequest httpServletRequest, HttpMethod httpMethodProxyRequest) {
		Cookie[] cookies = httpServletRequest.getCookies();

		if (cookies == null) {
			return;
		}

		for (Cookie cookie : cookies) {
			cookie.setDomain(this.proxyHost);
			cookie.setPath(httpServletRequest.getServletPath());
			httpMethodProxyRequest.setRequestHeader("Cookie", String.format("%s=%s; Path=", cookie.getName(), cookie.getValue(), cookie.getPath()));
		}
	}

	/**
	 * Retrieves all of the headers from the servlet request and sets them on
	 * the proxy request
	 *
	 * @param httpServletRequest
	 *            The request object representing the client's request to the
	 *            servlet engine
	 * @param httpMethodProxyRequest
	 *            The request that we are about to send to the proxy host
	 */
	private void setProxyRequestHeaders(HttpServletRequest httpServletRequest, HttpMethod httpMethodProxyRequest) {
		// Get an Enumeration of all of the header names sent by the client
		@SuppressWarnings("unchecked")
		Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			if (headerName.equalsIgnoreCase(STRING_CONTENT_LENGTH_HEADER_NAME)) {
				continue;
			}

			// As per the Java Servlet API 2.5 documentation:
			// Some headers, such as Accept-Language can be sent by clients
			// as several headers each with a different value rather than
			// sending the header as a comma separated list.
			// Thus, we get an Enumeration of the header values sent by the
			// client
			@SuppressWarnings("unchecked")
			Enumeration<String> headerValues = httpServletRequest.getHeaders(headerName);
			while (headerValues.hasMoreElements()) {
				String headerValue = headerValues.nextElement();

				// In case the proxy host is running multiple virtual servers,
				// rewrite the Host header to ensure that we get content from
				// the correct virtual server
				if (headerName.equalsIgnoreCase(STRING_HOST_HEADER_NAME)) {
					headerValue = getProxyHostAndPort();
				}

				Header header = new Header(headerName, headerValue);
				// Set the same header on the proxy request
				httpMethodProxyRequest.setRequestHeader(header);
			}
		}

		Header proxyHeader = new Header(PROXY_SERVLET_URL_HEADER_NAME, getProxyURL());
		Header originalUrlHeader = new Header(PROXY_SERVLET_ORIGINAL_BASE_URL_HEADER_NAME, getOriginalURL(httpServletRequest));

		// Set the same header on the proxy request
		httpMethodProxyRequest.setRequestHeader(proxyHeader);
		httpMethodProxyRequest.setRequestHeader(originalUrlHeader);
	}

	private String getOriginalURL(HttpServletRequest httpServletRequest) {
		return String.format("%s://%s:%s", httpServletRequest.getScheme(), httpServletRequest.getServerName(), httpServletRequest.getServerPort());
	}

	protected void setRemovePrefix(boolean removePrefix) {
		this.removePrefix = removePrefix;
	}

	protected void setSecure(boolean secure) {
		this.isSecure = secure;
	}

	/**
	 * A highly performant ungzip implementation. Do not refactor this without
	 * taking new timings. See ElementTest in ehcache for timings
	 *
	 * @param gzipped
	 *            the gzipped content
	 * @return an ungzipped byte[]
	 * @throws java.io.IOException
	 *             when something bad happens
	 */
	private byte[] ungzip(final byte[] gzipped) throws IOException {
		final GZIPInputStream inputStream = new GZIPInputStream(new ByteArrayInputStream(gzipped));
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(gzipped.length);
		final byte[] buffer = new byte[FOUR_KB];
		int bytesRead = 0;

		while (bytesRead != -1) {
			bytesRead = inputStream.read(buffer, 0, FOUR_KB);
			if (bytesRead != -1) {
				byteArrayOutputStream.write(buffer, 0, bytesRead);
			}
		}

		byte[] ungzipped = byteArrayOutputStream.toByteArray();
		inputStream.close();
		byteArrayOutputStream.close();

		return ungzipped;
	}
}