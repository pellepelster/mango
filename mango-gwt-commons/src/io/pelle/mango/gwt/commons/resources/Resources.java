package io.pelle.mango.gwt.commons.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

public interface Resources extends ClientBundle {

	@Source("toastr.min.js")
	TextResource toastrJs();

	@Source("toastr.min.css")
	TextResource toastrCss();

	@Source("jquery-1.11.2.min.js")
	TextResource jqueryJs();

}