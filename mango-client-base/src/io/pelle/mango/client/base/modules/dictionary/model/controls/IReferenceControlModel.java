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
package io.pelle.mango.client.base.modules.dictionary.model.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.IBaseLookupControlModel;

/**
 * Model for VO lookup
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public interface IReferenceControlModel extends IBaseLookupControlModel {

	public static int DEFAULT_SUGGESTIONS_LIMIT = 20;

	enum CONTROL_TYPE {
		TEXT, DROPDOWN
	}

	CONTROL_TYPE getControlType();

	int getSuggestionsLimit();
}
