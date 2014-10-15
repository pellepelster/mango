package io.pelle.mango.demo.client;

import io.pelle.mango.client.gwt.GWTLayoutFactory;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.module.ModuleHandler;
import io.pelle.mango.client.web.modules.navigation.ModuleNavigationModule;
import io.pelle.mango.test.client.MangoDemoClientConfiguration;
import io.pelle.mango.test.client.MangoDemoDictionaryModel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel.Direction;

public class DemoClient implements EntryPoint {

	/** {@inheritDoc} */
	@Override
	public void onModuleLoad() {
		try {
			MangoClientWeb.getInstance().setLayoutFactory(new GWTLayoutFactory(Unit.PX));

			init();
		} catch (Exception e) {
			GWT.log(e.getMessage(), e);
		}

		ModuleHandler.getInstance().startUIModule(ModuleNavigationModule.NAVIGATION_UI_MODULE_LOCATOR, Direction.WEST.toString());

	}

	public void init() {
		MangoDemoClientConfiguration.registerAll();

		String source = MangoDslGenerator.create().block("stringdatatype", "StringDatatype1 ", "maxLength", "42")
				.block("entity", " Entity1", "string", "StringDatatype1 stringDatatype1").abbr()
				.block("textcontrol", "TextControl1", "entityattribute", "Entity1.stringDatatype1").abbr().toString();

		String help = source;

		MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1.TEXT_CONTROL1.setHelpText(help);
	}
}
