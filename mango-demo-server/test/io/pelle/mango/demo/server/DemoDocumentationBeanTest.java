package io.pelle.mango.demo.server;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.pelle.mango.demo.server.util.BaseDemoTest;
import io.pelle.mango.server.documentation.DocumentationRestApi;
import io.pelle.mango.server.documentation.PackageDocumentation;

public class DemoDocumentationBeanTest extends BaseDemoTest {

	@Autowired
	private DocumentationRestApi documentationBean;

	@Test
	public void testServiceDocumentationCount() {

		List<PackageDocumentation> packageDocumentations = documentationBean.getPackageDocumentation();
		Assert.assertEquals(3, packageDocumentations.size());

	}

}