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
import io.pelle.mango.gwt.commons.editableLabel.BaseEditableLabel;
import io.pelle.mango.gwt.commons.editableLabel.BooleanEditableLabel;
import io.pelle.mango.gwt.commons.editableLabel.IntegerEditableLabel;
import io.pelle.mango.gwt.commons.editableLabel.StringEditableLabel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.gwtbootstrap3.client.ui.constants.Styles;

import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PropertyModuleUI extends BaseGwtModuleUI<PropertyModule> {

	final static Logger LOG = Logger.getLogger("PropertyModuleUI");

	private HTML title;

	private VerticalPanel panel = new VerticalPanel();

	private Map<String, BaseEditableLabel> properties2Label = new HashMap<String, BaseEditableLabel>();

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
			FlowPanel categoryPanel = new FlowPanel();
			categoryPanel.getElement().getStyle().setMarginTop(1, Unit.EM);
			categories.add(categoryPanel, category.getName());
			createProperties(categoryPanel, category);
		}

		if (categories.getWidgetCount() > 0) {
			categories.selectTab(0);
			panel.add(categories);
		}
	}

	@SuppressWarnings("unchecked")
	private void createProperties(Panel panel, IPropertyCategory category) {

		for (final IProperty<? extends Serializable> property : category.getProperties()) {
			FlowPanel propertyPanel = createPropertyPanel(property);
			panel.add(propertyPanel);
		}

		MangoClientWeb.getInstance().getRemoteServiceLocator().getPropertyService().getPropertyValues(category, new AsyncCallback<Map>() {

			@Override
			public void onSuccess(Map result) {
				Map<IProperty<Serializable>, Serializable> values = result;

				for (Map.Entry<IProperty<Serializable>, Serializable> propertyValue : values.entrySet()) {
					if (properties2Label.containsKey(propertyValue.getKey().getKey())) {
						properties2Label.get(propertyValue.getKey().getKey()).setValue(propertyValue.getValue());
					}
				}
			}

			@Override
			public void onFailure(Throwable caught) {
			}
		});

	}

	private <V extends Serializable> FlowPanel createPropertyPanel(final IProperty<V> property) {
		FlowPanel propertyPanel = new FlowPanel();
		propertyPanel.getElement().getStyle().setProperty("display", "table");
		propertyPanel.getElement().getStyle().setMarginBottom(1.5d, Unit.EM);

		Label propertyLabel = new Label(property.getName());
		propertyPanel.add(propertyLabel);
		propertyLabel.getElement().getStyle().setFloat(Float.LEFT);
		propertyLabel.getElement().getStyle().setMarginRight(1.5d, Unit.EM);

		BaseEditableLabel<V, ?> baseEditableLabel = null;
		switch (property.getValueType()) {
		case BOOLEAN:
			BooleanEditableLabel booleanEditableLabel = new BooleanEditableLabel();
			baseEditableLabel = (BaseEditableLabel<V, ?>) booleanEditableLabel;
			break;
		case STRING:
			final StringEditableLabel stringEditableLabel = new StringEditableLabel();
			stringEditableLabel.addControlStyle(Styles.FORM_CONTROL);
			baseEditableLabel = (BaseEditableLabel<V, ?>) stringEditableLabel;

			break;
		case INTEGER:
			IntegerEditableLabel integerEditableLabel = new IntegerEditableLabel();
			integerEditableLabel.addControlStyle(Styles.FORM_CONTROL);
			baseEditableLabel = (BaseEditableLabel<V, ?>) integerEditableLabel;
			break;

		default:
			throw new RuntimeException("unsupported property value type + '" + property.getValueType() + "'");
		}

		baseEditableLabel.addValueChangeHandler(new ValueChangeHandler<V>() {
			@Override
			public void onValueChange(ValueChangeEvent<V> event) {

				MangoClientWeb.getInstance().getRemoteServiceLocator().getPropertyService().setProperty(property, event.getValue(), new BaseErrorAsyncCallback<Void>() {
					@Override
					public void onSuccess(Void result) {
					}
				});
			}
		});

		baseEditableLabel.addButtonStyle(Styles.BTN);
		baseEditableLabel.setErrorStyle(GwtStyles.FORM_CONTROL_ERROR);
		baseEditableLabel.getElement().getStyle().setFloat(Float.LEFT);

		properties2Label.put(property.getKey(), baseEditableLabel);

		propertyPanel.add(baseEditableLabel);
		return propertyPanel;
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
