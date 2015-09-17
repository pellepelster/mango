package io.pelle.mango.client.base.modules.dictionary.container;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import io.pelle.mango.client.base.modules.dictionary.IUpdateListener;
import io.pelle.mango.client.base.vo.IBaseVO;

public interface IReferenceList<VOTYPE extends IBaseVO> extends IBaseContainer<IUpdateListener> {

	List<VOTYPE> getAvailableVOs();

	void updateReferenceList(final AsyncCallback<Void> asyncCallback);

}
