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
package io.pelle.mango.client.web.modules.dictionary.container;

/**
 * Container UI
 * 
 * @author pelle
 * 
 */
public interface IContainer<ContainerType>
{

	/**
	 * Returns the widget wrapped by this control
	 * 
	 * @return
	 */
	ContainerType getContainer();
}
