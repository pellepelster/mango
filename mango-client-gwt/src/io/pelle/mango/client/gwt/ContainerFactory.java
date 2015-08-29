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
package io.pelle.mango.client.gwt;

import com.google.gwt.user.client.ui.Panel;

import io.pelle.mango.client.gwt.modules.dictionary.container.GwtAssignmentTable;
import io.pelle.mango.client.gwt.modules.dictionary.container.GwtComposite;
import io.pelle.mango.client.gwt.modules.dictionary.container.GwtEditableTable;
import io.pelle.mango.client.gwt.modules.dictionary.container.GwtFileList;
import io.pelle.mango.client.gwt.modules.dictionary.container.GwtTabFolder;
import io.pelle.mango.client.web.modules.dictionary.container.AssignmentTable;
import io.pelle.mango.client.web.modules.dictionary.container.BaseContainerElement;
import io.pelle.mango.client.web.modules.dictionary.container.Composite;
import io.pelle.mango.client.web.modules.dictionary.container.EditableTable;
import io.pelle.mango.client.web.modules.dictionary.container.FileList;
import io.pelle.mango.client.web.modules.dictionary.container.IContainer;
import io.pelle.mango.client.web.modules.dictionary.container.TabFolder;

/**
 * Factory for creation of container UI elements
 * 
 * @author pelle
 * 
 */
public class ContainerFactory {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static IContainer<Panel> createContainer(BaseContainerElement baseContainer, ColumnLayoutStrategy columnLayoutStrategy) {
		IContainer<Panel> container;

		if (baseContainer instanceof FileList) {
			return new GwtFileList((FileList) baseContainer);
		} else if (baseContainer instanceof TabFolder) {
			return new GwtTabFolder((TabFolder) baseContainer, columnLayoutStrategy);
		} else if (baseContainer instanceof Composite) {
			return new GwtComposite((Composite) baseContainer);
		} else if (baseContainer instanceof EditableTable) {
			container = new GwtEditableTable((EditableTable) baseContainer);
		} else if (baseContainer instanceof AssignmentTable) {
			container = new GwtAssignmentTable((AssignmentTable) baseContainer);
		} else {
			throw new RuntimeException("unsupported container type '" + baseContainer.getClass().getName() + "'");
		}

		return container;

	}
}
