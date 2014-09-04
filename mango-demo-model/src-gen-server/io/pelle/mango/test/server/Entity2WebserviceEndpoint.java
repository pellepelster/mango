package io.pelle.mango.test.server;

import io.pelle.mango.server.base.xml.IXmlVOImporter;	

import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.w3c.dom.Element;

@org.springframework.ws.server.endpoint.annotation.Endpoint
public class Entity2WebserviceEndpoint {

	private static final String TARGET_NAMESPACE = "http://mangodemo/entity2";

	@org.springframework.beans.factory.annotation.Autowired
	private IXmlVOImporter xmlVOImporter;
	
	@PayloadRoot(localPart = "Entity2", namespace = TARGET_NAMESPACE)
	public @ResponsePayload Element importEntity(@RequestPayload Element element) {
		xmlVOImporter.importVOs(element);
		return element;
	}
	
	public void setXmlVOImporter(IXmlVOImporter xmlVOImporter) {
		this.xmlVOImporter = xmlVOImporter;
	}
}	
