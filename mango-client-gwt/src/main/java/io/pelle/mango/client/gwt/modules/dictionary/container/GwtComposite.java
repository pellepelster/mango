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

import io.pelle.mango.client.base.modules.dictionary.model.containers.ICompositeModel;
import io.pelle.mango.client.web.modules.dictionary.container.Composite;
import io.pelle.mango.client.web.modules.dictionary.container.IContainer;

import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.user.client.ui.Panel;

/**
 * GWT {@link ICompositeModel} implementation
 * 
 * @author pelle
 * 
 */
public class GwtComposite extends Div implements IContainer<Panel> {

	public GwtComposite(Composite composite) {
		setWidth("100%");
	}

	/** {@inheritDoc} */
	@Override
	public Panel getContainer() {
		return this;
	}

}
