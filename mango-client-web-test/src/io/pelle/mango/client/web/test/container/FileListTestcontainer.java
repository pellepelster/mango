package io.pelle.mango.client.web.test.container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;

import io.pelle.mango.client.base.modules.dictionary.IListUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.container.IFileList;
import io.pelle.mango.client.base.modules.dictionary.controls.IFileControl;
import io.pelle.mango.client.web.test.AsyncCallbackFuture;
import io.pelle.mango.client.web.test.controls.FileTestControl;

public class FileListTestcontainer extends BaseTestContainer implements IListUpdateListener<IFileControl> {

	private IFileList fileList;

	private List<IFileControl> fileControls = new ArrayList<>();

	private AsyncCallbackFuture<FileTestControl> future;

	public FileListTestcontainer(IFileList fileList) {
		super();
		this.fileList = fileList;

		fileList.addUpdateListener(this);
		onUpdate();
	}

	public void assertFileCount(int filecount) {
		Assert.assertEquals(filecount, fileControls.size());
	}

	@Override
	public void onUpdate() {
		fileControls.clear();
		fileControls.addAll(fileList.getFileControls());
	}

	public FileTestControl getFileControl(int index) {
		return new FileTestControl(fileControls.get(index));
	}

	@Override
	public void onAdded(int index, IFileControl added) {

		fileControls.add(index, added);

		if (future != null) {
			future.onSuccess(new FileTestControl(added));
		}
	}

	@Override
	public void onRemoved(Collection<IFileControl> removed) {
		fileControls.removeAll(removed);
	}

}
