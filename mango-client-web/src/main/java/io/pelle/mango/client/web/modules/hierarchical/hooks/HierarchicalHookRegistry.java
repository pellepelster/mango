package io.pelle.mango.client.web.modules.hierarchical.hooks;

import java.util.HashMap;
import java.util.Map;

public class HierarchicalHookRegistry
{
	private static HierarchicalHookRegistry instance;

	private Map<String, BaseActivationHook> activationHooks = new HashMap<String, BaseActivationHook>();

	private HierarchicalHookRegistry()
	{
	}

	public static HierarchicalHookRegistry getInstance()
	{
		if (instance == null)
		{
			instance = new HierarchicalHookRegistry();
		}

		return instance;
	}

	public void addActivationHook(String treeId, BaseActivationHook activationHook)
	{
		if (this.activationHooks.containsKey(treeId))
		{
			throw new RuntimeException("hook already registered for tree '" + treeId + "'");
		}

		this.activationHooks.put(treeId, activationHook);
	}

	public boolean hasActivationHook(String treeId)
	{
		return this.activationHooks.get(treeId) != null;
	}

	public BaseActivationHook getActivationHook(String treeId)
	{
		return this.activationHooks.get(treeId);
	}

}
