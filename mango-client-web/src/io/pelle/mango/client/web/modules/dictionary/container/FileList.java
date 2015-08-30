package io.pelle.mango.client.web.modules.dictionary.container;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import io.pelle.mango.client.FileVO;
import io.pelle.mango.client.base.db.vos.UUID;
import io.pelle.mango.client.base.modules.dictionary.IListUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.IVOWrapper;
import io.pelle.mango.client.base.modules.dictionary.container.IFileList;
import io.pelle.mango.client.base.modules.dictionary.controls.IFileControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IFileListModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.FileControlModel;
import io.pelle.mango.client.base.util.SimpleCallback;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;
import io.pelle.mango.client.web.modules.dictionary.controls.FileControl;
import io.pelle.mango.client.web.modules.dictionary.editor.EditorVOWrapper;

public class FileList extends BaseContainerElement<IFileListModel, IListUpdateListener<IFileControl>>implements IFileList, SimpleCallback<FileControl> {

	private List<IFileControl> fileControls = new ArrayList<>();

	public static Function<FileVO, File> FILEVO2FILE = new Function<FileVO, IFileList.File>() {

		@Override
		public File apply(FileVO input) {
			File file = new File();
			return file;
		}
	};

	private Predicate<IFileControl> findByFileVO(final FileVO file) {

		return new Predicate<IFileControl>() {

			@Override
			public boolean apply(IFileControl input) {
				return file.equals(input.getValue());
			}
		};

	}

	public FileList(IFileListModel fileListModel, BaseDictionaryElement<? extends IBaseModel> parent) {
		super(fileListModel, parent);
	}

	@Override
	public Collection<IFileControl> getFileControls() {
		return fileControls;
	}

	@SuppressWarnings("unchecked")
	private List<FileVO> getFilesInternal() {
		return (List<FileVO>) getParent().getVOWrapper().get(getModel().getAttributePath());
	}

	@Override
	public void update() {

		super.update();

		List<IFileControl> addedFileControls = new ArrayList<>();
		List<IFileControl> removedFileControls = new ArrayList<>(fileControls);

		if (getFilesInternal().isEmpty()) {
			addNewFile();
		} else {
			for (FileVO fileVO : getFilesInternal()) {

				Optional<IFileControl> fileControl = Iterables.tryFind(fileControls, findByFileVO(fileVO));

				if (fileControl.isPresent()) {
					removedFileControls.remove(fileControl.get());
				} else {
					FileControl newFileControl = createFilecontrol(fileVO);
					fileControls.add(newFileControl);
					addedFileControls.add(newFileControl);
				}
			}
		}

		if (!addedFileControls.isEmpty()) {
			fireOnAddedUpdateListeners(addedFileControls);
		}

		if (!removedFileControls.isEmpty()) {
			fireOnAddedUpdateListeners(removedFileControls);
		}

	}

	protected void fireOnAddedUpdateListeners(Collection<IFileControl> added) {
		for (IListUpdateListener<IFileControl> updateListener : getUpdateListeners()) {
			updateListener.onAdded(added);
		}
	}

	protected void fireOnRemovedUpdateListeners(Collection<IFileControl> removed) {
		for (IListUpdateListener<IFileControl> updateListener : getUpdateListeners()) {
			updateListener.onRemoved(removed);
		}
	}

	@Override
	public void removeFile(IFileControl fileControl) {

		// getFilesInternal().add(fileVO);

		fileControls.remove(fileControl);

		fireOnRemovedUpdateListeners(Arrays.asList(fileControl));
	}

	@Override
	public void addNewFile() {

		FileVO fileVO = new FileVO();

		IFileControl fileControl = createFilecontrol(fileVO);

		fileControls.add(fileControl);
		getFilesInternal().add(fileVO);

		fireOnAddedUpdateListeners(Arrays.asList(fileControl));
	}

	private FileControl createFilecontrol(FileVO fileVO) {

		final EditorVOWrapper<FileVO> voWrapper = new EditorVOWrapper<>(fileVO);

		FileControlModel fileControlModel = new FileControlModel(UUID.uuid(), getModel());
		fileControlModel.setAttributePath("/");

		FileControl fileControl = new FileControl(fileControlModel, this, this) {
			@Override
			public IVOWrapper<? extends IBaseVO> getVOWrapper() {
				return voWrapper;
			}
		};

		return fileControl;
	}

	@Override
	public void onCallback(FileControl fileControl) {
		removeFile(fileControl);
	}

}
