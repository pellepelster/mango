package io.pelle.mango.client.base.modules.dictionary.container;

import io.pelle.mango.client.base.vo.IBaseVO;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IEditableTable<VOType extends IBaseVO> extends IBaseTable<VOType> {
	
	void add(AsyncCallback<List<ITableRow<VOType>>> asyncCallback);
	
}
