package io.pelle.mango.demo.client;

import io.pelle.mango.client.base.modules.dictionary.hooks.BaseEditorHook;
import io.pelle.mango.client.gwt.GWTLayoutFactory;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.module.ModuleHandler;
import io.pelle.mango.client.web.modules.navigation.ModuleNavigationModule;
import io.pelle.mango.demo.client.showcase.CountryVO;

import java.math.BigDecimal;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel.Direction;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DemoClient implements EntryPoint {

	/** {@inheritDoc} */
	@Override
	public void onModuleLoad() {
		MangoClientWeb.getInstance().setLayoutFactory(new GWTLayoutFactory(Unit.PX));
		init();
		ModuleHandler.getInstance().startUIModule(ModuleNavigationModule.NAVIGATION_UI_MODULE_LOCATOR, Direction.WEST.toString());

		String greetingText = "";
		greetingText += "<h2>Mango Showcase</h2><br/>";
		greetingText += "<span style=\"font-size:150%;\">This example Mango application showcases some key features that make up the generic CRUD UI part of Mango.<br/>";
		greetingText += "Click on question marks after the country search/editor title to see how the model corresponds to the UI.</span>";
		greetingText += "<h2>Country Search</h2><br/>";
		greetingText += "<img src=\"images/country_search.png\"/>";
		greetingText += "<h2>Country Editor</h2><br/>";
		greetingText += "<span style=\"font-size:150%;\">To open an empty editor click on the create button (+) in the Country Search toolbar</span>";
		greetingText += "<img src=\"images/country_editor.png\"/>";

		final PopupPanel greeting = new PopupPanel();

		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setSpacing(25);

		HTML content = new HTML(SafeHtmlUtils.fromTrustedString(greetingText));
		content.getElement().getStyle().setOverflowY(Overflow.SCROLL);
		content.setWidth("600px");
		content.setHeight("500px");

		vPanel.add(content);

		Button closeButton = new Button("Got it!");
		closeButton.getElement().getStyle().setBackgroundColor("#FF6666");
		closeButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				greeting.hide();
			}
		});

		vPanel.add(closeButton);
		greeting.setWidget(vPanel);
		greeting.setGlassEnabled(true);
		greeting.center();
		greeting.setAutoHideEnabled(true);
		greeting.show();
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

		createNavigationHelpText();
		createCountrySearchHelpText();
		createCountryEditorHelpText();

		MangoDemoDictionaryModel.COUNTRY.COUNTRY_EDITOR.addEditorHook(new BaseEditorHook<CountryVO>() {

			@Override
			public void onSave(AsyncCallback<Boolean> asyncCallback, CountryVO vo) {

				vo.setCountryExchangeRate(new BigDecimal(1.56));
				asyncCallback.onSuccess(true);
			}
		});

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

	private void createCountryEditorHelpText() {

		String helpText = "";
		helpText += "<strong>Model defining the country editor</strong><br/>";
		helpText += "Again all controls are reused from the central defintion in dictionarycontrols. Note the label for the <strong>IsoCode3</strong> control model which is overriden in the editor model.<br/>";
		helpText += "<pre style=\"overflow: auto; height: 200px;\">";
		helpText += "stringdatatype CountryName {\n  label \"Name\"\n  maxLength 64\n}\n";
		helpText += "stringdatatype IsoCode2 {\n  label \"ISO-Code (2)\"\n  maxLength 2\n  minLength 2\n}\n";
		helpText += "stringdatatype IsoCode3 {\n  label \"ISO-Code (3)\"\n  maxLength 3\n  minLength 3\n}\n";
		helpText += "entitydatatype CountryCurrency {\n  label \"Currency\"\n  entity Currency }\n";
		helpText += "\n";
		helpText += "entity Country {\n";
		helpText += "  naturalkey { countryIsoCode2 }\n";
		helpText += "  string IsoCode2 countryIsoCode2\n";
		helpText += "  string IsoCode3 countryIsoCode3\n";
		helpText += "  string CountryName countryName\n";
		helpText += "  entity CountryCurrency countryCurrency\n";
		helpText += "}";
		helpText += "\n";
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
		helpText += "        width 32\n";
		helpText += "      }\n";
		helpText += "      referencecontrol CountryCurrency {\n";
		helpText += "        entityattribute countryCurrency\n";
		helpText += "        dictionary Currency\n";
		helpText += "      }\n";
		helpText += "      bigdecimalcontrol CountryExchangeRate {\n";
		helpText += "        entityattribute countryExchangeRate\n";
		helpText += "        readonly true\n";
		helpText += "      }\n";
		helpText += "    }\n";
		helpText += "  }\n";
		helpText += "  [...]\n";
		helpText += "  dictionaryeditor CountryEditor {\n";
		helpText += "    layout { columns 2 }\n";
		helpText += "    textcontrol ref CountryIsoCode2\n";
		helpText += "    textcontrol ref CountryIsoCode3 {\n        label \"Alpha 3\"\n      }\n";
		helpText += "    textcontrol ref CountryName\n";
		helpText += "    referencecontrol ref CountryCurrency\n";
		helpText += "    bigdecimalcontrol ref CountryExchangeRate\n";
		helpText += "  }\n";
		helpText += "}\n";
		helpText += "</pre>";

		MangoDemoDictionaryModel.COUNTRY.COUNTRY_EDITOR.setHelpText(replaceKeywords(helpText));
	}

	private void createCountrySearchHelpText() {

		String helpText = "";
		helpText += "<strong>Model defining the country search</strong><br/>";
		helpText += "All controls used in the model are defined once in the dictionarycontrols section of the dictionary and then referenced from the filter/result model. "
				+ "Each control referes to an entityAttribute from the entity that is set in the root dictionary model. The control labels are inherited from the datatypes "
				+ "that are used for each entityAttribute. All model attributes like label/maxlength/... can be overridden at any point in the inheritance hierarchy (see "
				+ "the label for the <strong>IsoCode3</strong> control model for example.<br/>";
		helpText += "<pre style=\"overflow: auto; height: 200px;\">";
		helpText += "stringdatatype CountryName {\n  label \"Name\"\n}\n";
		helpText += "stringdatatype IsoCode2 {\n  label \"ISO-Code (2)\"\n  maxLength 2\n  minLength 2\n}\n";
		helpText += "stringdatatype IsoCode3 {\n  label \"ISO-Code (3)\"\n  maxLength 3\n  minLength 3\n}\n";
		helpText += "entitydatatype CountryCurrency {\n  label \"Currency\"\n  entity Currency }\n";
		helpText += "\n";
		helpText += "entity Country {\n";
		helpText += "  naturalkey { countryIsoCode2 }\n";
		helpText += "  string IsoCode2 countryIsoCode2\n";
		helpText += "  string IsoCode3 countryIsoCode3\n";
		helpText += "  string CountryName countryName\n";
		helpText += "  entity CountryCurrency countryCurrency\n";
		helpText += "}";
		helpText += "\n";
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
		helpText += "      layout { columns 2 }\n";
		helpText += "      textcontrol ref CountryIsoCode2\n";
		helpText += "      textcontrol ref CountryIsoCode3 {\n        label \"ISO-3166 (Alpha 3)\"\n      }\n";
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
		result = keyword(result, "layout");
		result = keyword(result, "minLength");
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
