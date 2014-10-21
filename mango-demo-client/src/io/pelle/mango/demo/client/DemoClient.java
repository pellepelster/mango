package io.pelle.mango.demo.client;

import io.pelle.mango.client.gwt.GWTLayoutFactory;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.module.ModuleHandler;
import io.pelle.mango.client.web.modules.navigation.ModuleNavigationModule;

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

		MangoDemoNavigationModel.ROOT.setHelpText("xxx");

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

		createNavigationHelpText();
		createCountrySearchHelpText();

	}

	private void createNavigationHelpText() {
		String helpText = "";
		helpText += "<strong>Model defining navigation tree for the showcase</strong><br/>";
		helpText += "The title text is taken from the navigation nodes name. Each navigation node can link to a dictionarySearch to open the corresponding search or an dictionaryEditor for the same functionality on an editor.<br/>";
		helpText += "<pre>";
		helpText += "navigationnode Showcase {\n";
		helpText += "  navigationnode Country {\n";
		helpText += "    dictionarySearch Country.CountrySearch\n";
		helpText += "  }\n";
		helpText += "  navigationnode Currency {\n";
		helpText += "    dictionarySearch Currency.CurrencySearch\n";
		helpText += "  }\n";
		helpText += "  navigationnode Customer {\n";
		helpText += "    dictionarySearch Customer.CustomerSearch\n";
		helpText += "  }\n";
		helpText += "}\n";
		helpText += "</pre>";
		MangoDemoNavigationModel.ROOT.setHelpText(replaceKeywords(helpText));
	}

	private void createCountrySearchHelpText() {
		String helpText = "";
		helpText += "<strong>Model defining the country search</strong><br/>";
		helpText += "All control used in the model are defined once in the dictionarycontrols section of the dictionary and then referenced from the filter/result. Each control referes to an <em>entityAttribute</em> that corresponds the the entity for the whole dictionary. See individual help texts for each control for more information on the specific control models.<br/>";
		helpText += "<pre>";
		helpText += "dictionary Country {\n";
		helpText += "  entity Country\n";
		helpText += "    dictionarycontrols {\n";
		helpText += "      textcontrol CountryIsoCode2 {\n";
		helpText += "        entityattribute countryIsoCode2\n";
		helpText += "      }\n";
		helpText += "      textcontrol CountryIsoCode3 {\n";
		helpText += "        entityattribute countryIsoCode3\n";
		helpText += "      }\n";
		helpText += "      textcontrol CountryName {\n";
		helpText += "        entityattribute countryName\n";
		helpText += "      }\n";
		helpText += "      referencecontrol CountryCurrency {\n";
		helpText += "        entityattribute countryCurrency\n";
		helpText += "        dictionary Currency\n";
		helpText += "    }\n";
		helpText += "  }\n";
		helpText += "  dictionarysearch CountrySearch {\n";
		helpText += "    label \"Countries\"\n";
		helpText += "    dictionaryfilter CountryFilter {\n";
		helpText += "      textcontrol ref CountryIsoCode2\n";
		helpText += "      textcontrol ref CountryIsoCode3\n";
		helpText += "      textcontrol ref CountryName\n";
		helpText += "      referencecontrol ref CountryCurrency\n";
		helpText += "    }\n";
		helpText += "    dictionaryresult CountryResult {\n";
		helpText += "      textcontrol ref CountryIsoCode2\n";
		helpText += "      textcontrol ref CountryIsoCode3\n";
		helpText += "      textcontrol ref CountryName\n";
		helpText += "      referencecontrol ref CountryCurrency\n";
		helpText += "    }\n";
		helpText += "  }\n";
		helpText += "  [...]\n";
		helpText += "}\n";
		helpText += "</pre>";

		MangoDemoDictionaryModel.COUNTRY.COUNTRY_SEARCH.setHelpText(replaceKeywords(helpText));
	}

	private String replaceKeywords(String string) {

		String result = string;

		result = keyword(result, "textcontrol");
		result = keyword(result, "maxLength");
		result = keyword(result, "entityattribute");
		result = keyword(result, "navigationnode");
		result = keyword(result, "dictionarySearch");
		result = keyword(result, "dictionaryEditor");
		result = keyword(result, "entity");
		result = keyword(result, "stringdatatype");
		result = keyword(result, "string");
		result = keyword(result, "entity");
		result = keyword(result, "referencecontrol");
		result = keyword(result, "dictionary");
		result = keyword(result, "entitydatatype");
		result = keyword(result, "labelcontrols");
		result = keyword(result, "dictionarycontrols");
		result = keyword(result, "dictionarysearch");
		result = keyword(result, "label");
		result = keyword(result, "dictionaryfilter");
		result = keyword(result, "dictionaryresult");
		result = keyword(result, "ref");

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
