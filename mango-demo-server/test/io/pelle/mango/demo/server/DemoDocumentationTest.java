package io.pelle.mango.demo.server;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.pelle.mango.demo.server.util.BaseDemoTest;
import io.pelle.mango.server.documentation.DocumentationRestApi;
import io.pelle.mango.server.documentation.DocumentationRestApi.PackageOrService;
import io.pelle.mango.server.documentation.PackageDocumentation;

public class DemoDocumentationTest extends BaseDemoTest {

	@Autowired
	private DocumentationRestApi documentationBean;

	@Test
	public void testServiceDocumentationCount() {

		List<PackageDocumentation> packageDocumentations = documentationBean.getPackageDocumentation();
		Assert.assertEquals(1, packageDocumentations.size());

	}

	@Test
	public void testGetServiceDocumentation() {

		PackageOrService packageOrService = documentationBean.getPackageOrService("io.pelle.mango.demo.server.test.RestTestRestController");
		Assert.assertEquals("io.pelle.mango.demo.server.test", packageOrService.getPackage().getPackageName());
		Assert.assertTrue("io.pelle.mango.demo.server.test", packageOrService.getService().isPresent());
		Assert.assertEquals("io.pelle.mango.demo.server.test.RestTestRestController", packageOrService.getService().get().getClassName());
		Assert.assertEquals("RestTestRestController", packageOrService.getService().get().getServiceName());

}

	@Test
	public void testGetPackageDocumentation() {

		PackageOrService packageOrService = documentationBean.getPackageOrService("io.pelle.mango.demo.server.test");
		Assert.assertEquals("io.pelle.mango.demo.server.test", packageOrService.getPackage().getPackageName());
		Assert.assertFalse("io.pelle.mango.demo.server.test", packageOrService.getService().isPresent());
		
	}

}