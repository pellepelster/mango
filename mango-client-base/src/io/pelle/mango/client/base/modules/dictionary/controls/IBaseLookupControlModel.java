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
package io.pelle.mango.client.base.modules.dictionary.controls;

import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;

import java.util.List;

/**
 * Represents a basic control model for reference lookups
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public interface IBaseLookupControlModel extends IBaseControlModel
{

	/**
	 * Name of the dictionary for the lookup
	 * 
	 * @return
	 */
	String getDictionaryName();

	/**
	 * List of controls for displaying the lookup up VOs
	 * 
	 * @return
	 */
	List<IBaseControlModel> getLabelControls();

}
