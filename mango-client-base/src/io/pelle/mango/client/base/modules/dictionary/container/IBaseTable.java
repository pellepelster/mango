package io.pelle.mango.client.base.modules.dictionary.container;

import io.pelle.mango.client.base.modules.dictionary.controls.IBaseControl;
import io.pelle.mango.client.base.modules.dictionary.model.BaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IBaseTable<VOType extends IBaseVO> extends IBaseContainer
{
	public interface TableUpdateListener
	{
		void onUpdate();
	}

	interface ITableRow<RowVOType extends IBaseVO>
	{

		RowVOType getVO();

		@SuppressWarnings("rawtypes")
		<ElementType extends IBaseControl> ElementType getElement(BaseModel<ElementType> baseModel);

		@SuppressWarnings("rawtypes")
		<ElementType extends IBaseControl> ElementType getElement(IBaseControlModel baseControlModel);

	}

	void addTableUpdateListeners(TableUpdateListener tableUpdateListener);

	List<ITableRow<VOType>> getRows();

	List<ITableRow<VOType>> getSelection();

	void delete(AsyncCallback<List<ITableRow<VOType>>> asyncCallback);

	void delete(List<ITableRow<VOType>> tableRowsToDelete, AsyncCallback<List<ITableRow<VOType>>> asyncCallback);

	void delete(ITableRow<VOType> tableRowToDelete, AsyncCallback<List<ITableRow<VOType>>> asyncCallback);

}
