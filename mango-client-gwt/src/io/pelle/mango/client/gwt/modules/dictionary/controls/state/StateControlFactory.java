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
package io.pelle.mango.client.gwt.modules.dictionary.controls.state;

import com.google.gwt.user.client.ui.Widget;

import io.pelle.mango.client.base.layout.LAYOUT_TYPE;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IStateControlModel;
import io.pelle.mango.client.gwt.modules.dictionary.controls.BaseControlFactory;
import io.pelle.mango.client.gwt.modules.dictionary.controls.ReadonlyControl;
import io.pelle.mango.client.web.modules.dictionary.controls.BaseDictionaryControl;
import io.pelle.mango.client.web.modules.dictionary.controls.StateControl;

public class StateControlFactory extends BaseControlFactory<IStateControlModel, StateControl> {

	/** {@inheritDoc} */
	@Override
	public Widget createControl(StateControl stateControl, LAYOUT_TYPE layoutType) {
		if (stateControl.getModel().isReadonly()) {
			return new ReadonlyControl(stateControl);
		} else {
			return new GwtStateControl(stateControl);
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean supports(BaseDictionaryControl<?, ?> baseControl) {
		return baseControl instanceof StateControl;
	}

}
