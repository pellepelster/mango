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

import io.pelle.mango.client.base.modules.dictionary.model.controls.IReferenceControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;

import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
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
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.client.SafeHtmlTemplates.Template;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.SafeHtmlRenderer;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;

@SuppressWarnings("unused")
public class SuggestCellControl<T extends IBaseVO> extends BaseCellControl<T> {

	private class SuggestCellSuggestBox extends SuggestBox {
		private final int absoluteLeft;
		private final int absoluteTop;
		private final int offsetHeight;
		private final int offsetWidth;

		public SuggestCellSuggestBox(SuggestOracle oracle, TextBoxBase box) {
			super(oracle, box);
			this.absoluteLeft = box.getAbsoluteLeft();
			this.absoluteTop = box.getAbsoluteTop();
			this.offsetWidth = box.getOffsetWidth();
			this.offsetHeight = box.getOffsetHeight();
		}

		@Override
		public int getAbsoluteLeft() {
			if (super.getAbsoluteLeft() == 0) {
				return absoluteLeft;
			} else {
				return super.getAbsoluteLeft();
			}
		}

		@Override
		public int getAbsoluteTop() {
			if (super.getAbsoluteTop() == 0) {
				return absoluteTop;
			} else {
				return super.getAbsoluteTop();
			}
		}

		@Override
		public int getOffsetHeight() {
			if (super.getOffsetHeight() == 0) {
				return offsetHeight;
			} else {
				return super.getOffsetHeight();
			}
		}

		@Override
		public int getOffsetWidth() {
			if (super.getOffsetWidth() == 0) {
				return offsetWidth;
			} else {
				return super.getOffsetWidth();
			}
		}

		@Override
		public void onAttach() {
			super.onAttach();
		}
	}

	public interface SuggestCellSuggestion<T> extends Suggestion {
		public T getValue();
	}

	public class SuggestTextBox extends TextBox {
		public SuggestTextBox(Element element) {
			super(element);
		}
	}

	interface Template extends SafeHtmlTemplates {
		@Template("<input type=\"text\" value=\"{0}\" tabindex=\"-1\"></input>")
		SafeHtml input(String value);
	}

	private static Template template;

	private final SafeHtmlRenderer<String> renderer;

	private SuggestBox suggestBox;

	private final SuggestOracle suggestOracle;

	private IReferenceControlModel referenceControlModel;

	public SuggestCellControl(IReferenceControlModel referenceControlModel, SafeHtmlRenderer<String> renderer, SuggestOracle suggestOracle) {
		super(referenceControlModel, ClickEvent.getType().getName(), KeyUpEvent.getType().getName(), KeyDownEvent.getType().getName(), BlurEvent.getType().getName());

		this.referenceControlModel = referenceControlModel;
		this.suggestOracle = suggestOracle;
		this.renderer = renderer;

		if (template == null) {
			template = GWT.create(Template.class);
		}

		if (renderer == null) {
			throw new IllegalArgumentException("renderer == null");
		}
	}

	public SuggestCellControl(IReferenceControlModel referenceControlModel, SuggestOracle suggestOracle) {
		this(referenceControlModel, ControlHtmlRenderer.getInstance(), suggestOracle);
	}

	private void cancel(Context context, Element parent) {
		ViewData<T> viewData = getOrInitViewData(context);
		viewData.setEditing(false);

		clearInput(getInputElement(parent));
		clearSuggestBox();

		setValue(context, parent, getBaseControl(context).getValue());
	}

	private native void clearInput(Element input) /*-{ if (input.selectionEnd) input.selectionEnd = input.selectionStart; 
													else if ($doc.selection) $doc.selection.clear(); 
													}-*/;

	private void clearSuggestBox() {
		if (suggestBox == null) {
			throw new RuntimeException("no suggestbox instance");
		}
		suggestBox.hideSuggestionList();
		suggestBox.removeFromParent();
		suggestBox = null;
	}

	private void commit(Context context, Element parent) {
		ViewData<T> viewData = getOrInitViewData(context);
		viewData.setEditing(false);

		clearSuggestBox();

		setValue(context, parent, getBaseControl(context).getValue());
	}

	protected void createSuggestBox(final Context context, final Element parent, T value, final ValueUpdater<T> valueUpdater) {
		if (suggestBox != null) {
			throw new RuntimeException("SuggestCell already active");
		}

		GWT.log("createSuggestBox");

		InputElement input = getInputElement(parent);
		final TextBox textBox = new SuggestTextBox(input);
		suggestBox = new SuggestCellSuggestBox(suggestOracle, textBox);
		suggestBox.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
			@Override
			public void onSelection(SelectionEvent<Suggestion> event) {
				if (event.getSelectedItem() instanceof SuggestCellSuggestion) {
					SuggestCellSuggestion<T> suggestCellSuggestions = (SuggestCellSuggestion<T>) event.getSelectedItem();
					getBaseControl(context).setValue(suggestCellSuggestions.getValue());
					commit(context, parent);
				}
			}
		});

		parent.replaceChild(suggestBox.getElement(), input);

		setFocus(parent);

	}

	private void setFocus(final Element parent) {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				InputElement input = getInputElement(parent);
				input.focus();
				input.select();
			}
		});
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
				if (!suggestBox.isSuggestionListShowing()) {
					if (suggestBox.getText() == null || suggestBox.getText().isEmpty()) {
						getBaseControl(context).setValue(null);
					}

					commit(context, parent);
				}
			} else {
				fireEventToSuggesBox(event);
			}
		} else if (BlurEvent.getType().getName().equals(type)) {
			EventTarget eventTarget = event.getEventTarget();
			if (Element.is(eventTarget)) {
				Element target = Element.as(eventTarget);
				if ("input".equals(target.getTagName().toLowerCase()) && !suggestBox.isSuggestionListShowing()) {

					commit(context, parent);
				}
			}
		}

	}

	private void fireEventToSuggesBox(NativeEvent event) {
		if (suggestBox != null) {
			DomEvent.fireNativeEvent(event, suggestBox.getTextBox());
		}
	}

	private boolean hasSuggestBox() {
		return suggestBox != null;
	}

	@Override
	public void onBrowserEvent(final Context context, final Element parent, final T value, final NativeEvent event, final ValueUpdater<T> valueUpdater) {
		final ViewData<T> viewData = getOrInitViewData(context);

		if (viewData.isEditing()) {
			if (!hasSuggestBox()) {
				createSuggestBox(context, parent, value, valueUpdater);
			}

			editEvent(context, parent, value, viewData, event, valueUpdater);

		} else {
			if (ClickEvent.getType().getName().equals(event.getType()) || enterPressed(event)) {
				viewData.setEditing(true);
				setValue(context, parent, value);
				createSuggestBox(context, parent, value, valueUpdater);
			}
		}
	}

	@Override
	public void render(Context context, T value, SafeHtmlBuilder sb) {
		GWT.log("render");

		ViewData<T> viewData = getOrInitViewData(context);

		if (hasSuggestBox()) {
			clearSuggestBox();
		}

		if (viewData.isEditing()) {
			sb.append(template.input(getBaseControl(context).format()));
		} else {
			sb.append(renderer.render(getBaseControl(context).format()));
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

	private native void selectAll(Element input) /*-{ input.select(); 
													}-*/;
}