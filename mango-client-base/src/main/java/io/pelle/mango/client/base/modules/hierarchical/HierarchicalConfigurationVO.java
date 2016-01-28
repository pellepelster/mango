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
package io.pelle.mango.client.base.modules.hierarchical;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HierarchicalConfigurationVO implements Serializable
{

	private static final long serialVersionUID = 2347100754321469227L;

	private Map<String, List<String>> dictionaryHierarchy = new HashMap<String, List<String>>();

	private String id;

	private String title;

	public HierarchicalConfigurationVO()
	{
		super();
	}

	public HierarchicalConfigurationVO(String id, String title, Map<String, List<String>> dictionaryHierarchy)
	{
		super();
		this.id = id;
		this.title = title;
		this.dictionaryHierarchy = dictionaryHierarchy;
	}

	public boolean isRootDictionary(String dictionaryId)
	{
		return this.dictionaryHierarchy.get(dictionaryId).isEmpty();
	}

	public Collection<String> getDictionaryIds()
	{
		return this.dictionaryHierarchy.keySet();
	}

	public List<String> getChildDictionaryIds(String dictionaryId)
	{
		List<String> childDictionaries = new ArrayList<String>();

		for (Map.Entry<String, List<String>> hierarchyEntry : this.dictionaryHierarchy.entrySet())
		{
			if (hierarchyEntry.getValue().contains(dictionaryId))
			{
				childDictionaries.add(hierarchyEntry.getKey());
			}
		}

		return childDictionaries;
	}

	public static boolean isRootDictionary(String dictionaryId, List<HierarchicalConfigurationVO> hierarchicalConfigurationVOs)
	{
		for (HierarchicalConfigurationVO hierarchicalConfigurationVO : hierarchicalConfigurationVOs)
		{
			if (hierarchicalConfigurationVO.isRootDictionary(dictionaryId))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns the hierarchy configuration (Dictionary Name -> List of possible
	 * parents)
	 * 
	 * @return
	 */
	public Map<String, List<String>> getDictionaryHierarchy()
	{
		return this.dictionaryHierarchy;
	}

	public String getId()
	{
		return this.id;
	}

	public String getTitle()
	{
		return this.title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

}
