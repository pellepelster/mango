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

import io.pelle.mango.client.base.modules.dictionary.controls.IBaseControl;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.gwt.GwtStyles;
import io.pelle.mango.client.web.modules.dictionary.controls.BaseDictionaryControl;

import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesBuilder;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.client.SafeHtmlTemplates.Template;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.SafeHtmlRenderer;
import com.google.gwt.text.shared.SimpleSafeHtmlRenderer;

public class EditTextCellWithValidation<T> extends BaseCellControl<T> {

	interface Template extends SafeHtmlTemplates {
		@Template("<input style=\"{1}\" type=\"text\" value=\"{0}\" tabindex=\"-1\"></input>")
		SafeHtml input(String value, SafeStyles styles);

		@Template("<span style=\"{1}\">{0}</span>")
		SafeHtml display(String value, SafeStyles styles);
	}

	private static Template template;

	private final BaseDictionaryControl<IBaseControlModel, Object> baseControl;

	private final SafeHtmlRenderer<String> renderer;

	public EditTextCellWithValidation(BaseDictionaryControl<IBaseControlModel, Object> baseControl) {
		super(baseControl.getModel(), ClickEvent.getType().getName(), KeyUpEvent.getType().getName(), KeyDownEvent.getType().getName(), FocusEvent.getType().getName(), BlurEvent.getType().getName());

		if (template == null) {
			template = GWT.create(Template.class);
		}

		this.baseControl = baseControl;
		this.renderer = SimpleSafeHtmlRenderer.getInstance();
	}

	@Override
	public void onBrowserEvent(Context context, Element parent, T value, NativeEvent event, ValueUpdater<T> valueUpdater) {
		GWT.log("onBrowserEvent: value: " + value.toString());

		String type = event.getType();
		int keyCode = event.getKeyCode();

		boolean enterPressed = KeyUpEvent.getType().getName().equals(type) && keyCode == KeyCodes.KEY_ENTER;
		boolean startEdit = ClickEvent.getType().getName().equals(type) || enterPressed;
		boolean eventTargetIsInput = false;

		if (Element.is(event.getEventTarget())) {
			Element target = Element.as(event.getEventTarget());
			GWT.log("target: " + target.getTagName() + "(" + target.getId() + "), eventType: " + type);

			eventTargetIsInput = "input".equals(target.getTagName().toLowerCase());

		}

		if (BlurEvent.getType().getName().equals(type)) {
			if (eventTargetIsInput) {
				commit(context, parent, valueUpdater);
			}

		} else if (FocusEvent.getType().getName().equals(type)) {
			getInputElement(parent).focus();
		} else {
			if (isEditing(context, parent, value)) {
				editEvent(context, parent, value, event, valueUpdater);

			} else {
				if (startEdit) {
					startEdit(context, parent, value);
				}
			}
		}

	}

	@Override
	public void render(Context context, T value, SafeHtmlBuilder sb) {
		ViewData<T> viewData = getOrInitViewData(context);

		SafeStylesBuilder styles = new SafeStylesBuilder();

		if (getBaseControl(context).getValidationMessages().hasErrors()) {
			styles.appendTrustedString(GwtStyles.CELL_ERROR_STYLE);
		}

		if (viewData.isEditing()) {
			sb.append(template.input(getBaseControl(context).format(), styles.toSafeStyles()));
		} else {
			styles.appendTrustedString(GwtStyles.CELL_ERROR_DISPLAY_PADDING);
			sb.append(template.display(renderer.render(getBaseControl(context).format()).asString(), styles.toSafeStyles()));
		}
	}

	@Override
	public boolean resetFocus(Context context, Element parent, T value) {
		if (isEditing(context, parent, value)) {
			getInputElement(parent).focus();
			return true;
		}
		return false;
	}

	protected void startEdit(Context context, Element parent, T value) {
		ViewData<T> viewData = getOrInitViewData(context);
		viewData.setEditing(true);

		setValue(context, parent, value);

		focus(parent);
	}

	private void focus(Element parent) {
		InputElement input = getInputElement(parent);

		input.focus();
		input.select();
	}

	private void cancel(Context context, Element parent, T value) {
		clearInput(getInputElement(parent));
		setValue(context, parent, value);
	}

	private native void clearInput(Element input) /*-{
													if (input.selectionEnd)
													input.selectionEnd = input.selectionStart;
													else if ($doc.selection)
													$doc.selection.clear();
													}-*/;

	private void commit(Context context, Element parent, ValueUpdater<T> valueUpdater) {
		ViewData<T> viewData = getOrInitViewData(context);
		viewData.setEditing(false);

		IBaseControl<?, ?> baseControl = getBaseControl(context);
		baseControl.parseValue(getInputElement(parent).getValue());

		clearInput(getInputElement(parent));

		setValue(context, parent, getBaseControl(context).getValue());
	}

	private void editEvent(Context context, Element parent, T value, NativeEvent event, ValueUpdater<T> valueUpdater) {
		ViewData<T> viewData = getOrInitViewData(context);
		String type = event.getType();

		boolean keyUp = KeyUpEvent.getType().getName().equals(type);
		boolean keyDown = KeyDownEvent.getType().getName().equals(type);

		if (keyUp || keyDown) {
			int keyCode = event.getKeyCode();

			if (keyUp && keyCode == KeyCodes.KEY_ENTER) {
				commit(context, parent, valueUpdater);
			} else if (keyUp && keyCode == KeyCodes.KEY_ESCAPE) {
				if (viewData.isEditingAgain()) {
					viewData.setEditing(false);
				} else {
					clearViewData(context);
				}

				cancel(context, parent, value);
			} else {
				// Update the text in the view data on each key.
				updateViewData(context, parent, viewData, true);
			}
		}
	}

	private void updateViewData(Context context, Element parent, ViewData<T> viewData, boolean isEditing) {
		viewData.setEditing(isEditing);

	}
}
