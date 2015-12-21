package io.pelle.mango.demo.server;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.pelle.mango.demo.server.util.BaseDemoTest;
import io.pelle.mango.server.util.RestServiceDocumentationBean;
import io.pelle.mango.server.util.RestServiceDocumentationBean.RestPackageDocumentation;

public class DemoDocumentationBeanTest extends BaseDemoTest {

	@Autowired
	private RestServiceDocumentationBean documentationBean;
	
	@Test
	public void testServiceDocumentationCount() {
		
		List<RestPackageDocumentation> packageDocumentations = documentationBean.getPackageDocumentations();
		Assert.assertEquals(3, packageDocumentations.size());
		
	}


}