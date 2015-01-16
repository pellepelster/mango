package io.pelle.mango.client.gwt.modules.property;

import io.pelle.mango.client.web.MangoMessages;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
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
import com.google.gwt.i18n.client.LocalizableResource.Generate;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class BaseEditableLabel extends Composite implements HasValue<String>, HasValueChangeHandlers<String>, KeyUpHandler, BlurHandler {

	public static final String EDITABLE_LABEL_EDITABLE_STYLE = "editablelable-editable";

	public static final String EDITABLE_LABEL_READONLY_STYLE = "editablelable-readonly";

	public static final String EDITABLE_LABEL_STYLE = "editablelable";

	@Generate(format = "com.google.gwt.i18n.rebind.format.PropertiesFormat")
	public interface EditableLabelMessages extends Messages {

		@DefaultMessage("Ok")
		String buttonOk();

		@DefaultMessage("Cancel")
		String buttonCancel();

	}

	EditableLabelMessages MESSAGES = ((EditableLabelMessages) GWT.create(MangoMessages.class));

	private class ImageButton extends Button {

		private String text;

		public ImageButton() {
			super();
		}

		public ImageButton(ImageResource imageResource) {
			setResource(imageResource);
		}

		public ImageButton(String text) {
			setText(text);
		}

		/** {@inheritDoc} */
		@Override
		public String getText() {
			return this.text;
		}

		public void setResource(ImageResource imageResource) {
			Image img = new Image(imageResource);
			String definedStyles = img.getElement().getAttribute("style");
			img.getElement().setAttribute("style", definedStyles + "; margin-left:15px; margin-right:15px; vertical-align:middle;");
			DOM.insertBefore(getElement(), img.getElement(), DOM.getFirstChild(getElement()));
		}

		/** {@inheritDoc} */
		@Override
		public void setText(String text) {
			this.text = text;
			Element span = DOM.createElement("span");
			span.setInnerText(text);
			span.setAttribute("style", "padding-left:3px; vertical-align:middle;");

			DOM.insertChild(getElement(), span, 0);
		}
	}

	final int VIEW_MODE = 0;

	final int EDIT_MODE = 1;

	private DeckPanel decks = new DeckPanel();

	private HorizontalPanel editComposite = new HorizontalPanel();

	private Label editLabel;

	private TextBox text = new TextBox();;

	private String oldValue;

	private ImageButton okButton;

	private ImageButton cancelButton = new ImageButton();

	private boolean isReadOnly = false;

	protected Label createLabel() {
		Label label = new Label();
		label.addStyleName(EDITABLE_LABEL_STYLE);
		return label;
	}

	protected ImageButton createOkButton() {

		ImageButton button = new ImageButton(MESSAGES.buttonOk());

		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				endEdit();
			}
		});

		return button;
	}

	protected ImageButton createCancelButton() {

		ImageButton button = new ImageButton(MESSAGES.buttonCancel());

		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				cancelEdit();
			}
		});

		return button;
	}

	public BaseEditableLabel() {

		editLabel = createLabel();
		decks.add(editLabel);

		okButton = createOkButton();
		cancelButton = createCancelButton();
		editComposite.add(text);
		editComposite.add(okButton);
		editComposite.add(cancelButton);
		decks.add(editComposite);

		initWidget(decks);

		decks.showWidget(VIEW_MODE);
		text.setValue("");

		editLabel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				startEdit();
			}
		});

		updateLabelStyle();
	}

	private void updateLabelStyle() {
		if (isReadOnly) {
			editLabel.removeStyleName(EDITABLE_LABEL_READONLY_STYLE);
			editLabel.addStyleName(EDITABLE_LABEL_EDITABLE_STYLE);
		} else {
			editLabel.addStyleName(EDITABLE_LABEL_READONLY_STYLE);
			editLabel.removeStyleName(EDITABLE_LABEL_EDITABLE_STYLE);
		}
	}

	public void setText(String newText) {
		editLabel.setText(newText);
	}

	public void highlightWidget(MouseOverEvent event) {
		this.getElement().getStyle().setBackgroundColor("yellow");
	}

	public void unhighlightWidget(MouseOutEvent event) {
		this.getElement().getStyle().setBackgroundColor("");
	}

	public void startEdit() {
		oldValue = editLabel.getText();
		decks.showWidget(EDIT_MODE);
		text.setText(oldValue);
		text.addKeyUpHandler(this);
		text.addBlurHandler(this);
		text.setFocus(true);
	}

	public void cancelEdit() {
		decks.showWidget(VIEW_MODE);
	}

	public void endEdit() {
		editLabel.setText(text.getValue());
		decks.showWidget(VIEW_MODE);
		ValueChangeEvent.fire(this, text.getValue());
	}

	public String getValue() {
		return text.getValue();
	}

	public void setValue(String value) {
		editLabel.setText(value);
		text.setValue(value);
	}

	public void setValue(String value, boolean fireEvents) {
		editLabel.setText(value);
		text.setValue(value, fireEvents);
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