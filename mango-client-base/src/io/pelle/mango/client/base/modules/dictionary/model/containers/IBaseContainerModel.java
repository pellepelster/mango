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
package io.pelle.mango.client.base.modules.dictionary.model.containers;

import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;

import java.util.List;

public interface IBaseContainerModel extends IBaseModel
{
	/**
	 * All child composites
	 * 
	 * @return
	 */
	List<IBaseContainerModel> getChildren();

	/**
	 * All controls for this composite
	 * 
	 * @return
	 */
	List<IBaseControlModel> getControls();

}
