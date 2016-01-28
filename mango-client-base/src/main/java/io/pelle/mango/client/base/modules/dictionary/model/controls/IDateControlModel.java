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
 * Date control
 * 
 * @author Christian Pelster
 * @version $Rev$, $Date$
 * 
 */
public interface IDateControlModel extends IBaseControlModel {

	enum DATE_FORMAT {
		DATE_SHORT, DATE_TIME_SHORT, YEAR_MONTH_DAY_HOUR_MINUTE_SECOND
	}

	DATE_FORMAT getDateFormat();
}
