package io.pelle.mango.demo.server;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMethod;

import io.pelle.mango.client.web.modules.dictionary.editor.DictionaryEditor;
import io.pelle.mango.demo.server.test.RestTestRestController;
import io.pelle.mango.server.documentation.DocumentationReflectionFactory;
import io.pelle.mango.server.documentation.RestAttributeDocumentation;
import io.pelle.mango.server.documentation.RestMethodDocumentation;
import io.pelle.mango.server.documentation.RestServiceDocumentation;
import io.pelle.mango.server.documentation.RestTypeDocumentation;

public class DocumentationReflectionUtilsTest {

	@Test
	public void testInvalidClass() {
		Assert.assertNull(DocumentationReflectionFactory.getDocumentationFor(DictionaryEditor.class));
	}

	@Test
	public void testRestTestRestControllerDocumentation() {

		RestServiceDocumentation serviceDocumentation = DocumentationReflectionFactory.getDocumentationFor(RestTestRestController.class);

		assertNotNull(serviceDocumentation);
		assertEquals("RestTestRestController", serviceDocumentation.getServiceName());
		assertEquals("io.pelle.mango.demo.server.test.RestTestRestController", serviceDocumentation.getClassName());
		assertArrayEquals(new String[] { "resttest" }, serviceDocumentation.getPaths());

		assertEquals(4, serviceDocumentation.getMethods().size());

		// methodwithbooleanparameter (GET)
		RestMethodDocumentation methodDocumentation = serviceDocumentation.getMethods().get(0);
		assertArrayEquals(new RequestMethod[] { RequestMethod.GET }, methodDocumentation.getHttpMethods().toArray());
		assertArrayEquals(new String[] { "methodwithbooleanparameter" }, methodDocumentation.getPaths().toArray());
		RestTypeDocumentation response = methodDocumentation.getReturnType();
		assertTrue(response.isPrimitive());
		assertEquals("Boolean", response.getTypeName());

		// check attributes
		assertEquals(1, methodDocumentation.getAttributes().size());
		RestAttributeDocumentation attributeDocumentation = methodDocumentation.getAttributes().get(0);
		assertEquals("onOff", attributeDocumentation.getName());
		assertEquals("Boolean", attributeDocumentation.getType().getTypeName());

		
		// methodwithbooleanparameter (POST)
		methodDocumentation = serviceDocumentation.getMethods().get(1);
		assertArrayEquals(new RequestMethod[] { RequestMethod.POST }, methodDocumentation.getHttpMethods().toArray());
		assertArrayEquals(new String[] { "methodwithbooleanparameter" }, methodDocumentation.getPaths().toArray());
		response = methodDocumentation.getReturnType();
		assertTrue(response.isPrimitive());
		assertEquals("Boolean", response.getTypeName());

		// methodwithvalueobjectparameter
		methodDocumentation = serviceDocumentation.getMethods().get(2);
		assertArrayEquals(new String[] { "methodwithvalueobjectparameter" }, methodDocumentation.getPaths().toArray());
		response = methodDocumentation.getReturnType();
		assertFalse(response.isPrimitive());
		assertEquals(4, response.getAttributes().size());

		attributeDocumentation = response.getAttributes().get(0);
		assertEquals("entity5", attributeDocumentation.getName());
		assertFalse(attributeDocumentation.getType().isPrimitive());
		assertEquals("Entity5VO", attributeDocumentation.getType().getTypeName());

		attributeDocumentation = response.getAttributes().get(1);
		assertEquals("string1", attributeDocumentation.getName());
		assertTrue(attributeDocumentation.getType().isPrimitive());
		assertEquals("String", attributeDocumentation.getType().getTypeName());

		attributeDocumentation = response.getAttributes().get(2);
		assertEquals("valueObject2", attributeDocumentation.getName());
		assertFalse(attributeDocumentation.getType().isPrimitive());
		assertEquals("ValueObject2", attributeDocumentation.getType().getTypeName());

		RestTypeDocumentation valueObject2 = attributeDocumentation.getType();
		assertEquals("ValueObject2", valueObject2.getTypeName());

		attributeDocumentation = response.getAttributes().get(3);
		assertEquals("valueObjects2", attributeDocumentation.getName());

		// methodwithvalueobjectparameter
		methodDocumentation = serviceDocumentation.getMethods().get(3);
		assertArrayEquals(new String[] { "methodwithvalueobjectparameter" }, methodDocumentation.getPaths().toArray());

		// check attributes
		assertEquals(1, methodDocumentation.getAttributes().size());
		attributeDocumentation = methodDocumentation.getAttributes().get(0);
		assertNull(attributeDocumentation.getName());
		assertEquals("ValueObject2", attributeDocumentation.getType().getTypeName());

		// check response type
		response = methodDocumentation.getReturnType();
		assertFalse(response.isPrimitive());
		assertEquals(4, response.getAttributes().size());
		assertEquals("ValueObject1", response.getTypeName());
		attributeDocumentation = response.getAttributes().get(0);
		assertEquals("entity5", attributeDocumentation.getName());

	}

}