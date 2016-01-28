package io.pelle.mango.server.gwt;

import org.gwtwidgets.server.spring.DefaultRPCServiceExporterFactory;
import org.gwtwidgets.server.spring.RPCServiceExporter;

public class MangoRPCServiceExporterFactory extends DefaultRPCServiceExporterFactory {

	public RPCServiceExporter create() {
		MangoGWTRPCServiceExporter exporter = new MangoGWTRPCServiceExporter();
		return exporter;
	}
}
