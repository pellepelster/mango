package io.pelle.mango.client.base.modules.dictionary.container;

import java.util.Collection;

import io.pelle.mango.client.base.modules.dictionary.controls.IFileControl;

public interface IFileList extends IBaseContainer {

	public class File {
	}

	Collection<File> getFiles();

	IFileControl addNewFile();
	
}
