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

/**
 * Generic layout factory hiding the UI library specifics
 * 
 * @author pelle
 * 
 * @param <WidgetType>
 */
public interface ILayoutFactory<ContainerType, WidgetType> {

	public static final int AUTO_WIDTH = -1;

	void showModuleUI(IModuleUI<ContainerType, ?> moduleUI, String location);

	void closeModuleUI(IModuleUI<ContainerType, ?> moduleUI);

}
