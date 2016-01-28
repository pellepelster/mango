package io.pelle.mango.server.base.xml;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.server.endpoint.mapping.PayloadRootAnnotationMethodEndpointMapping;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.server.SoapMessageDispatcher;
import org.springframework.ws.transport.http.WebServiceMessageReceiverHandlerAdapter;
import org.springframework.ws.transport.http.WsdlDefinitionHandlerAdapter;

@Configuration
public class MangoBaseXmlImportExportApplicationContext {

	@Bean
	public PayloadRootAnnotationMethodEndpointMapping payloadRootAnnotationMethodEndpointMapping() {
		return new PayloadRootAnnotationMethodEndpointMapping();
	}

	@Bean
	public SaajSoapMessageFactory saajSoapMessageFactory() {
		return new SaajSoapMessageFactory();
	}

	@Bean
	public WebServiceMessageReceiverHandlerAdapter webServiceMessageReceiverHandlerAdapter(SaajSoapMessageFactory saajSoapMessageFactory) {
		WebServiceMessageReceiverHandlerAdapter result = new WebServiceMessageReceiverHandlerAdapter();
		result.setMessageFactory(saajSoapMessageFactory);
		return result;
	}

	@Bean
	public SoapMessageDispatcher soapMessageDispatcher() {
		return new SoapMessageDispatcher();
	}

	@Bean
	public WsdlDefinitionHandlerAdapter wsdlDefinitionHandlerAdapter() {
		return new WsdlDefinitionHandlerAdapter();
	}

}
