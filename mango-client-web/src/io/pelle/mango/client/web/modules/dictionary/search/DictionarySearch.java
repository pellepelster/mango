package io.pelle.mango.client.web.modules.dictionary.search;

import io.pelle.mango.client.base.modules.dictionary.IVOWrapper;
import io.pelle.mango.client.base.modules.dictionary.container.IBaseTable;
import io.pelle.mango.client.base.modules.dictionary.model.IDictionaryModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.search.IFilterModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.base.vo.query.expressions.PathExpression;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;
import io.pelle.mango.client.web.modules.dictionary.base.DictionaryUtil;
import io.pelle.mango.client.web.modules.dictionary.controls.BaseDictionaryControl;
import io.pelle.mango.client.web.modules.dictionary.filter.DictionaryFilter;
import io.pelle.mango.client.web.modules.dictionary.query.DictionaryCompositeQuery;
import io.pelle.mango.client.web.modules.dictionary.result.DictionaryResult;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;
import io.pelle.mango.client.web.util.DummyAsyncCallback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Optional;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DictionarySearch<VOType extends IBaseVO> extends BaseDictionaryElement<IDictionaryModel> {

	private DictionaryResult<VOType> dictionaryResult;

	private List<DictionaryFilter<VOType>> dictionaryFilters = new ArrayList<DictionaryFilter<VOType>>();

	private SearchVOWrapper<VOType> voWrapper = new SearchVOWrapper<VOType>();

	private List<ISearchUpdateListener> updateListeners = new ArrayList<ISearchUpdateListener>();

	public DictionarySearch(IDictionaryModel dictionaryModel) {
		super(dictionaryModel, null);

		this.dictionaryResult = new DictionaryResult<VOType>(getModel(), this);

		for (IFilterModel filterModel : getModel().getSearchModel().getFilterModels()) {
			this.dictionaryFilters.add(new DictionaryFilter(filterModel, this));
		}
	}

	public void search() {
		search(DummyAsyncCallback.<List<IBaseTable.ITableRow<VOType>>> dummyAsyncCallback());
	}

	@SuppressWarnings("unchecked")
	public void search(final AsyncCallback<List<IBaseTable.ITableRow<VOType>>> asyncCallback) {

		SelectQuery<VOType> selectQuery = (SelectQuery<VOType>) SelectQuery.selectFrom(getModel().getVOClass());

		Collection<BaseDictionaryControl<? extends IBaseControlModel, ?>> baseControls = DictionaryCompositeQuery.create(getActiveFilter().getRootComposite()).getAllControls();

		Optional<IBooleanExpression> expression = Optional.absent();

		for (BaseDictionaryControl<? extends IBaseControlModel, ?> baseControl : baseControls) {

			PathExpression pathExpression = new PathExpression(getModel().getVOClass().getName(), baseControl.getModel().getAttributePath());
			Optional<IBooleanExpression> compareExpression = baseControl.getExpression(pathExpression);

			if (compareExpression.isPresent()) {
				if (expression.isPresent()) {
					expression = Optional.of(expression.get().and(compareExpression.get()));
				} else {
					expression = compareExpression;
				}
			}
		}

		if (expression.isPresent()) {
			selectQuery.where(expression.get());
		}

		MangoClientWeb.getInstance().getRemoteServiceLocator().getBaseEntityService().filter(selectQuery, new BaseErrorAsyncCallback<List<VOType>>() {

			@Override
			public void onSuccess(List<VOType> result) {
				DictionarySearch.this.dictionaryResult.setRows(result);
				asyncCallback.onSuccess(DictionarySearch.this.dictionaryResult.getRows());
				fireUpdateListeners();
			}
		});
	}

	public DictionaryFilter<VOType> getActiveFilter() {
		return this.dictionaryFilters.get(0);
	}

	public DictionaryResult<VOType> getDictionaryResult() {
		return this.dictionaryResult;
	}

	public boolean hasFilter() {
		return !this.dictionaryFilters.isEmpty();
	}

	@Override
	public IVOWrapper<? extends IBaseVO> getVOWrapper() {
		return this.voWrapper;
	}

	@Override
	public List<? extends BaseDictionaryElement<?>> getAllChildren() {
		List<BaseDictionaryElement<?>> allChildren = new ArrayList<BaseDictionaryElement<?>>();

		for (DictionaryFilter<VOType> dictionaryFilter : this.dictionaryFilters) {
			allChildren.addAll(dictionaryFilter.getAllChildren());
		}

		allChildren.addAll(this.dictionaryResult.getAllChildren());

		return allChildren;
	}

	public String getTitle() {
		return DictionaryUtil.getSearchLabel(getModel(), getDictionaryResult().getRows().size());
	}

	private void fireUpdateListeners() {
		for (ISearchUpdateListener updateListener : this.updateListeners) {
			updateListener.onUpdate();
		}
	}

	public void addUpdateListener(ISearchUpdateListener updateListener) {
		this.updateListeners.add(updateListener);
	}

}
