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

import java.util.Map;

/**
 * Model for a text control
 * 
 * @author Christian Pelster
 * @version $Rev$, $Date$
 * 
 */
public interface IEnumerationControlModel extends IBaseControlModel
{

	Map<String, String> getEnumeration();

}
