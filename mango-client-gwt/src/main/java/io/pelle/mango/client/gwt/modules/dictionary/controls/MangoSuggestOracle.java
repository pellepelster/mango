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

import io.pelle.mango.client.base.modules.dictionary.controls.IReferenceControl;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gwt.user.client.ui.MultiWordSuggestOracle;

public class MangoSuggestOracle<VOTYPE extends IBaseVO> extends MultiWordSuggestOracle {

	private IReferenceControl<VOTYPE> referenceControl;

	public MangoSuggestOracle(IReferenceControl<VOTYPE> referenceControl) {
		super();
		this.referenceControl = referenceControl;
	}

	@Override
	public void requestSuggestions(final Request request, final Callback callback) {

		request.setLimit(referenceControl.getModel().getSuggestionsLimit());
		referenceControl.parseValue(request.getQuery(), new BaseErrorAsyncCallback<List<IReferenceControl.Suggestion<VOTYPE>>>() {

			@Override
			public void onSuccess(List<IReferenceControl.Suggestion<VOTYPE>> result) {

				Response response = new Response();

				Collection<SuggestionWrapper<VOTYPE>> voSuggestions = new ArrayList<SuggestionWrapper<VOTYPE>>();

				for (IReferenceControl.Suggestion<VOTYPE> suggestion : result) {
					voSuggestions.add(new SuggestionWrapper<VOTYPE>(suggestion, request.getQuery()));
				}

				response.setSuggestions(voSuggestions);
				response.setMoreSuggestions(result.size() > request.getLimit());

				callback.onSuggestionsReady(request, response);

			}
		});
	}
}
