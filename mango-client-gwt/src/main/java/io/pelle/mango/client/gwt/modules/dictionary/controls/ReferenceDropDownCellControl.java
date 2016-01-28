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

import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.client.SafeHtmlTemplates.Template;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.SafeHtmlRenderer;
import com.google.gwt.text.shared.SimpleSafeHtmlRenderer;
import com.google.gwt.user.client.ui.ListBox;

import io.pelle.mango.client.base.modules.dictionary.model.controls.IReferenceControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;

public class ReferenceDropDownCellControl<T extends IBaseVO> extends BaseCellControl<T> {

	public class ListBoxInternal extends ListBox {
		public ListBoxInternal(Element element) {
			super(element);
		}
	}

	interface Template extends SafeHtmlTemplates {
		@Template("<select tabindex=\"-1\">")
		SafeHtml inputStart();

		@Template("</select>")
		SafeHtml inputEnd();

		@Template("<option value=\"{0}\" selected=\"selected\">{0}</option>")
		SafeHtml selected(String option);

	}

	private static Template template;

	private final SafeHtmlRenderer<String> renderer;

	private ListBox listBox;

	private IReferenceControlModel referenceControlModel;

	public ReferenceDropDownCellControl(IReferenceControlModel referenceControlModel, SafeHtmlRenderer<String> renderer) {
		super(referenceControlModel, ClickEvent.getType().getName(), KeyUpEvent.getType().getName(), KeyDownEvent.getType().getName(), BlurEvent.getType().getName());

		this.referenceControlModel = referenceControlModel;
		this.renderer = renderer;

		if (template == null) {
			template = GWT.create(Template.class);
		}

		if (renderer == null) {
			throw new IllegalArgumentException("renderer == null");
		}
	}

	public ReferenceDropDownCellControl(IReferenceControlModel referenceControlModel) {
		this(referenceControlModel, SimpleSafeHtmlRenderer.getInstance());
	}

	private void cancel(Context context, Element parent) {
		ViewData<T> viewData = getOrInitViewData(context);
		viewData.setEditing(false);

		clearListBox();
		setValue(context, parent, getBaseControl(context).getValue());
	}

	// private native void clearInput(Element input) /*-{ if
	// (input.selectionEnd) input.selectionEnd = input.selectionStart;
	// else if ($doc.selection) $doc.selection.clear();
	// }-*/;

	private void clearListBox() {
		if (listBox == null) {
			throw new RuntimeException("no listbox instance");
		}

		listBox.removeFromParent();
		listBox = null;
	}

	private void commit(Context context, Element parent) {
		ViewData<T> viewData = getOrInitViewData(context);
		viewData.setEditing(false);

		clearListBox();

		setValue(context, parent, getBaseControl(context).getValue());
	}

	protected void createListBox(final Context context, final Element parent, T value, final ValueUpdater<T> valueUpdater) {
		if (listBox != null) {
			throw new RuntimeException("listbox already active");
		}

		InputElement input = getInputElement(parent);
		listBox = new ListBoxInternal(input);
		ControlUtil.populateListBox(referenceControlModel, listBox);

		// listBox.addSelectionHandler(new
		// SelectionHandler<SuggestOracle.Suggestion>()
		// {
		// @Override
		// public void onSelection(SelectionEvent<Suggestion> event)
		// {
		// if (event.getSelectedItem() instanceof SuggestCellSuggestion)
		// {
		// SuggestCellSuggestion<T> suggestCellSuggestions =
		// (SuggestCellSuggestion<T>) event.getSelectedItem();
		// commit(suggestCellSuggestions.getValue(), context, parent,
		// valueUpdater);
		// }
		// }
		// });
		parent.replaceChild(listBox.getElement(), input);

		// setFocus(parent);

	}

	private void editEvent(Context context, Element parent, T value, ViewData<T> viewData, NativeEvent event, ValueUpdater<T> valueUpdater) {
		String type = event.getType();
		boolean keyUp = KeyUpEvent.getType().getName().equals(type);
		boolean keyDown = KeyDownEvent.getType().getName().equals(type);
		int keyCode = event.getKeyCode();

		if (keyUp || keyDown) {
			if (keyUp && keyCode == KeyCodes.KEY_ESCAPE) {
				if (viewData.isEditingAgain()) {
					viewData.setEditing(false);
				}

				cancel(context, parent);
			} else if (keyCode == KeyCodes.KEY_ENTER) {
				commit(context, parent);
			} else {
				fireEventToSuggesBox(event);
			}
		} else if (BlurEvent.getType().getName().equals(type)) {
			EventTarget eventTarget = event.getEventTarget();
			if (Element.is(eventTarget)) {
				Element target = Element.as(eventTarget);
				if ("select".equals(target.getTagName().toLowerCase())) {
					commit(context, parent);
				}
			}
		}

	}

	private void fireEventToSuggesBox(NativeEvent event) {
		if (listBox != null) {
			DomEvent.fireNativeEvent(event, listBox);
		}
	}

	private boolean hasListBox() {
		return listBox != null;
	}

	@Override
	public void onBrowserEvent(final Context context, final Element parent, final T value, final NativeEvent event, final ValueUpdater<T> valueUpdater) {
		final ViewData<T> viewData = getOrInitViewData(context);

		if (viewData.isEditing()) {
			if (!hasListBox()) {
				createListBox(context, parent, value, valueUpdater);
			}

			editEvent(context, parent, value, viewData, event, valueUpdater);

		} else {
			if (ClickEvent.getType().getName().equals(event.getType()) || enterPressed(event)) {
				viewData.setEditing(true);
				setValue(context, parent, value);
				createListBox(context, parent, value, valueUpdater);
			}
		}
	}

	@Override
	public void render(Context context, T value, SafeHtmlBuilder sb) {
		ViewData<T> viewData = getOrInitViewData(context);

		if (hasListBox()) {
			clearListBox();
		}

		if (viewData.isEditing()) {
			sb.append(template.inputStart());
			sb.append(template.selected(format(context)));
			sb.append(template.inputEnd());
		} else {
			sb.append(renderer.render(format(context)));
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

	// private native void selectAll(Element input) /*-{ input.select();
	// }-*/;
}