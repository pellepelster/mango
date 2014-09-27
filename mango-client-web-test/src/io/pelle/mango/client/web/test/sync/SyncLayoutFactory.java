package io.pelle.mango.client.web.test.sync;

import io.pelle.mango.client.base.layout.ILayoutFactory;
import io.pelle.mango.client.base.layout.IModuleUI;
import io.pelle.mango.client.web.module.ModuleUIFactoryRegistry;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class SyncLayoutFactory implements ILayoutFactory {
	private List<IModuleUI> moduleUIs = new ArrayList<IModuleUI>();

	public interface JunitLayoutFactoryCallback {
		public void onStartModuleUI(IModuleUI moduleUI);

	}

	public SyncLayoutFactory() {
		ModuleUIFactoryRegistry.getInstance().addModuleFactory(new DictionaryEditorModuleSyncTestUIFactory());
	}

	@Override
	public void showModuleUI(IModuleUI moduleUI, String location) {
		this.moduleUIs.add(moduleUI);

	}

	@Override
	public void closeModuleUI(IModuleUI moduleUI) {
	}

}
