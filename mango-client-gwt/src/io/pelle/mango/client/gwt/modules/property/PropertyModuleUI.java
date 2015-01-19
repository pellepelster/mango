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
import io.pelle.mango.client.base.property.IPropertyCategory;
import io.pelle.mango.client.gwt.GwtStyles;
import io.pelle.mango.client.gwt.modules.dictionary.BaseGwtModuleUI;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.property.PropertyModule;
import io.pelle.mango.gwt.commons.BaseEditableLabel;
import io.pelle.mango.gwt.commons.BooleanEditableLabel;
import io.pelle.mango.gwt.commons.IntegerEditableLabel;
import io.pelle.mango.gwt.commons.StringEditableLabel;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PropertyModuleUI extends BaseGwtModuleUI<PropertyModule> {

	final static Logger LOG = Logger.getLogger("PropertyModuleUI");

	private HTML title;

	private VerticalPanel panel = new VerticalPanel();

	public PropertyModuleUI(PropertyModule module) {
		super(module, PropertyModule.UI_MODULE_ID);
		panel.setSpacing(GwtStyles.SPACING);
		panel.setWidth("100%");

		title = new HTML(MangoClientWeb.MESSAGES.properties());
		title.addStyleName(GwtStyles.TITLE);
		panel.add(title);

		TabPanel categories = new TabPanel();
		categories.setWidth("70%");

		for (IPropertyCategory category : getModule().getRootCategory().getCategories()) {
			VerticalPanel categoryPanel = new VerticalPanel();
			categories.add(categoryPanel, category.getName());
			createProperties(categoryPanel, category);
		}

		if (categories.getWidgetCount() > 0) {
			categories.selectTab(0);
			panel.add(categories);
		}
	}

	private void createProperties(Panel panel, IPropertyCategory category) {

		for (IProperty<?> property : category.getProperties()) {

			HorizontalPanel propertyPanel = new HorizontalPanel();
			propertyPanel.setSpacing(GwtStyles.SPACING * 2);

			Label propertyLabel = new Label(property.getName());
			propertyPanel.add(propertyLabel);

			BaseEditableLabel<?, ?> baseEditableLabel = null;
			switch (property.getValueType()) {
			case BOOLEAN:
				BooleanEditableLabel booleanEditableLabel = new BooleanEditableLabel();
				baseEditableLabel = booleanEditableLabel;
				break;
			case STRING:
				StringEditableLabel stringEditableLabel = new StringEditableLabel();
				stringEditableLabel.setControlStyle(GwtStyles.FORM_CONTROL);
				stringEditableLabel.setValue(property.getKey());
				baseEditableLabel = stringEditableLabel;
				break;
			case INTEGER:
				IntegerEditableLabel integerEditableLabel = new IntegerEditableLabel();
				integerEditableLabel.setControlStyle(GwtStyles.FORM_CONTROL);
				//integerEditableLabel.setValue(property.getKey());
				baseEditableLabel = integerEditableLabel;
				break;

			default:
				throw new RuntimeException("unsupported property value type + '" + property.getValueType() + "'");
			}
			
			baseEditableLabel.addButtonStyleName(GwtStyles.BUTTON);
			baseEditableLabel.addButtonStyleName(GwtStyles.BUTTON_DEFAULT);
			baseEditableLabel.setErrorStyle(GwtStyles.FORM_CONTROL_ERROR);

			baseEditableLabel.addValueChangeHandler(new ValueChangeHandler() {
				@Override
				public void onValueChange(ValueChangeEvent event) {
					
					if (event.getValue() == null) {
						GWT.log("null");
					} else {
						GWT.log(event.getValue().toString());
					}
					
				}
			});
			
			propertyPanel.add(baseEditableLabel);

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
