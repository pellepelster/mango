package io.pelle.mango.server.gwt;

import javax.servlet.http.HttpServletRequest;

import org.gwtwidgets.server.spring.GWTRPCServiceExporter;

import com.google.gwt.user.server.rpc.SerializationPolicy;

@SuppressWarnings("serial")
public class MangoGWTRPCServiceExporter extends GWTRPCServiceExporter {

	@Override
	protected SerializationPolicy doGetSerializationPolicy(HttpServletRequest request, String moduleBaseURL, String strongName) {

		try {

			return super.doGetSerializationPolicy(request, moduleBaseURL, strongName);

		} catch (Exception e) {
			String url = getCodeServerPolicyUrl(strongName);
			if (url != null) {
				return loadPolicyFromCodeServer(url);
			}
		}

		return null;
	}

	@Override
	public String getServletName() {
		if (getServletConfig() == null) {
			return getServletContext().getServletContextName();
		} else {
			return super.getServletName();
		}
	}

}
