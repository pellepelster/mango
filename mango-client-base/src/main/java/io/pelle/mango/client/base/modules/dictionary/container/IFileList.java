package io.pelle.mango.client.base.modules.dictionary.container;

import java.util.Collection;

import io.pelle.mango.client.base.modules.dictionary.IListUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.controls.IFileControl;

public interface IFileList extends IBaseContainer<IListUpdateListener<IFileControl>> {

	public class File {
	}

	Collection<IFileControl> getFileControls();

	void removeFile(IFileControl fileControl);

}
