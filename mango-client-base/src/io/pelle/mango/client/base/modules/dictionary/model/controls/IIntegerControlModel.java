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

/**
 * Model for a integer control
 * 
 * @author Christian Pelster
 * @version $Rev$, $Date$
 * 
 */
public interface IIntegerControlModel extends IBaseControlModel {

	public static int MAX_DEFAULT = Integer.MAX_VALUE;

	public static int MIN_DEFAULT = Integer.MIN_VALUE;

	Integer getMax();

	Integer getMin();

	enum CONTROL_TYPE {
		TEXTCONTROL, RATING
	}

	CONTROL_TYPE getControlType();

}
