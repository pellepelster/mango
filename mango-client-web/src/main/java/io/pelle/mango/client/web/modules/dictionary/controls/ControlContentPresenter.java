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
package io.pelle.mango.client.web.modules.dictionary.controls;

import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;

@Deprecated
public final class ControlContentPresenter
{

	public static String getControlContent(IBaseControlModel controlModel, Object content)
	{
		String result = null;

		if (content != null)
		{
			result = content.toString();
		}

		return result;
	}

	private ControlContentPresenter()
	{
	}

}
