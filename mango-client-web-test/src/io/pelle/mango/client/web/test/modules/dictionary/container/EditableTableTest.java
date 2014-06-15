package io.pelle.mango.client.web.test.modules.dictionary.container;

import io.pelle.mango.client.base.modules.dictionary.container.IBaseTable;
import io.pelle.mango.client.base.modules.dictionary.container.IEditableTable;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;

import java.util.List;

import junit.framework.Assert;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class EditableTableTest<VOType extends IBaseVO> {

	private IEditableTable<VOType> editableTable;

	public EditableTableTest(IEditableTable<VOType> editableTable) {
		this.editableTable = editableTable;
	}

	public void add(final AsyncCallback<EditableTableTest<VOType>> asyncCallback) {
		this.editableTable.add(new BaseErrorAsyncCallback<List<IBaseTable.ITableRow<VOType>>>() {
			@Override
			public void onSuccess(List<IBaseTable.ITableRow<VOType>> result) {
				asyncCallback.onSuccess(EditableTableTest.this);
			}
		});
	}

	public void delete(final AsyncCallback<EditableTableTest<VOType>> asyncCallback) {
		this.editableTable.delete(new BaseErrorAsyncCallback<List<IBaseTable.ITableRow<VOType>>>() {
			@Override
			public void onSuccess(List<IBaseTable.ITableRow<VOType>> result) {
				asyncCallback.onSuccess(EditableTableTest.this);
			}
		});
	}

	public void assertRowCount(int expectedRowCount) {
		Assert.assertEquals(expectedRowCount, this.editableTable.getRows().size());
	}

}
