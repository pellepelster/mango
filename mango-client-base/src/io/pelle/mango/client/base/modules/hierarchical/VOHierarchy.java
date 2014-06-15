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

import io.pelle.mango.client.base.db.vos.IHierarchicalVO;
import io.pelle.mango.client.base.modules.dictionary.model.IDictionaryModel;

import java.io.Serializable;
import java.util.List;

public class VOHierarchy implements Serializable
{
	private static final long serialVersionUID = 2205436841248608738L;

	private Class<? extends IHierarchicalVO> clazz;

	private List<Class<? extends IHierarchicalVO>> parents;

	private IDictionaryModel dictionaryModel;

	public VOHierarchy()
	{
		super();
	}

	public VOHierarchy(IDictionaryModel dictionaryModel, Class<? extends IHierarchicalVO> clazz, List<Class<? extends IHierarchicalVO>> parents)
	{
		super();
		this.dictionaryModel = dictionaryModel;
		this.clazz = clazz;
		this.parents = parents;
	}

	public Class<? extends IHierarchicalVO> getClazz()
	{
		return clazz;
	}

	public IDictionaryModel getDictionaryModel()
	{
		return dictionaryModel;
	}

	public List<Class<? extends IHierarchicalVO>> getParents()
	{
		return parents;
	}

	public void setClazz(Class<? extends IHierarchicalVO> clazz)
	{
		this.clazz = clazz;
	}

	public void setDictionaryModel(IDictionaryModel dictionaryModel)
	{
		this.dictionaryModel = dictionaryModel;
	}

	public void setParents(List<Class<? extends IHierarchicalVO>> parents)
	{
		this.parents = parents;
	}

}