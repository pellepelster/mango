package io.pelle.mango.gwt.commons;

import com.google.common.base.Objects;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
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
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;

public abstract class BaseEditableLabel<VALUETYPE, CONTROLTYPE extends FocusWidget> extends Composite implements HasValue<VALUETYPE>, HasValueChangeHandlers<VALUETYPE>, KeyUpHandler, BlurHandler {

	public static final String EDITABLE_LABEL_EDITABLE_STYLE = "editablelabel-editable";

	public static final String EDITABLE_LABEL_READONLY_STYLE = "editablelabel-readonly";

	public static final String EDITABLE_LABEL_STYLE = "editablelabel";

	public static final String EDITABLE_LABEL_EDIT_PANEL_STYLE = "editablelabel-editpanel";

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

	private FlowPanel editPanel;

	private Label editLabel;

	private CONTROLTYPE control;

	private VALUETYPE value;

	private Button okButton;

	private Button cancelButton;

	private boolean isReadOnly = false;

	private static final int MARGIN = 5;

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

		editPanel = new FlowPanel();
		editPanel.getElement().getStyle().setPosition(Position.ABSOLUTE);

		control = createControl();
		control.getElement().getStyle().setFloat(Float.LEFT);
		editPanel.add(control);

		okButton = createOkButton();
		okButton.getElement().getStyle().setFloat(Float.LEFT);
		okButton.getElement().getStyle().setMarginLeft(MARGIN * 2, Unit.PX);
		editPanel.add(okButton);

		cancelButton = createCancelButton();
		cancelButton.getElement().getStyle().setFloat(Float.LEFT);
		cancelButton.getElement().getStyle().setMarginLeft(MARGIN, Unit.PX);
		editPanel.add(cancelButton);
		decks.add(editPanel);

		initWidget(decks);

		decks.showWidget(VIEW_MODE);
		decks.addStyleName(EDITABLE_LABEL_STYLE);

		editLabel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				startEdit();
			}
		});

		addValueChangeHandler(handler);
		updateLabelStyle();
	}

	public void setControlStyle(String styleName) {
		control.addStyleName(styleName);
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
		setValueToControl(value);
		control.addKeyUpHandler(this);
		control.addBlurHandler(this);
		control.setFocus(true);
	}

	private void cancelEdit() {
		decks.showWidget(VIEW_MODE);
	}

	@Override
	public VALUETYPE getValue() {
		return value;
	}

	private void endEdit() {
		value = getValueFromControl();
		decks.showWidget(VIEW_MODE);
		setValue(value);
	}

	protected abstract String formatValue(VALUETYPE value);

	protected abstract CONTROLTYPE createControl();

	protected abstract VALUETYPE getValueFromControl();

	protected abstract void setValueToControl(VALUETYPE value);

	@Override
	public void setValue(VALUETYPE value, boolean fireEvents) {
		this.value = value;
		editLabel.setText(Objects.firstNonNull(formatValue(value), MESSAGES.emptyValue()));
		setValueToControl(value);

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

	public CONTROLTYPE getControl() {
		return control;
	}

}