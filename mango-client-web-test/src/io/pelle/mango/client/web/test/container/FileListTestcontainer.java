package io.pelle.mango.client.web.test.container;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import io.pelle.mango.client.base.modules.dictionary.IUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.container.IFileList;
import io.pelle.mango.client.base.modules.dictionary.container.IFileList.File;
import io.pelle.mango.client.web.test.controls.FileTestControl;

public class FileListTestcontainer extends BaseTestContainer implements IUpdateListener {

	private IFileList fileList;

	private List<File> files = new ArrayList<>();
	
	public FileListTestcontainer(IFileList fileList) {
		super();
		this.fileList = fileList;
		
		fileList.addUpdateListener(this);
	}

	public void assertFileCount(int filecount) {
		Assert.assertEquals(filecount, files.size());
	}

	@Override
	public void onUpdate() {
		files.clear();
		files.addAll(fileList.getFiles());
	}

	public FileTestControl addNewFile() {
		return new FileTestControl(fileList.addNewFile());
	}

}
