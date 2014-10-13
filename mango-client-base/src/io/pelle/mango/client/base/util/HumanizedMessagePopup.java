package io.pelle.mango.client.base.util;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;

public class HumanizedMessagePopup extends PopupPanel {

	public enum HIDE_BEHAVIOR {
		ON_CLICK, ON_CLICK_FADE, SHOW_AND_FADE, FADE_AFTER_MOUSE_MOVE
	}

	public enum MESSAGE_TYPE {
		INFO, WARNING, ERROR
	}

	private static final String BASE_CSS_CLASS = "humanizedMessage";

	private static final String ERROR_CSS_CLASS = "humanizedMessageError";

	private static final String WARNING_CSS_CLASS = "humanizedMessageWarning";

	private static final String INFO_CSS_CLASS = "humanizedMessageInfo";

	private static final float DELTA = 0.05f;

	private static final int HIDE_TIMER_DELAY = 50;

	private static final int SHOW_MESSAGE_DELAY = 1000;

	private static final float INITAL_OPACITY = 0.8f;

	private final HIDE_BEHAVIOR hideBehavior;

	private HandlerRegistration handlerRegistration;

	public HumanizedMessagePopup(String message, MESSAGE_TYPE messageType, HIDE_BEHAVIOR hideBehavior) {
		this.hideBehavior = hideBehavior;

		boolean autoHide = false;
		boolean animate = false;

		switch (hideBehavior) {
		case ON_CLICK:
			autoHide = true;
			break;
		case SHOW_AND_FADE:
			animate = false;
			break;

		default:
			break;
		}

		setAutoHideEnabled(autoHide);
		setAnimationEnabled(animate);
		setOpacity(INITAL_OPACITY);

		HTML html = new HTML(message);
		setStylePrimaryName(BASE_CSS_CLASS);

		switch (messageType) {
		case ERROR:
			html.addStyleName(ERROR_CSS_CLASS);
			addStyleName(ERROR_CSS_CLASS);
			break;
		case WARNING:
			html.addStyleName(WARNING_CSS_CLASS);
			addStyleName(WARNING_CSS_CLASS);
			break;

		default:
			html.addStyleName(INFO_CSS_CLASS);
			addStyleName(INFO_CSS_CLASS);
			break;
		}

		setWidget(html);
	}

	private Timer getHideTimer() {
		if (handlerRegistration != null) {
			handlerRegistration.removeHandler();
			handlerRegistration = null;
		}

		final Timer hideTimer = new Timer() {
			@Override
			public void run() {

				float opacity = getOpacity();
				opacity -= DELTA;

				if (opacity > DELTA) {
					setOpacity(opacity);
				} else {
					hide();
					cancel();
				}
			}
		};

		return hideTimer;
	}

	@Override
	public void show() {
		super.show();

		Timer waitTimer = null;

		switch (hideBehavior) {
		case SHOW_AND_FADE:
			waitTimer = new Timer() {
				@Override
				public void run() {
					getHideTimer().scheduleRepeating(HIDE_TIMER_DELAY);
				}
			};
			break;
		case ON_CLICK_FADE:
			waitTimer = new Timer() {
				@Override
				public void run() {
					addNativePreviewHandler(Event.ONCLICK);
				}
			};
			break;
		case FADE_AFTER_MOUSE_MOVE:
			waitTimer = new Timer() {
				@Override
				public void run() {
					addNativePreviewHandler(Event.ONMOUSEMOVE);
				}
			};
			break;
		default:
			break;
		}

		if (waitTimer != null) {
			waitTimer.schedule(SHOW_MESSAGE_DELAY);
		}
	}

	private void addNativePreviewHandler(final int eventType) {
		if (handlerRegistration == null) {
			handlerRegistration = Event.addNativePreviewHandler(new NativePreviewHandler() {
				@Override
				public void onPreviewNativeEvent(NativePreviewEvent event) {
					int type = event.getTypeInt();

					if (type == eventType)
						getHideTimer().scheduleRepeating(HIDE_TIMER_DELAY);
				}
			});
		}
	}

	private void setOpacity(double opacity) {
		if (opacity < 0) {
			opacity = 0;
		}

		getElement().getStyle().setOpacity(opacity);
	}

	private float getOpacity() {
		String opacityString = getElement().getStyle().getOpacity();

		try {
			float opacity = Float.parseFloat(opacityString);
			return opacity;
		} catch (NumberFormatException nfe) {
			throw new RuntimeException("could not parse opacity, expected float but got '" + opacityString + "'");
		}

	}

	/**
	 * Show a message for {@link SHOW_MESSAGE_DELAY} milliseconds and hide it
	 * when the user clicks outside of it
	 *
	 * @param message
	 *            the message
	 * @param messageType
	 *            the message type
	 */
	public static void showMessageUntilClick(String message, MESSAGE_TYPE messageType) {
		HumanizedMessagePopup humanizedMessagePopup = new HumanizedMessagePopup(message, messageType, HIDE_BEHAVIOR.ON_CLICK);
		humanizedMessagePopup.center();
	}

	/**
	 * Show a message for {@link SHOW_MESSAGE_DELAY} milliseconds and fade it
	 * out when the user clicks outside of it
	 *
	 * @param message
	 *            the message
	 * @param messageType
	 *            the message type
	 */
	public static void showMessageAndFadeAfterClick(String message, MESSAGE_TYPE messageType) {
		HumanizedMessagePopup humanizedMessagePopup = new HumanizedMessagePopup(message, messageType, HIDE_BEHAVIOR.ON_CLICK_FADE);
		humanizedMessagePopup.center();
	}

	/**
	 * Show a message for {@link SHOW_MESSAGE_DELAY} milliseconds and fade it
	 * after first mouse move
	 *
	 * @param message
	 *            the message
	 * @param messageType
	 *            the message type
	 */
	public static void showMessageAndFadeAfterMouseMove(String message, MESSAGE_TYPE messageType) {
		HumanizedMessagePopup humanizedMessagePopup = new HumanizedMessagePopup(message, messageType, HIDE_BEHAVIOR.FADE_AFTER_MOUSE_MOVE);
		humanizedMessagePopup.center();
	}

	/**
	 * Show a message for {@link SHOW_MESSAGE_DELAY} milliseconds and fade it
	 * out afterwards
	 *
	 * @param message
	 *            the message
	 * @param messageType
	 *            the message type
	 */
	public static void showMessageAndFade(String message, MESSAGE_TYPE messageType) {
		HumanizedMessagePopup humanizedMessagePopup = new HumanizedMessagePopup(message, messageType, HIDE_BEHAVIOR.SHOW_AND_FADE);
		humanizedMessagePopup.center();
	}

}