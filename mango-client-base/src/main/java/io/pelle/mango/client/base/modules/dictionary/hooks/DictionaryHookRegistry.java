package io.pelle.mango.client.base.modules.dictionary.hooks;

import io.pelle.mango.client.base.modules.dictionary.BaseDictionaryElementUtil;
import io.pelle.mango.client.base.modules.dictionary.controls.IButton;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IBaseTableModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IFileControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.search.ISearchModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pelle
 *
 */
@SuppressWarnings("rawtypes")
public class DictionaryHookRegistry {
	private static DictionaryHookRegistry instance;

	private Map<String, List<BaseEditorHook>> editorHooks = new HashMap<String, List<BaseEditorHook>>();

	private Map<String, List<IButton>> editorButtons = new HashMap<String, List<IButton>>();

	private Map<String, List<BaseSearchHook>> searchHooks = new HashMap<String, List<BaseSearchHook>>();

	private Map<String, IFileControlHook> fileControlHooks = new HashMap<String, IFileControlHook>();

	private Map<String, BaseTableHook> tableHooks = new HashMap<String, BaseTableHook>();

	private DictionaryHookRegistry() {
	}

	public static DictionaryHookRegistry getInstance() {
		if (instance == null) {
			instance = new DictionaryHookRegistry();
		}

		return instance;
	}

	public void addTableHook(IBaseTableModel baseTableModel, BaseTableHook<?> baseTableHook) {
		this.tableHooks.put(BaseDictionaryElementUtil.getModelId(baseTableModel), baseTableHook);
	}

	public IBaseTableHook<?> getTableHook(IBaseTableModel baseTableModel) {
		return this.tableHooks.get(BaseDictionaryElementUtil.getModelId(baseTableModel));
	}

	public void addSearchHook(ISearchModel searchModel, BaseSearchHook baseSearchHook) {
		if (this.searchHooks.get(searchModel.getName()) == null) {
			this.searchHooks.put(searchModel.getName(), new ArrayList<BaseSearchHook>());
		}

		this.searchHooks.get(searchModel.getName()).add(baseSearchHook);
	}

	public void setFileControlHook(IFileControlModel fileControlModel, IFileControlHook fileControlHook) {
		this.fileControlHooks.put(BaseDictionaryElementUtil.getModelId(fileControlModel), fileControlHook);
	}

	public IFileControlHook getFileControlHook(IFileControlModel fileControlModel) {
		return this.fileControlHooks.get(BaseDictionaryElementUtil.getModelId(fileControlModel));
	}

	public boolean hasFileControlHook(IFileControlModel fileControlModel) {
		return getFileControlHook(fileControlModel) != null;
	}

	/*
	 * editor hook
	 */
	public void addEditorHook(String dictionaryId, BaseEditorHook editorHook) {
		if (this.editorHooks.get(dictionaryId) == null) {
			this.editorHooks.put(dictionaryId, new ArrayList<BaseEditorHook>());
		}

		this.editorHooks.get(dictionaryId).add(editorHook);
	}

	public boolean hasEditorHook(String dictionaryId) {
		return this.editorHooks.containsKey(dictionaryId) && !this.editorHooks.get(dictionaryId).isEmpty();
	}

	public List<BaseEditorHook> getEditorHook(String dictionaryId) {
		return this.editorHooks.get(dictionaryId);
	}

	/*
	 * editor buttons
	 */
	public void addEditorButton(String editorId, IButton button) {
		if (this.editorButtons.get(editorId) == null) {
			this.editorButtons.put(editorId, new ArrayList<IButton>());
		}

		this.editorButtons.get(editorId).add(button);
	}

	public boolean hasEditorButtons(String editorId) {
		return this.editorButtons.containsKey(editorId) && !this.editorButtons.get(editorId).isEmpty();
	}

	public List<IButton> getEditorButtons(String editorId) {
		return this.editorButtons.get(editorId);
	}

	/*
	 * search hook
	 */
	public boolean hasSearchHook(ISearchModel searchModel) {
		return this.searchHooks.containsKey(searchModel.getName()) && !this.searchHooks.get(searchModel.getName()).isEmpty();
	}

	public List<BaseSearchHook> getSearchHook(ISearchModel searchModel) {
		return this.searchHooks.get(searchModel.getName());
	}

	public void clearAll() {
		editorHooks.clear();
		fileControlHooks.clear();
		searchHooks.clear();
		tableHooks.clear();
	}

}
