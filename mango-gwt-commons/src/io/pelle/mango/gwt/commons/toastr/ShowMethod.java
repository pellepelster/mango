package io.pelle.mango.gwt.commons.toastr;

public enum ShowMethod {

	FADE_IN("fadeIn"), SLIDE_DOW("slideDown");

	private String value;

	private ShowMethod(String pos) {
		this.value = pos;
	}

	public String getValue() {
		return this.value;
	}
}