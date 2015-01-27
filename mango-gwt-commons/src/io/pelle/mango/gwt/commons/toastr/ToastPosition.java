package io.pelle.mango.gwt.commons.toastr;

public enum ToastPosition {

	BOTTOM_LEFT("toast-bottom-left"), BOTTOM_RIGHT("toast-bottom-right"), TOP_RIGHT("toast-top-right"), TOP_LEFT("toast-top-left"), TOP_FULL_WIDTH("toast-top-full-width"), BOTTOM_FULL_WIDTH("toast-bottom-full-width");

	private String value;

	private ToastPosition(String pos) {
		this.value = pos;
	}

	public String getValue() {
		return this.value;
	}

}