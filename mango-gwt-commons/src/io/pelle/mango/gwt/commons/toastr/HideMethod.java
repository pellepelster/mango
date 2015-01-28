package io.pelle.mango.gwt.commons.toastr;

public enum HideMethod {

	FADE_OUT("fadeOut");

	private String value;

	private HideMethod(String pos) {
		this.value = pos;
	}

	public String getValue() {
		return this.value;
	}
}