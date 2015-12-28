package io.pelle.mango.demo.server;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.pelle.mango.demo.server.util.BaseDemoTest;
import io.pelle.mango.server.documentation.DocumentationRestApi;
import io.pelle.mango.server.documentation.DocumentationRestApi.PackageOrService;
import io.pelle.mango.server.documentation.RestPackageDocumentation;

public class DemoDocumentationTest extends BaseDemoTest {

	@Autowired
	private DocumentationRestApi documentationBean;

	@Test
	public void testServiceDocumentationCount() {

		List<RestPackageDocumentation> packageDocumentations = documentationBean.getPackageDocumentation();
		Assert.assertEquals(1, packageDocumentations.size());

	}

	@Test
	public void testGetServiceDocumentation() {

		PackageOrService packageOrService = documentationBean.getPackageOrService("io.pelle.mango.demo.server.test.RestTestRestController");
		Assert.assertEquals("io.pelle.mango.demo.server.test", packageOrService.getPackage().getPackageName());
		Assert.assertTrue("io.pelle.mango.demo.server.test", packageOrService.getService().isPresent());
		Assert.assertEquals("io.pelle.mango.demo.server.test.RestTestRestController", packageOrService.getService().get().getClassName());
		Assert.assertEquals("RestTestRestController", packageOrService.getService().get().getServiceName());
		Assert.assertEquals("This service exists for the sole purpose of showcasing the different features of the automatic REST service generation.", packageOrService.getService().get().getDescription());
		Assert.assertEquals("The short description is intended to be used in lists.", packageOrService.getService().get().getShortDescription());
		
	}

	@Test
	public void testGetPackageDocumentation() {

		PackageOrService packageOrService = documentationBean.getPackageOrService("io.pelle.mango.demo.server.test");
		Assert.assertEquals("io.pelle.mango.demo.server.test", packageOrService.getPackage().getPackageName());
		Assert.assertFalse("io.pelle.mango.demo.server.test", packageOrService.getService().isPresent());
		Assert.assertTrue(packageOrService.getPackage().getDescription().startsWith("The description for a specific package is read from a property file named"));

	}

}