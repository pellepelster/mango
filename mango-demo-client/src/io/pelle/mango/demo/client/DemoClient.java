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
import com.google.gwt.regexp.shared.RegExp;
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

		String string1 = "";
		string1 += "<strong>Model defining this control</strong><br/>";
		string1 += "<pre>";
		string1 += "stringdatatype StringDatatype1 {\n";
		string1 += "	maxLength 42\n";
		string1 += "}\n\n";
		string1 += "entity Entity1 {\n";
		string1 += "	string StringDatatype1 stringDatatype1\n";
		string1 += "}\n\n";
		string1 += "dictionary DemoDictionary1 {\n";
		string1 += "	[...]\n";
		string1 += "	textcontrol TextControl1 {\n";
		string1 += "		entityattribute Entity1.stringDatatype1\n";
		string1 += "	}\n";
		string1 += "	[...]\n";
		string1 += "}";
		string1 += "</pre>";

		MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1.TEXT_CONTROL1.setHelpText(replaceKeywords(string1));

		String string2 = "";
		string2 += "<strong>Model defining this control</strong><br/>";
		string2 += "<pre>";
		string2 += "entitydatatype Entity2Datatype { entity Entity2 }\n\n";
		string2 += "entity Entity1 { entity Entity2Datatype entity2Datatype }\n\n";
		string2 += "stringdatatype StringDatatype2 { }\n\n";
		string2 += "entity Entity2 {\n";
		string2 += "	string StringDatatype2 stringDatatype2\n";
		string2 += "}\n\n";
		string2 += "dictionary DemoDictionary1 {\n";
		string2 += "	[...]\n";
		string2 += "	referencecontrol ReferenceControl1 {\n";
		string2 += "		entityattribute Entity1.entity2Datatype\n";
		string2 += "		dictionary DemoDictionary2\n";
		string2 += "	}\n";
		string2 += "	[...]\n";
		string2 += "}\n\n";
		string2 += "dictionary DemoDictionary2 {\n";
		string2 += "	[...]\n";
		string2 += "	labelcontrols {\n";
		string2 += "		referencecontrol ReferenceControl1 {\n";
		string2 += "			entityattribute Entity1.entity2Datatype\n";
		string2 += "			dictionary DemoDictionary2\n";
		string2 += "		}\n";
		string2 += "	}\n";
		string2 += "	[...]\n";
		string2 += "}";
		string2 += "</pre>";

		MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1.REFERENCE_CONTROL1.setHelpText(replaceKeywords(string2));
	}

	private String replaceKeywords(String string) {

		String result = string;

		result = keyword(result, "textcontrol");
		result = keyword(result, "maxLength");
		result = keyword(result, "entityattribute");
		result = keyword(result, "entity");
		result = keyword(result, "stringdatatype");
		result = keyword(result, "string");
		result = keyword(result, "entity");
		result = keyword(result, "referencecontrol");
		result = keyword(result, "dictionary");
		result = keyword(result, "entitydatatype");
		result = keyword(result, "labelcontrols");

		result = coloredItalicString(result, "\\[\\.\\.\\.\\]", "#999");

		return result.toString();
	}

	private String coloredStrongString(String text, String colorString, String color) {
		return replace(text, colorString, "<strong><span style=\"color:" + color + ";font-weight:bold\">", "</span></strong>");
	}

	private String coloredItalicString(String text, String colorString, String color) {
		return replace(text, colorString, "<em><span style=\"color:" + color + ";font-weight:bold\">", "</span></em>");
	}

	public String keyword(String text, String keyword) {
		return coloredStrongString(text, keyword, "#950055");
	}

	public String replace(String text, String strongText, String before, String after) {
		RegExp pattern = RegExp.compile("(" + strongText + ")(\\s+)", "g");
		return pattern.replace(text, before + "$1" + after + "$2");
	}
}
