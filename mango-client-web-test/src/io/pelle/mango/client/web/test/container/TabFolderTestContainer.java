package io.pelle.mango.client.web.test.container;

import org.junit.Assert;

import io.pelle.mango.client.base.modules.dictionary.container.ITabFolder;

public class TabFolderTestContainer extends BaseTestContainer {

	private ITabFolder tabFolder;

	public TabFolderTestContainer(ITabFolder tabFolder) {
		super();
		this.tabFolder = tabFolder;
	}

	public void assertTabCount(int count) {
		Assert.assertEquals(count, tabFolder.getTabs().size());
	}

}
