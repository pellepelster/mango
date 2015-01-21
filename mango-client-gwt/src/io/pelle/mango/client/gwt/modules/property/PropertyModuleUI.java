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
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;
import io.pelle.mango.gwt.commons.BaseEditableLabel;
import io.pelle.mango.gwt.commons.BooleanEditableLabel;
import io.pelle.mango.gwt.commons.IntegerEditableLabel;
import io.pelle.mango.gwt.commons.StringEditableLabel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
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

	private Map<IProperty<Serializable>, BaseEditableLabel<Serializable, ?>> properties2Label = new HashMap<IProperty<Serializable>, BaseEditableLabel<Serializable, ?>>();

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

		for (final IProperty<? extends Serializable> property : category.getProperties()) {

			HorizontalPanel propertyPanel = new HorizontalPanel();
			propertyPanel.setSpacing(GwtStyles.SPACING * 2);

			Label propertyLabel = new Label(property.getName());
			propertyPanel.add(propertyLabel);

			BaseEditableLabel<? extends Serializable, ?> baseEditableLabel = null;
			switch (property.getValueType()) {
			case BOOLEAN:
				BooleanEditableLabel booleanEditableLabel = new BooleanEditableLabel();
				baseEditableLabel = booleanEditableLabel;
				break;
			case STRING:
				final StringEditableLabel stringEditableLabel = new StringEditableLabel();
				stringEditableLabel.addControlStyle(GwtStyles.FORM_CONTROL);
				stringEditableLabel.setValue(property.getKey());
				baseEditableLabel = stringEditableLabel;

				break;
			case INTEGER:
				IntegerEditableLabel integerEditableLabel = new IntegerEditableLabel();
				integerEditableLabel.addControlStyle(GwtStyles.FORM_CONTROL);
				// integerEditableLabel.setValue(property.getKey());
				baseEditableLabel = integerEditableLabel;
				break;

			default:
				throw new RuntimeException("unsupported property value type + '" + property.getValueType() + "'");
			}

			// setValuChangeCallback(baseEditableLabel, property);
			baseEditableLabel.addButtonStyle(GwtStyles.BUTTON);
			baseEditableLabel.addButtonStyle(GwtStyles.BUTTON_DEFAULT);
			baseEditableLabel.setErrorStyle(GwtStyles.FORM_CONTROL_ERROR);

			propertyPanel.add(baseEditableLabel);

			// properties2Label.put(property, baseEditableLabel);
			panel.add(propertyPanel);
		}

		MangoClientWeb.getInstance().getRemoteServiceLocator().getPropertyService().getPropertyValues(category, new AsyncCallback<Map>() {

			@Override
			public void onSuccess(Map result) {
				Map<IProperty<Serializable>, Serializable> values = result;

				for (Map.Entry<IProperty<Serializable>, Serializable> propertyValue : values.entrySet()) {
					if (properties2Label.containsKey(propertyValue.getKey())) {
						properties2Label.get(propertyValue.getKey()).setValue(propertyValue.getValue());
					}
				}
			}

			@Override
			public void onFailure(Throwable caught) {
			}
		});

	}

	private <VALUETYPE extends Serializable> void setValuChangeCallback(BaseEditableLabel<VALUETYPE, ?> baseEditableLabel, final IProperty<VALUETYPE> property) {

		baseEditableLabel.addValueChangeHandler(new ValueChangeHandler<VALUETYPE>() {
			@Override
			public void onValueChange(ValueChangeEvent<VALUETYPE> event) {

				MangoClientWeb.getInstance().getRemoteServiceLocator().getPropertyService().setProperty(property, event.getValue(), new BaseErrorAsyncCallback<Void>() {
					@Override
					public void onSuccess(Void result) {
					}
				});
			}
		});

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
