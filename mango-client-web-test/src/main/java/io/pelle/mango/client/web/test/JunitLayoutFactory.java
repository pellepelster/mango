package io.pelle.mango.client.web.test;

import io.pelle.mango.client.base.layout.ILayoutFactory;
import io.pelle.mango.client.base.layout.IModuleUI;
import io.pelle.mango.client.web.module.ModuleUIFactoryRegistry;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class JunitLayoutFactory implements ILayoutFactory {
	private List<IModuleUI> moduleUIs = new ArrayList<IModuleUI>();

	public interface JunitLayoutFactoryCallback {
		public void onStartModuleUI(IModuleUI moduleUI);
	}

	public JunitLayoutFactory() {
		ModuleUIFactoryRegistry.getInstance().addModuleFactory(new DictionaryEditorModuleTestUIFactory());
		ModuleUIFactoryRegistry.getInstance().addModuleFactory(new DictionarySearchModuleTestUIFactory());
	}

	@Override
	public void showModuleUI(IModuleUI moduleUI, String location) {
		this.moduleUIs.add(moduleUI);

	}

	@Override
	public void closeModuleUI(IModuleUI moduleUI) {
	}

}
