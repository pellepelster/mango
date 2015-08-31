package io.pelle.mango.client.web.modules.dictionary.controls;

import com.google.common.base.Objects;
import com.google.gwt.core.client.GWT;

import io.pelle.mango.client.FileVO;
import io.pelle.mango.client.base.modules.dictionary.controls.IFileControl;
import io.pelle.mango.client.base.modules.dictionary.hooks.DictionaryHookRegistry;
import io.pelle.mango.client.base.modules.dictionary.hooks.IFileControlHook;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IFileControlModel;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

public class FileControl extends BaseDictionaryControl<IFileControlModel, Object>implements IFileControl {

	private boolean showEmptyPlaceHolder;
	
	public interface IFileControlCallback {

		void onDelete(FileControl fileControl);

		void onAdd(FileControl fileControl);
		
	}
	
	private boolean deleteEnabled = false;

	private IFileControlHook fileControlHook = null;

	private IFileControlCallback fileControlCallback;

	public FileControl(IFileControlModel fileControlModel, BaseDictionaryElement<? extends IBaseModel> parent, IFileControlCallback fileControlCallback, boolean showEmptyPlaceHolder) {
		super(fileControlModel, parent);

		this.fileControlHook = DictionaryHookRegistry.getInstance().getFileControlHook(getModel());
		this.fileControlCallback = fileControlCallback;
		this.showEmptyPlaceHolder = showEmptyPlaceHolder;
	}

	public FileControl(IFileControlModel fileControlModel, BaseDictionaryElement<? extends IBaseModel> parent) {
		this(fileControlModel, parent, null, true);
	}

	@Override
	public boolean isDeleteEnabled() {
		return deleteEnabled;
	}

	@Override
	public String getFileUrl() {

		String fileUUID = null;

		if (getValue() != null && getValue() instanceof FileVO) {
			fileUUID = ((FileVO) getValue()).getFileUUID();
		}

		if (fileControlHook == null && fileUUID != null) {
			return GWT.getModuleBaseURL() + "../remote/" + IFileControl.FILE_GET_BASE_URL + "/" + IFileControl.FILE_GET_URL + "/" + fileUUID;
		} else {
			return "_blank";
		}
	}

	@Override
	public void delete() {
		
		deleteEnabled = false;
		parseValue(null);

		if (fileControlCallback != null) {
			fileControlCallback.onDelete(this);
		}
		
		fireUpdateListeners();
	}

	public String stripFileName(String filePathName) {

		if (filePathName == null)
			return null;

		int dotPos = filePathName.lastIndexOf('.');
		int slashPos = filePathName.lastIndexOf('\\');
		if (slashPos == -1) {
			slashPos = filePathName.lastIndexOf('/');
		}

		if (dotPos > slashPos) {
			return filePathName.substring(slashPos > 0 ? slashPos + 1 : 0, dotPos);
		}

		return filePathName.substring(slashPos > 0 ? slashPos + 1 : 0);
	}

	private String getFileNamePlaceholder() {
		
		if (showEmptyPlaceHolder) {
			return MangoClientWeb.MESSAGES.fileNone();
		} else {
			return "";
		}
		
	}
	
	@Override
	public String getFileName() {
		if (getValue() instanceof FileVO) {
			return Objects.firstNonNull(((FileVO) getValue()).getFileName(), getFileNamePlaceholder());
		} else {
			return getFileNamePlaceholder();
		}
	}

	@Override
	public void setFileNameUUID(String fileName, String fileUUID) {

		FileVO file = null;

		if (getValue() != null && getValue() instanceof FileVO) {
			file = (FileVO) getValue();
		} else {
			file = new FileVO();
		}

		file.setFileName(stripFileName(fileName));
		file.setFileUUID(fileUUID);

		deleteEnabled = true;
		setValue(file);
		
		if (fileControlCallback != null) {
			fileControlCallback.onAdd(this);
		}
		
		fireUpdateListeners();
	}

	@Override
	public boolean hasHook() {
		return fileControlHook != null;
	}

	@Override
	public void activate() {
		if (hasHook()) {
			fileControlHook.onLinkActivate(this);
		}
	}

	@Override
	protected BaseDictionaryControl<IFileControlModel, Object>.ParseResult parseValueInternal(String valueString) {
		return null;
	}

	@Override
	public String getFileUUID() {
		if (getValue() instanceof FileVO) {
			return ((FileVO) getValue()).getFileUUID();
		} else {
			return null;
		}
	}

}
