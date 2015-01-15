package io.pelle.mango.client.gwt.modules.property;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class EditableLabel extends Composite implements HasValue<String>, HasValueChangeHandlers<String>, KeyUpHandler, BlurHandler {

	final int VIEW_MODE = 0;

	final int EDIT_MODE = 1;

	private DeckPanel decks = new DeckPanel();

	private Label label = new Label();

	private TextBox edit = new TextBox();;

	private String oldValue;

	public EditableLabel() {

		decks.add(label);
		decks.add(edit);

		initWidget(decks);

		decks.showWidget(VIEW_MODE);
		edit.setValue("");

		label.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				startEdit();
			}
		});

	}

	public void setText(String newText) {
		label.setText(newText);
	}

	public void highlightWidget(MouseOverEvent event) {
		this.getElement().getStyle().setBackgroundColor("yellow");
	}

	public void unhighlightWidget(MouseOutEvent event) {
		this.getElement().getStyle().setBackgroundColor("");
	}

	public void startEdit() {
		oldValue = label.getText();
		decks.showWidget(EDIT_MODE);
		edit.setText(oldValue);
		edit.addKeyUpHandler(this);
		edit.addBlurHandler(this);
		edit.setFocus(true);
	}

	public void cancelEdit() {
		decks.showWidget(VIEW_MODE);
	}

	public void endEdit() {
		label.setText(edit.getValue());
		decks.showWidget(VIEW_MODE);
		ValueChangeEvent.fire(this, edit.getValue());
	}

	public String getValue() {
		return edit.getValue();
	}

	public void setValue(String value) {
		label.setText(value);
		edit.setValue(value);
	}

	public void setValue(String value, boolean fireEvents) {
		label.setText(value);
		edit.setValue(value, fireEvents);
	}

	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		return addHandler(handler, ValueChangeEvent.getType());
	}

	@Override
	public void onKeyUp(KeyUpEvent event) {
		if (event.getNativeKeyCode() == KeyCodes.KEY_ESCAPE) {
			cancelEdit();
		}

		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			endEdit();
		}
	}

	@Override
	public void onBlur(BlurEvent event) {
		endEdit();
	}

}