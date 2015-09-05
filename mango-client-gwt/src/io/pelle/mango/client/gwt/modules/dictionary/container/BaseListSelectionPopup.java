package io.pelle.mango.client.gwt.modules.dictionary.container;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.base.util.SimpleCallback;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.modules.dictionary.BaseCellTable;

public class BaseListSelectionPopup<VOType extends IBaseVO> extends BaseSelectionPopup<VOType>implements AsyncCallback<List<VOType>> {

	private VOTable<VOType> voTable;

	private List<IBaseControlModel> baseControlModels;

	public BaseListSelectionPopup(String message, List<IBaseControlModel> baseControlModels, final SimpleCallback<VOType> selectionHandler) {
		super(message, selectionHandler);
		this.baseControlModels = baseControlModels;
		voTable = new VOTable<VOType>(baseControlModels);
	}

	@Override
	protected Widget createDialogBoxContent() {

		voTable.setHeight(BaseCellTable.DEFAULT_TABLE_HEIGHT);
		voTable.setWidth("100%");
		voTable.addSelectHandler(new SimpleCallback<VOType>() {
			@Override
			public void onCallback(VOType vo) {
				closeDialogWithSelection(vo);
			}
		});

		return voTable;
	}

	@Override
	protected void getCurrentSelection(AsyncCallback<VOType> asyncCallback) {
		asyncCallback.onSuccess(voTable.getCurrentSelection());
	}

	@Override
	public void onFailure(Throwable caught) {
		throw new RuntimeException(caught);
	}

	@Override
	public void onSuccess(List<VOType> result) {
		if (voTable != null) {
			voTable.setContent(result);
		}
	}

}
