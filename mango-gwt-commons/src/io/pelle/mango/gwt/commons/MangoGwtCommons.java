package io.pelle.mango.gwt.commons;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.StyleInjector;
import io.pelle.mango.gwt.commons.resources.Resources;

public class MangoGwtCommons implements EntryPoint {

	Resources INSTANCE = GWT.create(Resources.class);

	@Override
	public void onModuleLoad() {

		ScriptInjector.fromString(INSTANCE.jqueryJs().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
		ScriptInjector.fromString(INSTANCE.toastrJs().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
		StyleInjector.inject(INSTANCE.toastrCss().getText());
	}

}
