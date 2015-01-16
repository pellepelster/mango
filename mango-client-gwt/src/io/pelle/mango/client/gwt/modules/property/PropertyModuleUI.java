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
package io.pelle.mango.client.gwt.modules.property;

import io.pelle.mango.client.base.property.IProperty;
import io.pelle.mango.client.gwt.GwtStyles;
import io.pelle.mango.client.gwt.modules.dictionary.BaseGwtModuleUI;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.property.PropertyModule;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class PropertyModuleUI extends BaseGwtModuleUI<PropertyModule> {

	final static Logger LOG = Logger.getLogger("PropertyModuleUI");

	private HTML title;

	private VerticalPanel panel = new VerticalPanel();

	/**
	 * @param module
	 */
	public PropertyModuleUI(PropertyModule module) {
		super(module, PropertyModule.UI_MODULE_ID);
		panel.setSpacing(GwtStyles.SPACING);

		title = new HTML(MangoClientWeb.MESSAGES.properties());
		title.addStyleName(GwtStyles.TITLE);
		panel.add(title);

		for (IProperty<?> property : getModule().getProperties()) {

			HorizontalPanel propertyPanel = new HorizontalPanel();
			propertyPanel.setSpacing(GwtStyles.SPACING);

			Label propertyLabel = new Label(property.getName());
			propertyPanel.add(propertyLabel);

			StringEditableLabel editableLabel = new StringEditableLabel(new ValueChangeHandler<String>() {
				@Override
				public void onValueChange(ValueChangeEvent<String> event) {
					GWT.log(event.getValue());
				}
			});
			
			editableLabel.setTextBoxStyle(GwtStyles.FORM_CONTROL);
			editableLabel.addButtonStyleName(GwtStyles.BUTTON);
			editableLabel.addButtonStyleName(GwtStyles.BUTTON_DEFAULT);
			editableLabel.setValue(property.getKey());
			propertyPanel.add(editableLabel);

			panel.add(propertyPanel);
		}

	}

	/** {@inheritDoc} */
	@Override
	public Panel getContainer() {
		return panel;
	}

	@Override
	public String getTitle() {
		return getModule().getTitle();
	}

}
