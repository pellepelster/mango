package io.pelle.mango.client.web.modules.dictionary.container;

import io.pelle.mango.client.base.modules.dictionary.container.IEditableTable;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IEditableTableModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class EditableTable<VOType extends IBaseVO> extends BaseTableElement<VOType, IEditableTableModel> implements IEditableTable<VOType> {

	public EditableTable(IEditableTableModel editableTableModel, BaseDictionaryElement<IBaseModel> parent) {
		super(editableTableModel, parent);
	}

	@Override
	public void add(final AsyncCallback<List<ITableRow<VOType>>> asyncCallback) {
		MangoClientWeb.getInstance().getRemoteServiceLocator().getBaseEntityService().getNewVO(getModel().getVoName(), new HashMap<String, String>(), new BaseErrorAsyncCallback<IBaseVO>() {

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(IBaseVO newVO) {
				addRow((VOType) newVO);
				asyncCallback.onSuccess(EditableTable.this.getRows());
			}
		});
	}

}
