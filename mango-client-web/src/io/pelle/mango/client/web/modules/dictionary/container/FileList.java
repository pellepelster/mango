package io.pelle.mango.client.web.modules.dictionary.container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import io.pelle.mango.client.FileVO;
import io.pelle.mango.client.base.db.vos.UUID;
import io.pelle.mango.client.base.modules.dictionary.IVOWrapper;
import io.pelle.mango.client.base.modules.dictionary.container.IFileList;
import io.pelle.mango.client.base.modules.dictionary.controls.IFileControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IFileListModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.FileControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.modules.dictionary.DictionaryElementUtil;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;
import io.pelle.mango.client.web.modules.dictionary.controls.FileControl;

public class FileList extends BaseContainerElement<IFileListModel> implements IFileList {

	public static Function<FileVO, File> FILEVO2FILE = new Function<FileVO, IFileList.File>() {

		@Override
		public File apply(FileVO input) {
			File file = new File();
			return file;
		}
	};
	
	public FileList(IFileListModel fileListModel, BaseDictionaryElement<? extends IBaseModel> parent) {
		super(fileListModel, parent);
	}

	@Override
	public Collection<File> getFiles() {
		@SuppressWarnings("unchecked")
		List<FileVO> files = (List<FileVO>) getParent().getVOWrapper().get(getModel().getAttributePath());
		return Collections2.transform(files, FILEVO2FILE);
	}

	@SuppressWarnings("unchecked")
	private List<String> getFileUUIDs() {

		Map<String, Object> data = DictionaryElementUtil.getRootEditor(this).getVO().getData();
		
		if (!data.containsKey(getModel().getAttributePath()) || ! (data.get(getModel().getAttributePath()) instanceof List)) {
			data.put(getModel().getAttributePath(), new ArrayList<String>());
		}
		
		return (List<String>) data.get(getModel().getAttributePath());
	}
	
	public void addFile(String fileUUID) {
		if (fileUUID == null) {
			getFileUUIDs().add(fileUUID);
		}
	}

	@Override
	public IFileControl addNewFile() {
		
		FileControlModel fileControlModel = new FileControlModel(UUID.uuid(), getModel());
		FileControl fileControl = new FileControl(fileControlModel, this) {
			@Override
			public IVOWrapper<? extends IBaseVO> getVOWrapper() {
				return super.getVOWrapper();
			}
		};
		
		return fileControl;
	}

}
