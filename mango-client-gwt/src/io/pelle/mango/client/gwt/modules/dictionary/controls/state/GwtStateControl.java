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

import com.google.gwt.user.client.ui.TextBox;

import io.pelle.mango.client.gwt.modules.dictionary.controls.BaseControlWithHelp;
import io.pelle.mango.client.web.modules.dictionary.controls.IGwtControl;
import io.pelle.mango.client.web.modules.dictionary.controls.StateControl;

public class GwtStateControl extends BaseControlWithHelp<TextBox> implements IGwtControl {

	public GwtStateControl(final StateControl stateControl) {
		
		super(new TextBox(), stateControl.getModel());

	}

	public void setContent(Object content) {
	
	}

}
