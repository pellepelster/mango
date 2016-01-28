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

import io.pelle.mango.client.base.modules.dictionary.model.BaseModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public abstract class BaseHierarchicalConfiguration
{

	private Map<String, List<String>> hierarchy = new HashMap<String, List<String>>();

	private final String id;

	private final String title;

	public BaseHierarchicalConfiguration(String id, String title)
	{
		this.id = id;
		this.title = title;
	}

	@SuppressWarnings("rawtypes")
	protected void addHierarchy(BaseModel baseModel, BaseModel... parentModels)
	{
		if (parentModels.length > 0)
		{
			List<String> parentDictionaryNames = Lists.transform(Arrays.asList(parentModels), new Function<BaseModel, String>()
			{
				@Override
				@Nullable
				public String apply(@Nullable BaseModel input)
				{
					return input.getName();
				}
			});

			this.hierarchy.put(baseModel.getName(), new ArrayList<String>(parentDictionaryNames));
		}
		else
		{
			this.hierarchy.put(baseModel.getName(), Collections.<String>emptyList());
		}
	}

	public HierarchicalConfigurationVO getHierarchyConfigurationVO()
	{
		return new HierarchicalConfigurationVO(getId(), this.title, this.hierarchy);
	}

	public String getId()
	{
		return this.id;
	}

}
