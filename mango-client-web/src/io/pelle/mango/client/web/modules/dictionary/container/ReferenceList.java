package io.pelle.mango.client.web.modules.dictionary.container;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import io.pelle.mango.client.base.modules.dictionary.IUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.container.IReferenceList;
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelProvider;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.IDictionaryModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IReferenceListModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;

public class ReferenceList<VOTYPE extends IBaseVO> extends BaseContainerElement<IReferenceListModel<VOTYPE>, IUpdateListener>implements IReferenceList<VOTYPE> {

	private List<VOTYPE> availableVOs = new ArrayList<VOTYPE>();

	public ReferenceList(IReferenceListModel<VOTYPE> referenceListModel, BaseDictionaryElement<? extends IBaseModel> parent) {
		super(referenceListModel, parent);
	}

	@Override
	public void updateReferenceList(final AsyncCallback<Void> asyncCallback) {

		IDictionaryModel dictionaryModel = DictionaryModelProvider.getDictionary(getModel().getDictionaryName());

		@SuppressWarnings("unchecked")
		SelectQuery<VOTYPE> selectQuery = (SelectQuery<VOTYPE>) SelectQuery.selectFrom(dictionaryModel.getVOClass());

		MangoClientWeb.getInstance().getRemoteServiceLocator().getBaseEntityService().filter(selectQuery, new BaseErrorAsyncCallback<List<VOTYPE>>() {

			@Override
			public void onSuccess(List<VOTYPE> result) {
				availableVOs.clear();
				availableVOs.addAll(result);

				fireUpdateListeners();

				if (asyncCallback != null) {
					asyncCallback.onSuccess(null);
				}
			}
		});

	}

	@Override
	public List<VOTYPE> getAvailableVOs() {
		return availableVOs;
	}

}
