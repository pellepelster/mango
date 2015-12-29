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
	public void testServiceDocumentationCountAndOrdering() {

		List<RestPackageDocumentation> packageDocumentations = documentationBean.getPackageDocumentation();
		Assert.assertEquals(3, packageDocumentations.size());

		Assert.assertEquals("io.pelle.mango.demo.server.resttest1", packageDocumentations.get(0).getPackageName());
		Assert.assertEquals("io.pelle.mango.demo.server.resttest2", packageDocumentations.get(1).getPackageName());
		Assert.assertEquals("io.pelle.mango.demo.server.test", packageDocumentations.get(2).getPackageName());
		
	}

	@Test
	public void testGetRestService1RestControllerDocumentation() {

		PackageOrService packageOrService = documentationBean.getPackageOrService("io.pelle.mango.demo.server.resttest1.RestService1RestController");
		Assert.assertEquals("io.pelle.mango.demo.server.resttest1", packageOrService.getPackage().getPackageName());
		Assert.assertTrue(packageOrService.getService().isPresent());
		Assert.assertEquals("io.pelle.mango.demo.server.resttest1.RestService1RestController", packageOrService.getService().get().getClassName());
		Assert.assertEquals("RestService1RestController", packageOrService.getService().get().getServiceName());
		Assert.assertEquals("This service exists for the sole purpose of showcasing the different features of the automatic REST service generation.", packageOrService.getService().get().getDescription());
		Assert.assertEquals("Demo Rest services", packageOrService.getService().get().getShortDescription());
		
	}

	@Test
	public void testGetRestTest1PackageDocumentation() {

		PackageOrService packageOrService = documentationBean.getPackageOrService("io.pelle.mango.demo.server.resttest1");
		Assert.assertEquals("io.pelle.mango.demo.server.resttest1", packageOrService.getPackage().getPackageName());
		Assert.assertFalse("io.pelle.mango.demo.server.resttest1", packageOrService.getService().isPresent());
		Assert.assertTrue(packageOrService.getPackage().getDescription().startsWith("The description for a package is read from a property file named"));
		Assert.assertEquals("Rest Example Package 1", packageOrService.getPackage().getName());
		Assert.assertEquals("A short summary of the package content", packageOrService.getPackage().getShortDescription());

	}

}