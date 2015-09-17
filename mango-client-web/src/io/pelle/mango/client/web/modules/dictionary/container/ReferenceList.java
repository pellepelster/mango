package io.pelle.mango.client.web.modules.dictionary.container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
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
import io.pelle.mango.client.web.modules.dictionary.base.Button;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;

public class ReferenceList<VOTYPE extends IBaseVO> extends BaseContainerElement<IReferenceListModel<VOTYPE>, IUpdateListener>implements IReferenceList<VOTYPE> {

	private List<VOTYPE> availableVOs = new ArrayList<VOTYPE>();

	private Button addButton = new Button();

	private Button removeButton = new Button();

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
		return new ArrayList<VOTYPE>(Collections2.filter(availableVOs, new Predicate<VOTYPE>() {

			@Override
			public boolean apply(VOTYPE input) {
				return !getListInternal().contains(input);
			}
		}));
	}

	@Override
	protected void fireUpdateListeners() {

		addButton.setEnabled(!getAvailableVOs().isEmpty());
		removeButton.setEnabled(!getSelectedVOs().isEmpty());

		super.fireUpdateListeners();
	}

	@Override
	public void addVOs(Collection<VOTYPE> vos) {
		getListInternal().addAll(vos);
		fireUpdateListeners();
	}

	@SuppressWarnings("unchecked")
	private List<VOTYPE> getListInternal() {
		return (List<VOTYPE>) getParent().getVOWrapper().get(getModel().getAttributePath());
	}

	@Override
	public List<VOTYPE> getSelectedVOs() {
		return getListInternal();
	}

	@Override
	public void removeVOs(Collection<VOTYPE> vos) {
		getListInternal().removeAll(vos);
		fireUpdateListeners();
	}

	public Button getRemoveButton() {
		return removeButton;
	}

	public Button getAddButton() {
		return addButton;
	}

}
