package io.pelle.mango.demo.client;

import io.pelle.mango.client.base.modules.hierarchical.BaseHierarchicalConfiguration;

public class TestClientHierarchicalConfiguration extends BaseHierarchicalConfiguration {

	public static final String ID = "test";

	public TestClientHierarchicalConfiguration() {
		super(ID, "Test Hierarchy");

		addHierarchy(MangoDemoDictionaryModel.COMPANY);
		addHierarchy(MangoDemoDictionaryModel.DEPARTMENT, MangoDemoDictionaryModel.COMPANY);
		addHierarchy(MangoDemoDictionaryModel.EMPLOYEE, MangoDemoDictionaryModel.DEPARTMENT);
	}
}