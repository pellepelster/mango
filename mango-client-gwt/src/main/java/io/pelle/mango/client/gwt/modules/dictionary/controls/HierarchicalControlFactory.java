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

import io.pelle.mango.client.base.layout.LAYOUT_TYPE;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IHierarchicalControlModel;
import io.pelle.mango.client.web.modules.dictionary.controls.BaseDictionaryControl;
import io.pelle.mango.client.web.modules.dictionary.controls.HierarchicalControl;

import com.google.gwt.user.client.ui.Widget;

public class HierarchicalControlFactory extends BaseControlFactory<IHierarchicalControlModel, HierarchicalControl> {

	/** {@inheritDoc} */
	@Override
	public Widget createControl(HierarchicalControl hierarchicalControl, LAYOUT_TYPE layoutType) {
		return new GwtHierarchicalControl(hierarchicalControl);
	}

	/** {@inheritDoc} */
	@Override
	public boolean supports(BaseDictionaryControl<?, ?> baseControl) {
		return baseControl instanceof HierarchicalControl;
	}

}
