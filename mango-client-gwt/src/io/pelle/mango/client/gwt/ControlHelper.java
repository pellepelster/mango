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

import io.pelle.mango.client.base.modules.dictionary.controls.IBaseControl.IControlUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelUtil;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.base.util.CollectionUtils;
import io.pelle.mango.client.web.modules.dictionary.controls.BaseDictionaryControl;
import io.pelle.mango.client.web.modules.dictionary.controls.IGwtControl;
import io.pelle.mango.client.web.modules.dictionary.layout.WidthCalculationStrategy;

import java.util.Map;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class ControlHelper implements IControlUpdateListener {
	private BaseDictionaryControl<?, ?> baseControl;

	private final UIObject uiObject;

	private final IGwtControl gwtControl;

	private boolean isUpdating = false;

	public <ValueType> ControlHelper(final Widget uiObject, final BaseDictionaryControl<?, ValueType> baseControl, IGwtControl gwtControl, boolean addValueChangeListener) {
		this(uiObject, baseControl, gwtControl, addValueChangeListener, true);
	}

	public <ValueType> ControlHelper(final Widget uiObject, final BaseDictionaryControl<?, ValueType> baseControl, IGwtControl gwtControl, boolean addValueChangeListener, boolean addDefaultStyle) {
		this.uiObject = uiObject;
		this.baseControl = baseControl;
		this.gwtControl = gwtControl;

		if (addDefaultStyle) {
			uiObject.addStyleName(GwtStyles.FORM_CONTROL);
		}

		uiObject.setWidth(WidthCalculationStrategy.getInstance().getControlWidthCss(baseControl.getModel()));

		baseControl.addUpdateListener(this);

		if (uiObject instanceof HasValue<?>) {
			final HasValue<ValueType> hasValueWidget = (HasValue<ValueType>) uiObject;

			if (addValueChangeListener) {
				if (uiObject instanceof FocusWidget) {
					FocusWidget focusWidget = (FocusWidget) uiObject;

					focusWidget.addKeyUpHandler(new KeyUpHandler() {
						@Override
						public void onKeyUp(KeyUpEvent event) {
							setParseValue(hasValueWidget.getValue());
						}
					});
				}
			}
		}

		// uiObject.addDomHandler(new MouseOverHandler() {
		//
		// @Override
		// public void onMouseOver(MouseOverEvent event) {
		// baseControl.beginEdit();
		// }
		// }, MouseOverEvent.getType());
		//
		// uiObject.addDomHandler(new MouseOutHandler() {
		//
		// @Override
		// public void onMouseOut(MouseOutEvent event) {
		// baseControl.endEdit();
		// }
		// }, MouseOutEvent.getType());

		onUpdate();

	}

	private void setParseValue(Object value) {
		if (!isUpdating) {
			isUpdating = true;

			if (value != null) {
				baseControl.parseValue(value.toString());
			} else {
				baseControl.setValue(null);
			}

			isUpdating = false;
		}
	}

	@Override
	public void onUpdate() {
		if (!isUpdating) {
			isUpdating = true;
			gwtControl.setContent(baseControl.getValue());
			isUpdating = false;
		}

		if (baseControl.getValidationMessages().hasErrors()) {
			uiObject.addStyleName(GwtStyles.CONTROL_ERROR_STYLE);
			Map<String, Object> context = CollectionUtils.getMap(IBaseControlModel.EDITOR_LABEL_MESSAGE_KEY, DictionaryModelUtil.getEditorLabel(baseControl.getModel()));
			uiObject.setTitle(baseControl.getValidationMessages().getValidationMessageString(context));
		} else {
			uiObject.removeStyleName(GwtStyles.CONTROL_ERROR_STYLE);
			uiObject.setTitle("");
		}

	}
}
