package io.pelle.mango.client.gwt.modules.dictionary.container;

import io.pelle.mango.client.base.modules.dictionary.container.IBaseTable;
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelProvider;
import io.pelle.mango.client.base.modules.dictionary.model.IDictionaryModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.base.util.SimpleCallback;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.gwt.modules.dictionary.BaseCellTable;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.base.DictionaryUtil;
import io.pelle.mango.client.web.modules.dictionary.container.AssignmentTable;
import io.pelle.mango.client.web.modules.dictionary.controls.BaseDictionaryControl;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class VOSelectionPopup<VOType extends IBaseVO> extends BaseVOSelectionPopup<VOType> {

	private final Class<VOType> voClass;

	private VOTable<VOType> voTable;

	private static List<BaseDictionaryControl<?, ?>> baseControls;

	private VOSelectionPopup(Class<VOType> voClass, String message, List<BaseDictionaryControl<?, ?>> baseControls, final SimpleCallback<VOType> voSelectHandler) {
		super(message, voSelectHandler);

		this.voClass = voClass;
		this.baseControls = baseControls;
	}

	private void refreshTable() {

		SelectQuery<VOType> selectQuery = SelectQuery.selectFrom(voClass);

		MangoClientWeb.getInstance().getRemoteServiceLocator().getBaseEntityService().filter(selectQuery, new AsyncCallback<List<VOType>>() {

			@Override
			public void onFailure(Throwable caught) {
				throw new RuntimeException(caught);
			}

			@Override
			public void onSuccess(List<VOType> result) {
				throw new RuntimeException("TODO");
				// voTable.setContent(DictionaryElementUtil.vos2TableRows(result));
			}
		});
	}

	public static <VOType extends IBaseVO> VOSelectionPopup<VOType> create(Class<VOType> voClass, String message, List<IBaseControlModel> baseControlModels, SimpleCallback<VOType> voSelectHandler) {
		return new VOSelectionPopup<VOType>(voClass, message, baseControls, voSelectHandler);
	}

	public static <VOType extends IBaseVO> VOSelectionPopup<VOType> create(AssignmentTable<?> assignmentTable, SimpleCallback<VOType> voSelectHandler) {
		IDictionaryModel dictionaryModel = DictionaryModelProvider.getDictionary(assignmentTable.getModel().getDictionaryName());
		return new VOSelectionPopup<VOType>((Class<VOType>) dictionaryModel.getVOClass(), DictionaryUtil.getLabel(dictionaryModel), assignmentTable.getControls(), voSelectHandler);
	}

	@Override
	protected Widget createDialogBoxContent() {
		// vo table
		voTable = new VOTable<VOType>(baseControls);

		voTable.setHeight(BaseCellTable.DEFAULT_TABLE_HEIGHT);
		voTable.setWidth("100%");
		voTable.addVOSelectHandler(new SimpleCallback<IBaseTable.ITableRow<VOType>>() {
			@Override
			public void onCallback(IBaseTable.ITableRow<VOType> tableRow) {
				closeDialogWithSelection(tableRow.getVO());
			}
		});
		refreshTable();

		return voTable;
	}

	@Override
	protected void getCurrentSelection(AsyncCallback<VOType> asyncCallback) {
		asyncCallback.onSuccess(voTable.getCurrentSelection().getVO());
	}

}
