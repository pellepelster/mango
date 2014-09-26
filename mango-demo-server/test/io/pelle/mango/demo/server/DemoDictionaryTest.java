package io.pelle.mango.demo.server;

import static org.junit.Assert.assertNotNull;
import io.pelle.mango.client.web.modules.dictionary.controls.ControlFactory;
import io.pelle.mango.client.web.modules.dictionary.controls.GroupControl;
import io.pelle.mango.test.client.MangoDemoDictionaryModel;

import org.junit.Test;

public class DemoDictionaryTest extends BaseDemoTest {

	@Test
	public void testCreateAndSaveNaturalKey() {

		GroupControl groupControl = (GroupControl) ControlFactory.createControl(MangoDemoDictionaryModel.TESTDICTIONARY1.DICTIONARY_SEARCH1.DICTIONARY_FILTER1.CONTROL_GROUP1, null);

		assertNotNull(groupControl);
	}

}
