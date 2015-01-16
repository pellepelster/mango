package io.pelle.mango.client.gwt.modules.property;

import com.google.common.base.Objects;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.LocalizableResource.Generate;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public abstract class BaseEditableLabel<VALUETYPE> extends Composite implements HasValue<VALUETYPE>, HasValueChangeHandlers<VALUETYPE>, KeyUpHandler,
		BlurHandler {

	public static final String EDITABLE_LABEL_EDITABLE_STYLE = "editablelabel-editable";

	public static final String EDITABLE_LABEL_READONLY_STYLE = "editablelabel-readonly";

	public static final String EDITABLE_LABEL_STYLE = "editablelabel";

	@Generate(format = "com.google.gwt.i18n.rebind.format.PropertiesFormat")
	public interface EditableLabelMessages extends Messages {

		@DefaultMessage("Ok")
		String buttonOk();

		@DefaultMessage("Cancel")
		String buttonCancel();

		@DefaultMessage("<none>")
		String emptyValue();

	}

	private EditableLabelMessages MESSAGES = ((EditableLabelMessages) GWT.create(EditableLabelMessages.class));

	final int VIEW_MODE = 0;

	final int EDIT_MODE = 1;

	private DeckPanel decks = new DeckPanel();

	private HorizontalPanel editPanel;

	private Label editLabel;

	private TextBox textBox;

	private VALUETYPE value;

	private Button okButton;

	private Button cancelButton;

	private boolean isReadOnly = false;

	private Timer blurTimer = new Timer() {
		
		@Override
		public void run() {
			cancelEdit();
		}
	};

	private FocusHandler BUTTON_FOCUS_HANDLER = new FocusHandler() {
		
		@Override
		public void onFocus(FocusEvent event) {
			if (blurTimer.isRunning()) {
				blurTimer.cancel();
			}
		}
	};

	public BaseEditableLabel(ValueChangeHandler<VALUETYPE> handler) {

		editLabel = createLabel();
		decks.add(editLabel);

		editPanel = new HorizontalPanel();
		editPanel.setSpacing(10);

		textBox = createTextBox();
		editPanel.add(textBox);

		okButton = createOkButton();
		editPanel.add(okButton);

		cancelButton = createCancelButton();
		editPanel.add(cancelButton);
		decks.add(editPanel);

		initWidget(decks);

		decks.showWidget(VIEW_MODE);
		textBox.setValue("");

		editLabel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				startEdit();
			}
		});

		addValueChangeHandler(handler);
		updateLabelStyle();
	}

	public void setTextBoxStyle(String styleName) {
		textBox.addStyleName(styleName);
	}

	private void updateLabelStyle() {
		if (isReadOnly) {
			editLabel.addStyleName(EDITABLE_LABEL_READONLY_STYLE);
			editLabel.removeStyleName(EDITABLE_LABEL_EDITABLE_STYLE);
		} else {
			editLabel.removeStyleName(EDITABLE_LABEL_READONLY_STYLE);
			editLabel.addStyleName(EDITABLE_LABEL_EDITABLE_STYLE);
		}
	}

	private void startEdit() {
		decks.showWidget(EDIT_MODE);
		textBox.setText(valueToString(value));
		textBox.addKeyUpHandler(this);
		textBox.addBlurHandler(this);
		textBox.setFocus(true);
	}

	private void cancelEdit() {
		decks.showWidget(VIEW_MODE);
	}

	private void endEdit() {
		value = parseValue(textBox.getValue());
		decks.showWidget(VIEW_MODE);
		setValue(value);
	}

	protected abstract VALUETYPE parseValue(String valueString);

	protected abstract String valueToString(VALUETYPE value);

	public VALUETYPE getValue() {
		return parseValue(textBox.getValue());
	}

	@Override
	public void setValue(VALUETYPE value, boolean fireEvents) {
		this.value = value;
		editLabel.setText(Objects.firstNonNull(valueToString(value), MESSAGES.emptyValue()));
		textBox.setValue(valueToString(value));

		if (fireEvents) {
			ValueChangeEvent.fire(this, value);
		}
	}

	@Override
	public void setValue(VALUETYPE value) {
		setValue(value, true);
	}

	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<VALUETYPE> handler) {
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
		if (blurTimer.isRunning()) {
			blurTimer.cancel();
		}

		blurTimer.schedule(200);
	}

	public void addButtonStyleName(String styleName) {
		okButton.addStyleName(styleName);
		cancelButton.addStyleName(styleName);
	}

	protected Label createLabel() {
		Label label = new Label();
		label.addStyleName(EDITABLE_LABEL_STYLE);
		return label;
	}

	protected Button createOkButton() {

		Button button = new Button(MESSAGES.buttonOk());

		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				endEdit();
			}
		});

		button.addFocusHandler(BUTTON_FOCUS_HANDLER);

		return button;
	}

	protected Button createCancelButton() {

		Button button = new Button(MESSAGES.buttonCancel());

		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				cancelEdit();
			}
		});

		button.addFocusHandler(BUTTON_FOCUS_HANDLER);
		return button;
	}

	protected TextBox createTextBox() {
		return new TextBox();
	}

}