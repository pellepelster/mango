/**
 * Copyright (c) 2013 Christian Pelster.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Christian Pelster - initial API and implementation
 */
package io.pelle.mango.client.gwt.modules.dictionary.controls;

import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelProvider;
import io.pelle.mango.client.base.modules.dictionary.model.IDictionaryModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IReferenceControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.base.DictionaryUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestOracle;

public class VOSuggestOracle<VOType extends IBaseVO> extends SuggestOracle {
	private IReferenceControlModel referenceControlModel;

	public VOSuggestOracle(IReferenceControlModel referenceControlModel) {
		super();
		this.referenceControlModel = referenceControlModel;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void requestSuggestions(final Request request, final Callback callback) {

		IDictionaryModel dictionaryModel = DictionaryModelProvider.getDictionary(referenceControlModel.getDictionaryName());

		SelectQuery<VOType> selectQuery = (SelectQuery<VOType>) SelectQuery.selectFrom(dictionaryModel.getVOClass());

		MangoClientWeb.getInstance().getRemoteServiceLocator().getBaseEntityService().filter(selectQuery, new AsyncCallback<List<VOType>>() {

			@Override
			public void onFailure(Throwable caught) {
				throw new RuntimeException("error loading suggestions for '" + request.getQuery() + "'");
			}

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(List<VOType> result) {
				Response response = new Response();
				Collection<VOSuggestion> voSuggestions = new ArrayList<VOSuggestion>();

				for (IBaseVO vo : result) {
					VOSuggestion voSuggestion = new VOSuggestion<VOType>(DictionaryUtil.getLabel(referenceControlModel, vo), (VOType) vo);
					voSuggestions.add(voSuggestion);
				}

				response.setSuggestions(voSuggestions);
				response.setMoreSuggestions(result.size() > request.getLimit());

				callback.onSuggestionsReady(request, response);
			}
		});
	}
}
