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
package io.pelle.mango.client.gwt.modules.dictionary.controls;

import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelUtil;
import io.pelle.mango.client.gwt.ControlHelper;
import io.pelle.mango.client.web.modules.dictionary.controls.BooleanControl;
import io.pelle.mango.client.web.modules.dictionary.controls.IGwtControl;

import com.google.gwt.user.client.ui.CheckBox;

public class GwtBooleanControl extends CheckBox implements IGwtControl
{

	public GwtBooleanControl(BooleanControl booleanControl)
	{
		new ControlHelper(this, booleanControl, this, true, false);
		ensureDebugId(DictionaryModelUtil.getDebugId(booleanControl.getModel()));

	}

	@Override
	public void setContent(Object content)
	{
		if (content != null)
		{

			if (content instanceof Boolean)
			{
				super.setValue((Boolean) content);
			}
			else
			{
				throw new RuntimeException("unsupported value type '" + content.getClass().getName() + "'");
			}

		}
		else
		{
			super.setValue(null);
		}
	}
}
