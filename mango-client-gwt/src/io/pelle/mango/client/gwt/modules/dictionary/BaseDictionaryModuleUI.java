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
package io.pelle.mango.client.gwt.modules.dictionary;

import io.pelle.mango.client.base.module.IModule;

/**
 * Basic module UI implementation
 * 
 * @author pelle
 * 
 */
public abstract class BaseDictionaryModuleUI<ModuleType extends IModule> extends BaseGwtModuleUI<ModuleType>
{

	public BaseDictionaryModuleUI(ModuleType module, String uiModuleId)
	{
		super(module, uiModuleId);
	}

}
