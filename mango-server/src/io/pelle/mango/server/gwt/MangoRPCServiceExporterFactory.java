package io.pelle.mango.server.gwt;

import org.gwtwidgets.server.spring.DefaultRPCServiceExporterFactory;
import org.gwtwidgets.server.spring.RPCServiceExporter;

public class MangoRPCServiceExporterFactory extends DefaultRPCServiceExporterFactory {

	public MangoRPCServiceExporterFactory() {
		super();
		
//		setModulePathTranslation(new ModulePathTranslation() {
//			
//			@Override
//			public String computeModuleBaseURL(HttpServletRequest request, String moduleBaseURL, String strongName) {
//				
//				String result = moduleBaseURL;
//				
//				if (request.getHeader(PROXY_SERVLET_ORIGINAL_BASE_URL_HEADER_NAME) != null)
//				{
//					String originalBaseUrl = request.getHeader(PROXY_SERVLET_ORIGINAL_BASE_URL_HEADER_NAME);
//					
//					String path1 = request.getServletContext().getContextPath();
//					String path2 = request.getServletContext().getContextPath();
//					
//					try {
//						URL url = new URL(moduleBaseURL);
//						result = originalBaseUrl + url.getFile();
//						
//					} catch (MalformedURLException e) {
//						throw new RuntimeException(e);
//					}
//					
//					
//				}
//				
//				return result;
//			}
//		});
	}

	public RPCServiceExporter create() {
		MangoGWTRPCServiceExporter exporter = new MangoGWTRPCServiceExporter();
		return exporter;
	}
}
