package io.pelle.mango.client.web.test.container;

import org.junit.Assert;

import io.pelle.mango.client.base.modules.dictionary.container.IFileList;

public class FileListTestcontainer {

	private IFileList fileList;

	public FileListTestcontainer(IFileList fileList) {
		super();
		this.fileList = fileList;
	}

	public void assertFileCount(int filecount) {
		Assert.assertEquals(filecount, fileList.getFiles().size());
	}

}
