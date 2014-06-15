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
package io.pelle.mango.client.base.module;

import java.util.Map;

/**
 * MyAdmin module interface
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public interface IModule
{
	public interface IModuleUpdateListener
	{
		void onUpdate();
	}

	static final String MODULE_ID_PARAMETER_NAME = "moduleId";

	String getModuleUrl();

	boolean isInstanceOf(String moduleUrl);

	int getOrder();

	String getTitle();

	void updateUrl(String moduleUrl);
}
