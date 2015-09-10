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
package io.pelle.mango.client.web.modules.dictionary.base;

import io.pelle.mango.client.base.db.vos.IHierarchicalVO;
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelProvider;
import io.pelle.mango.client.base.modules.dictionary.model.IDictionaryModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IHierarchicalControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IReferenceControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.ITextControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.base.vo.query.expressions.ExpressionFactory;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.controls.ControlContentPresenter;
import io.pelle.mango.client.web.modules.dictionary.editor.DictionaryEditor;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;
import io.pelle.mango.client.web.util.DictionaryModelUtil;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Utilities for dictionary model handling
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public final class DictionaryUtil {

	public static String getDictionaryAdd(IDictionaryModel dictionaryModel) {
		String title = DictionaryModelUtil.getEditorLabel(dictionaryModel);

		return MangoClientWeb.getInstance().getMessages().dictionaryAdd(title);

	}

	public static String getEditorLabel(IDictionaryModel dictionaryModel, DictionaryEditor<?> dictionaryEditor) {

		IBaseVO vo = dictionaryEditor.getVO();

		String label = DictionaryModelUtil.getEditorLabel(dictionaryModel);

		if (vo == null || vo.isNew()) {
			label += " (" + MangoClientWeb.getInstance().getMessages().editorNew() + ")";
		} else {
			label += " " + getLabel(dictionaryModel.getLabelControls(), vo);
		}

		if (dictionaryEditor.isDirty()) {
			label += " " + MangoClientWeb.getInstance().getMessages().editorDirtyMarker();
		}

		return label;

	}

	public static String getLabel(IDictionaryModel dictionaryModel) {
		return Objects.firstNonNull(dictionaryModel.getLabel(), dictionaryModel.getName());
	}

	public static String getLabel(IDictionaryModel dictionaryModel, IBaseVO vo) {
		return getLabel(dictionaryModel.getLabelControls(), vo);
	};

	public static String getLabel(IHierarchicalControlModel hierarchicalControlModel, IHierarchicalVO vo, String defaultLabel) {
		IDictionaryModel dictionaryModel = DictionaryModelProvider.getDictionaryModelForClass(vo.getClass());

		return getLabel(dictionaryModel.getLabelControls(), vo);
	}

	public static String getLabel(IReferenceControlModel referenceControlModel, IBaseVO vo) {

		IDictionaryModel dictionaryModel = DictionaryModelProvider.getDictionary(referenceControlModel.getDictionaryName());

		List<IBaseControlModel> labelControlModels = Collections.emptyList();

		if (!referenceControlModel.getLabelControls().isEmpty()) {
			labelControlModels = referenceControlModel.getLabelControls();
		} else {
			if (!dictionaryModel.getLabelControls().isEmpty()) {
				labelControlModels = dictionaryModel.getLabelControls();
			} else {

				if (vo.hasNaturalKey()) {
					return vo.getNaturalKey();
				} else {
					labelControlModels = dictionaryModel.getSearchModel().getResultModel().getControls();
				}
			}
		}

		return DictionaryUtil.getLabel(labelControlModels, vo);
	}

	public static String getLabel(List<IBaseControlModel> baseControlModels, IBaseVO vo) {

		String label = null;
		String delimiter = "";

		if (vo != null) {
			for (IBaseControlModel baseControlModel : baseControlModels) {
				if (label == null) {
					label = "";
				}
				try {
					String controlLabel = ControlContentPresenter.getControlContent(baseControlModel, vo.get(baseControlModel.getAttributePath()));
					if (controlLabel != null && !controlLabel.isEmpty()) {
						label += delimiter + controlLabel;
						delimiter = ", ";
					}
				} catch (RuntimeException e) {
					GWT.log(e.getMessage(), e);
				}
			}
		}

		return (label == null) ? vo.getNaturalKey() : label;

	}

	public static String getSearchLabel(IDictionaryModel dictionaryModel, int resultCount, boolean moreResultsAvailable) {

		String countText = null;
		if (moreResultsAvailable) {
			countText = MangoClientWeb.getInstance().getMessages().searchResultsMoreAvailable(resultCount);
		} else {
			countText = MangoClientWeb.getInstance().getMessages().searchResults(resultCount);
		}

		return getSearchLabel(dictionaryModel) + " (" + countText + ")";
	}

	public static String getSearchLabel(IDictionaryModel dictionaryModel) {
		return Objects.firstNonNull(dictionaryModel.getSearchModel().getLabel(), getLabel(dictionaryModel));
	}

	private DictionaryUtil() {
		super();
	}

	public static IDictionaryModel getDictionaryModel(IReferenceControlModel controlModel) {
		return DictionaryModelProvider.getDictionary(controlModel.getDictionaryName());
	}

	public static <VOTYPE extends IBaseVO> void getEntitiesByDictionaryLabel(IReferenceControlModel controlModel, String text, final AsyncCallback<List<VOTYPE>> resultCallback) {
		IDictionaryModel dictionaryModel = getDictionaryModel(controlModel);
		getEntitiesByDictionaryLabel(dictionaryModel, text, controlModel.getSuggestionsLimit(), resultCallback);
	}

	@SuppressWarnings("unchecked")
	public static <VOTYPE extends IBaseVO> void getEntitiesByDictionaryLabel(IDictionaryModel dictionaryModel, String text, int limit, final AsyncCallback<List<VOTYPE>> resultCallback) {

		SelectQuery<VOTYPE> selectQuery = (SelectQuery<VOTYPE>) SelectQuery.selectFrom(dictionaryModel.getVOClass());

		Optional<IBooleanExpression> expression = Optional.absent();

		for (IBaseControlModel baseControlModel : dictionaryModel.getLabelControls()) {

			Optional<IBooleanExpression> compareExpression = Optional.absent();

			if (baseControlModel instanceof ITextControlModel) {
				compareExpression = ExpressionFactory.createStringStartsWithExpression(dictionaryModel.getVOClass(), baseControlModel.getAttributePath(), text);
			}

			if (compareExpression.isPresent()) {
				if (expression.isPresent()) {
					expression = Optional.of(expression.get().or(compareExpression.get()));
				} else {
					expression = compareExpression;
				}
			}
		}

		if (expression.isPresent()) {
			selectQuery.where(expression.get());
		}

		selectQuery.setMaxResults(limit + 1);

		MangoClientWeb.getInstance().getRemoteServiceLocator().getBaseEntityService().filter(selectQuery, new BaseErrorAsyncCallback<List<VOTYPE>>(resultCallback) {
			@Override
			public void onSuccess(List<VOTYPE> result) {
				resultCallback.onSuccess(result);
			}
		});
	}

}
