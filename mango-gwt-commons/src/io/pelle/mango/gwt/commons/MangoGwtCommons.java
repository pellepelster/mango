package io.pelle.mango.gwt.commons;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

public class MangoGwtCommons implements EntryPoint {

	Resources INSTANCE = GWT.create(Resources.class);

	interface Resources extends ClientBundle {

		@Source("toastr.min.js")
		TextResource toastrJs();

		@Source("toastr.min.css")
		TextResource toastrCss();

		@Source("jquery-1.11.2.min.js")
		TextResource jqueryJs();

	}

	@Override
	public void onModuleLoad() {

		ScriptInjector.fromString(INSTANCE.jqueryJs().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
		ScriptInjector.fromString(INSTANCE.toastrJs().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
		StyleInjector.inject(INSTANCE.toastrCss().getText());
	}

}
