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
package io.pelle.mango.client.base.modules.dictionary.model.search;

import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;

import java.util.List;

/**
 * Model for a generic search UI consisting of one or more filters and a result
 * list
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public interface ISearchModel extends IBaseModel {

	List<IFilterModel> getFilterModels();

	IResultModel getResultModel();

	String getLabel();

	boolean isCreateEnabled();

}
