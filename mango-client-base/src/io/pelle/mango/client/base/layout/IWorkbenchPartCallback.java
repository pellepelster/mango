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
 * Callback for workbench part interaction
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public interface IWorkbenchPartCallback
{

	/**
	 * Brings the workbench part to top
	 */
	void activate();

	/**
	 * Closes the current workbench part
	 */
	void close();

	/**
	 * Returns the parts title
	 * 
	 * @param title
	 */
	String getTitle();

	/**
	 * Sets the parts title
	 * 
	 * @param title
	 */
	void setTitle(String title);

}
