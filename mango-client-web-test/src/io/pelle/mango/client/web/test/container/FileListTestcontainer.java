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
		return new FileTestControl(fileControls.get(0));
	}

	public FileTestControl addNewFile() {
		future = AsyncCallbackFuture.create();
		fileList.addNewFile();
		FileTestControl result = future.get();

		future = null;

		return result;
	}

	@Override
	public void onAdded(Collection<IFileControl> added) {

		fileControls.addAll(added);

		if (future != null) {
			if (added.size() == 1) {
				future.onSuccess(new FileTestControl(added.iterator().next()));
			} else {
				Assert.fail(String.format("expected 1 file control, but got %d", added.size()));
			}
		}
	}

	@Override
	public void onRemoved(Collection<IFileControl> removed) {
		fileControls.removeAll(removed);
	}

}
