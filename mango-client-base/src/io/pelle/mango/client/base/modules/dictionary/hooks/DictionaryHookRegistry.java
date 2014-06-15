package io.pelle.mango.client.base.modules.dictionary.hooks;

import io.pelle.mango.client.base.modules.dictionary.BaseDictionaryElementUtil;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IFileControlModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class DictionaryHookRegistry
{
	private static DictionaryHookRegistry instance;

	private Map<String, List<BaseEditorHook>> editorHooks = new HashMap<String, List<BaseEditorHook>>();

	private Map<String, IFileControlHook> fileControlHooks = new HashMap<String, IFileControlHook>();

	private DictionaryHookRegistry()
	{
	}

	public static DictionaryHookRegistry getInstance()
	{
		if (instance == null)
		{
			instance = new DictionaryHookRegistry();
		}

		return instance;
	}

	// editor hook
	public void addEditorHook(String dictionaryId, BaseEditorHook editorHook)
	{
		if (this.editorHooks.get(dictionaryId) == null)
		{
			this.editorHooks.put(dictionaryId, new ArrayList<BaseEditorHook>());
		}

		this.editorHooks.get(dictionaryId).add(editorHook);
	}

	public void setFileControlHook(IFileControlModel fileControlModel, IFileControlHook fileControlHook)
	{
		this.fileControlHooks.put(BaseDictionaryElementUtil.getModelId(fileControlModel), fileControlHook);
	}

	public IFileControlHook getFileControlHook(IFileControlModel fileControlModel)
	{
		return this.fileControlHooks.get(BaseDictionaryElementUtil.getModelId(fileControlModel));
	}

	public boolean hasFileControlHook(IFileControlModel fileControlModel)
	{
		return getFileControlHook(fileControlModel) != null;
	}

	public boolean hasEditorHook(String dictionaryId)
	{
		return this.editorHooks.containsKey(dictionaryId) && !this.editorHooks.get(dictionaryId).isEmpty();
	}

	public List<BaseEditorHook> getEditorHook(String dictionaryId)
	{
		return this.editorHooks.get(dictionaryId);
	}

}
