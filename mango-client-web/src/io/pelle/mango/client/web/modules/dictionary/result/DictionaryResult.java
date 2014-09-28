package io.pelle.mango.client.web.modules.dictionary.result;

import io.pelle.mango.client.base.modules.dictionary.model.IDictionaryModel;
import io.pelle.mango.client.base.modules.dictionary.model.search.IResultModel;
import io.pelle.mango.client.base.modules.dictionary.search.IDictionaryResult;
import io.pelle.mango.client.base.util.SimpleCallback;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;
import io.pelle.mango.client.web.modules.dictionary.container.BaseTableElement;
import io.pelle.mango.client.web.modules.dictionary.editor.DictionaryEditorModuleFactory;

import java.util.HashSet;
import java.util.Set;

public class DictionaryResult<VOType extends IBaseVO> extends BaseTableElement<VOType, IResultModel> implements IDictionaryResult {

	public final static String CONTROL_FIRST_EDIT_DATA_KEY = "CONTROL_FIRST_EDIT_DATA_KEY";

	private Set<String> highlightTexts = new HashSet<String>();

	public DictionaryResult(final IDictionaryModel dictionaryModel, BaseDictionaryElement<?> parent) {
		super(dictionaryModel.getSearchModel().getResultModel(), parent);

		addActivateCallback(new SimpleCallback<VOType>() {

			@Override
			public void onCallback(VOType vo) {
				DictionaryEditorModuleFactory.openEditorForId(dictionaryModel.getName(), vo.getId());
			}
		});

	}

	public Set<String> getHighlightTexts() {
		return highlightTexts;
	}
}
