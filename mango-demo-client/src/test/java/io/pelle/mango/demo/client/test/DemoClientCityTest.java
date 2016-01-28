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
package io.pelle.mango.demo.client.test;

/**
 public class DemoClientCityTest extends MangoAsyncGwtTestCase<CityVO> {

 @Override
 public String getModuleName() {
 return "de.pellepelster.myadmin.demo.DemoTest";
 }

 @Test
 public void testCreateAndSearch() {
 MyAdminTest.getInstance();
 DemoClientConfiguration.registerAll();

 deleteAllVOs(CityVO.class);

 DictionarySearchModuleTestUIAsyncHelper<CityVO> search = openSearch(DemoDictionaryModel.CITY);
 search.assertTitle("City (0 results)");
 search.assertResultCount(0);

 DictionaryEditorModuleTestUIAsyncHelper<CityVO> editor = openEditor(DemoDictionaryModel.CITY);
 editor.assertTitle("City (New)");

 editor.getTextControlTest(DemoDictionaryModel.CITY.CITY_EDITOR.COMPOSITE2.COMPOSITE3.CITY_NAME).setValue("xxx");
 editor.assertTitle("City (New) *");
 editor.save();

 search.execute();
 search.assertTitle("City (1 result)");
 search.assertResultCount(1);

 search.getTextControlTest(DemoDictionaryModel.CITY.CITY_SEARCH.CITY_FILTER.COMPOSITE1.CITY_NAME).setValue("yyy");
 search.execute();
 search.assertTitle("City (0 results)");
 search.assertResultCount(0);

 search.getTextControlTest(DemoDictionaryModel.CITY.CITY_SEARCH.CITY_FILTER.COMPOSITE1.CITY_NAME).setValue("");
 search.execute();
 search.assertResultCount(1);

 runAsyncTests();
 }
 }
 */
