package io.pelle.mango.gwt.commons.toastr;

public enum ShowEasing {

	SWING("swing"), LINEAR("linear");

	private String value;

	private ShowEasing(String pos) {
		this.value = pos;
	}

	public String getValue() {
		return this.value;
	}
}