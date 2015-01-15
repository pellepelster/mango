/**
 * Copyright (c) 2013 Christian Pelster.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Christian Pelster - initial API and implementation
 */
package io.pelle.mango.client.web;

import com.google.gwt.i18n.client.LocalizableResource.Generate;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.safehtml.shared.SafeHtml;

@Generate(format = "com.google.gwt.i18n.rebind.format.PropertiesFormat")
public interface MangoMessages extends Messages {

	static final String MANDATORY_MESSAGE_KEY = "mandatory.message.key";

	@DefaultMessage("Add {0}")
	String dictionaryAdd(String title);

	@DefaultMessage("Add")
	String editableTableAdd();

	@DefaultMessage("Back to search results")
	String editorBack();

	@DefaultMessage("Editor contains unsaved data, do you really want to close?")
	String editorClose();

	@DefaultMessage("Unable to save the data, the editor contains errors")
	String editorContainsErrors();

	@DefaultMessage("*")
	String editorDirtyMarker();

	@DefaultMessage("*")
	String mandatoryMarker();

	@DefaultMessage("New")
	String editorNew();

	@DefaultMessage("None")
	String hierarchicalNone();

	@DefaultMessage("None")
	String fileNone();

	@DefaultMessage("Save")
	String editorSave();

	@DefaultMessage("{0}")
	String editorTitle(String editorTitle);

	@DefaultMessage("''{0}'' is not a valid date")
	String dateParseError(String value);

	@DefaultMessage("''{0}'' is not a valid decimal")
	String decimalParseError(String value);

	@DefaultMessage("''{0}'' is not a valid boolean")
	String booleanParseError(String value);

	@DefaultMessage("''{0}'' is not a valid enumeration")
	String enumerationParseError(String value);

	@DefaultMessage("''{0}'' is not a valid integer")
	String integerParseError(String value);

	@DefaultMessage("''{0}'' could not be found")
	String referenceParseError(String value);

	@Key(MANDATORY_MESSAGE_KEY)
	@DefaultMessage("Input is needed for ''{0}''")
	String mandatoryMessage(@Optional String fieldLabel);

	@DefaultMessage("Navigation")
	String navigationTitle();

	@DefaultMessage("Create")
	String dictionaryCreate();

	@DefaultMessage("Edit")
	String dictionaryEdit();

	@DefaultMessage("{0} results")
	@AlternateMessage({ "=1", "{0} result" })
	String searchResults(@PluralCount int resoultCount);

	@DefaultMessage("{0} results, more available")
	String searchResultsMoreAvailable(int resoultCount);

	@DefaultMessage("Search")
	String searchSearch();

	@DefaultMessage("Search results for \"{0}\"")
	String dictionarySearchResults(String query);

	@DefaultMessage("Search for {0}")
	String searchTitle(String dictionaryName);

	@DefaultMessage("Refresh")
	String editorRefresh();

	@DefaultMessage("Ok")
	String buttonOk();

	@DefaultMessage("Cancel")
	String buttonCancel();

	@DefaultMessage("Select {0}")
	String voSelectionHeader(String message);

	@DefaultMessage("Add children")
	String addChildren();

	@DefaultMessage("None")
	String none();

	@DefaultMessage("{0}")
	String panelTitle(String title);

	@DefaultMessage("Search")
	String dictionarySearch();

	@DefaultMessage("Info")
	String dictionaryInfo();

	@DefaultMessage("Created")
	String dictionaryInfoCreateDate();

	@DefaultMessage("User")
	String dictionaryInfoCreateUser();

	@DefaultMessage("Updated")
	String dictionaryInfoUpdateDate();

	@DefaultMessage("User")
	String dictionaryInfoUpdateUser();

	@DefaultMessage("undefined")
	String undefined();

	@DefaultMessage("true")
	String trueText();

	@DefaultMessage("false")
	String falseText();

	@DefaultMessage("?")
	String helpShort();

	@DefaultMessage("Log")
	String logTitle();

	@DefaultMessage("Message")
	String logMessageTitle();

	@DefaultMessage("Time")
	String logTimestampTitle();

	@DefaultMessage("Log")
	String log();

	@DefaultMessage("Properties")
	SafeHtml properties();

}