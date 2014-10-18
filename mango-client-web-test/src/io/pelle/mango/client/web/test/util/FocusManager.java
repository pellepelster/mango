package io.pelle.mango.client.web.test.util;


public class FocusManager {

	private FocusableTestWidget currentWidget;

	public static FocusManager instance;

	private FocusManager() {
	}

	public static FocusManager getInstance() {

		if (instance == null) {
			instance = new FocusManager();
		}

		return instance;
	}

	public void setCurrentWidget(FocusableTestWidget nextWidget) {

		if (this.currentWidget != null && this.currentWidget != nextWidget) {
			this.currentWidget.onFocusLeave();
		}

		this.currentWidget = nextWidget;
		
		if (this.currentWidget != null) {
			nextWidget.onFocusEnter();
		}
		
	}

	public void leaveCurrentWidget() {
		setCurrentWidget(null);
	}

}
