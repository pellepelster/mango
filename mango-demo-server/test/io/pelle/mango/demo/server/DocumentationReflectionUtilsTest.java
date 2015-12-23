package io.pelle.mango.demo.server;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import io.pelle.mango.client.web.modules.dictionary.editor.DictionaryEditor;
import io.pelle.mango.demo.server.test.RestTestRestController;
import io.pelle.mango.server.documentation.DocumentationReflectionUtils;
import io.pelle.mango.server.documentation.RestBodyAttributeDocumentation;
import io.pelle.mango.server.documentation.RestBodyDocumentation;
import io.pelle.mango.server.documentation.RestMethodDocumentation;
import io.pelle.mango.server.documentation.RestServiceDocumentation;

public class DocumentationReflectionUtilsTest {

	@Test
	public void testInvalidClass() {
		Assert.assertNull(DocumentationReflectionUtils.getDocumentationFor(DictionaryEditor.class));
	}

	@Test
	public void testRestTestRestControllerDocumentation() {
		
		RestServiceDocumentation serviceDocumentation = DocumentationReflectionUtils.getDocumentationFor(RestTestRestController.class);
		
		assertNotNull(serviceDocumentation);
		assertEquals("RestTestRestController", serviceDocumentation.getServiceName());
		assertEquals("io.pelle.mango.demo.server.test.RestTestRestController", serviceDocumentation.getClassName());
		assertArrayEquals(new String[] { "resttest" }, serviceDocumentation.getPaths());
		
		assertEquals(4, serviceDocumentation.getMethodDocumentations().size());


		RestMethodDocumentation methodDocumentation = serviceDocumentation.getMethodDocumentations().get(0);
		assertArrayEquals(new String[] { "methodwithbooleanparameter" }, methodDocumentation.getPaths());
		RestBodyDocumentation response = methodDocumentation.getResponse();
		assertTrue(response.isPrimitive());
		assertEquals("Boolean", response.getPrimitiveName());
		
		methodDocumentation = serviceDocumentation.getMethodDocumentations().get(1);
		assertArrayEquals(new String[] { "methodwithbooleanparameter" }, methodDocumentation.getPaths());
		response = methodDocumentation.getResponse();
		assertTrue(response.isPrimitive());
		assertEquals("Boolean", response.getPrimitiveName());

		methodDocumentation = serviceDocumentation.getMethodDocumentations().get(2);
		assertArrayEquals(new String[] { "methodwithvalueobjectparameter" }, methodDocumentation.getPaths());
		response = methodDocumentation.getResponse();
		assertFalse(response.isPrimitive());
		assertEquals(1, response.getAttributes().size());
		RestBodyAttributeDocumentation attributeDocumentation = response.getAttributes().get(0);
		assertEquals("string2", attributeDocumentation.getName());
		
		methodDocumentation = serviceDocumentation.getMethodDocumentations().get(3);
		assertArrayEquals(new String[] { "methodwithvalueobjectparameter" }, methodDocumentation.getPaths());
		response = methodDocumentation.getResponse();
		assertFalse(response.isPrimitive());
		assertEquals(1, response.getAttributes().size());
		attributeDocumentation = response.getAttributes().get(0);
		assertEquals("string2", attributeDocumentation.getName());
		

	}

}