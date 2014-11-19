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
package io.pelle.mango.client.base.layout;

import io.pelle.mango.client.base.module.IModule;

/**
 * Represents the UI for a module instance
 * 
 * @author pelle
 * 
 * @param <WidgetType>
 */
public interface IModuleUI<ContainerType, ModuleType extends IModule> {
	static final String UI_MODULE_ID_PARAMETER_NAME = "moduleUIName";

	enum CONTAINER_LAYOUT {
		HORIZONTAL, VERTICAL
	}

	int MARGIN = 15;

	int getOrder();

	boolean close();

	boolean contributesToBreadCrumbs();

	ContainerType getContainer();

	ModuleType getModule();

	String getTitle();

	boolean isInstanceOf(String moduleUrl);

	void updateUrl(String moduleUrl);

	void onResize();

}
