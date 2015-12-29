package io.pelle.mango.demo.server;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMethod;

import io.pelle.mango.client.web.modules.dictionary.editor.DictionaryEditor;
import io.pelle.mango.demo.server.resttest1.RestService1RestController;
import io.pelle.mango.demo.server.resttest2.RestService2RestController;
import io.pelle.mango.server.documentation.DocumentationReflectionFactory;
import io.pelle.mango.server.documentation.RestAttributeDocumentation;
import io.pelle.mango.server.documentation.RestMethodDocumentation;
import io.pelle.mango.server.documentation.RestResponseDocumentation;
import io.pelle.mango.server.documentation.RestServiceDocumentation;
import io.pelle.mango.server.documentation.RestTypeDocumentation;

public class DocumentationReflectionUtilsTest {

	@Test
	public void testInvalidClass() {
		Assert.assertNull(DocumentationReflectionFactory.getDocumentationFor(DictionaryEditor.class));
	}

	@Test
	public void testRestService1RestControllerMethod1PostDocumentation() {

		RestServiceDocumentation service = DocumentationReflectionFactory.getDocumentationFor(RestService1RestController.class);

		// method1 (POST)
		RestMethodDocumentation method = service.getMethods().get(1);
		assertArrayEquals(new RequestMethod[] { RequestMethod.POST }, method.getHttpMethods().toArray());
		assertArrayEquals(new String[] { "method1" }, method.getPaths().toArray());
		
		// response
		RestResponseDocumentation response = method.getResponse();
		assertTrue(response.getType().isPrimitive());
		assertEquals("Boolean", response.getType().getTypeName());
		assertEquals("xxx", response.getShortDescription());
		assertEquals("yyy", response.getDescription());
	}

	@Test
	public void testRestService1RestControllerMethod1GetDocumentation() {

		RestServiceDocumentation service = DocumentationReflectionFactory.getDocumentationFor(RestService1RestController.class);

		// method1 (GET)
		RestMethodDocumentation method = service.getMethods().get(0);
		assertArrayEquals(new RequestMethod[] { RequestMethod.GET }, method.getHttpMethods().toArray());
		assertArrayEquals(new String[] { "method1" }, method.getPaths().toArray());
		
		// response
		RestTypeDocumentation response = method.getResponse().getType();
		assertTrue(response.isPrimitive());
		assertEquals("Boolean", response.getTypeName());

		// check attributes
		assertEquals(1, method.getAttributes().size());
		RestAttributeDocumentation attributeDocumentation = method.getAttributes().get(0);
		assertEquals("onOff", attributeDocumentation.getName());
		assertTrue(attributeDocumentation.getType().isPrimitive());
		assertEquals("Boolean", attributeDocumentation.getType().getTypeName());

	}

	@Test
	public void testRestService1RestControllerDocumentation() {

		RestServiceDocumentation service = DocumentationReflectionFactory.getDocumentationFor(RestService1RestController.class);

		assertNotNull(service);
		assertEquals("RestService1RestController", service.getServiceName());
		assertEquals("io.pelle.mango.demo.server.resttest1.RestService1RestController", service.getClassName());
		assertArrayEquals(new String[] { "restservice1" }, service.getPaths());

		assertEquals(2, service.getMethods().size());

	}
	
	@Test
	public void testRestService2RestControllerDocumentation() {

		RestServiceDocumentation service = DocumentationReflectionFactory.getDocumentationFor(RestService2RestController.class);

		assertNotNull(service);
		assertEquals("RestService2RestController", service.getServiceName());
		assertEquals("io.pelle.mango.demo.server.resttest2.RestService2RestController", service.getClassName());
		assertArrayEquals(new String[] { "restservice2" }, service.getPaths());

		assertEquals(4, service.getMethods().size());

		// methodwithbooleanparameter (GET)
		RestMethodDocumentation method = service.getMethods().get(0);
		assertArrayEquals(new RequestMethod[] { RequestMethod.GET }, method.getHttpMethods().toArray());
		assertArrayEquals(new String[] { "methodwithbooleanparameter" }, method.getPaths().toArray());
		RestTypeDocumentation response = method.getResponse().getType();
		assertTrue(response.isPrimitive());
		assertEquals("Boolean", response.getTypeName());

		// check attributes
		assertEquals(1, method.getAttributes().size());
		RestAttributeDocumentation attribute = method.getAttributes().get(0);
		assertEquals("onOff", attribute.getName());
		assertEquals("Boolean", attribute.getType().getTypeName());

		
		// methodwithbooleanparameter (POST)
		method = service.getMethods().get(1);
		assertArrayEquals(new RequestMethod[] { RequestMethod.POST }, method.getHttpMethods().toArray());
		assertArrayEquals(new String[] { "methodwithbooleanparameter" }, method.getPaths().toArray());
		response = method.getResponse().getType();
		assertTrue(response.isPrimitive());
		assertEquals("Boolean", response.getTypeName());

		assertEquals(1, method.getAttributes().size());
		attribute = method.getAttributes().get(0);
		assertNull(attribute.getName());
		assertEquals("RestService2RestControllerMethodWithBooleanParameterRequest", attribute.getType().getTypeName());
		
		RestTypeDocumentation booleanType = attribute.getType();
		assertEquals(1, booleanType.getAttributes().size());
		attribute = booleanType.getAttributes().get(0);
		assertEquals("onOff", attribute.getName());

		
		// methodwithvalueobjectparameter (GET)
		method = service.getMethods().get(2);
		assertArrayEquals(new RequestMethod[] { RequestMethod.GET }, method.getHttpMethods().toArray());
		assertArrayEquals(new String[] { "methodwithvalueobjectparameter" }, method.getPaths().toArray());
		assertEquals(1, method.getAttributes().size());
		assertEquals("valueObject2", method.getAttributes().get(0).getName());
		
		response = method.getResponse().getType();
		assertFalse(response.isPrimitive());
		assertEquals(4, response.getAttributes().size());

		attribute = response.getAttributes().get(0);
		assertEquals("entity5", attribute.getName());
		assertFalse(attribute.getType().isPrimitive());
		assertEquals("Entity5VO", attribute.getType().getTypeName());

		attribute = response.getAttributes().get(1);
		assertEquals("string1", attribute.getName());
		assertTrue(attribute.getType().isPrimitive());
		assertEquals("String", attribute.getType().getTypeName());

		attribute = response.getAttributes().get(2);
		assertEquals("valueObject2", attribute.getName());
		assertFalse(attribute.getType().isPrimitive());
		assertEquals("ValueObject2", attribute.getType().getTypeName());

		RestTypeDocumentation valueObject2 = attribute.getType();
		assertEquals("ValueObject2", valueObject2.getTypeName());

		attribute = response.getAttributes().get(3);
		assertEquals("valueObjects2", attribute.getName());

		// methodwithvalueobjectparameter (POST)
		method = service.getMethods().get(3);
		assertArrayEquals(new RequestMethod[] { RequestMethod.POST }, method.getHttpMethods().toArray());
		assertArrayEquals(new String[] { "methodwithvalueobjectparameter" }, method.getPaths().toArray());
		assertEquals(1, method.getAttributes().size());
		assertNull(method.getAttributes().get(0).getName());

		// check attributes
		assertEquals(1, method.getAttributes().size());
		attribute = method.getAttributes().get(0);
		assertNull(attribute.getName());
		assertEquals("ValueObject2", attribute.getType().getTypeName());

		// check response type
		response = method.getResponse().getType();
		assertFalse(response.isPrimitive());
		assertEquals(4, response.getAttributes().size());
		assertEquals("ValueObject1", response.getTypeName());
		attribute = response.getAttributes().get(0);
		assertEquals("entity5", attribute.getName());

	}

}