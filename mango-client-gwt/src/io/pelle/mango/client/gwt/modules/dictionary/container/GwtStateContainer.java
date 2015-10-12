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
package io.pelle.mango.client.gwt.modules.dictionary.container;

import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.user.client.ui.Panel;

import io.pelle.mango.client.base.modules.dictionary.IUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.model.containers.ICompositeModel;
import io.pelle.mango.client.web.modules.dictionary.container.IContainer;
import io.pelle.mango.client.web.modules.dictionary.container.StateContainer;

/**
 * GWT {@link ICompositeModel} implementation
 * 
 * @author pelle
 * 
 */
public class GwtStateContainer extends Div implements IContainer<Panel>, IUpdateListener {

	public GwtStateContainer(StateContainer stateContainer) {

		onUpdate();
	}

	/** {@inheritDoc} */
	@Override
	public Panel getContainer() {
		return this;
	}

	@Override
	public void onUpdate() {
	}

}
