package io.pelle.mango.client.web.test;

import io.pelle.mango.client.base.layout.ILayoutFactory;
import io.pelle.mango.client.base.layout.IModuleUI;
import io.pelle.mango.client.web.module.ModuleUIFactoryRegistry;
import io.pelle.mango.client.web.test.modules.dictionary.DictionaryEditorModuleTestUIFactory;
import io.pelle.mango.client.web.test.modules.dictionary.DictionarySearchModuleTestUIFactory;
import io.pelle.mango.client.web.test.modules.navigation.NavigationModuleTestUIFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel.Direction;

@SuppressWarnings("rawtypes")
public class JunitLayoutFactory implements ILayoutFactory {
	private List<IModuleUI> moduleUIs = new ArrayList<IModuleUI>();

	public interface JunitLayoutFactoryCallback {
		public void onStartModuleUI(IModuleUI moduleUI);

	}

	private final LinkedHashMap<Direction, List<IModuleUI>> currentModules = new LinkedHashMap<Direction, List<IModuleUI>>();

	public JunitLayoutFactory() {
		ModuleUIFactoryRegistry.getInstance().addModuleFactory(new NavigationModuleTestUIFactory());
		ModuleUIFactoryRegistry.getInstance().addModuleFactory(new DictionaryEditorModuleTestUIFactory());
		ModuleUIFactoryRegistry.getInstance().addModuleFactory(new DictionarySearchModuleTestUIFactory());
	}

	private IModuleUI getCurrentModuleUI(Direction direction) {
		for (Map.Entry<Direction, List<IModuleUI>> entry : this.currentModules.entrySet()) {
			if (entry.getKey() == direction) {
				if (entry.getValue().isEmpty()) {
					return null;
				} else {
					return entry.getValue().get(entry.getValue().size() - 1);
				}
			}
		}

		return null;
	}

	private Direction getDirection(String location) {
		try {
			return Direction.valueOf(location);
		} catch (Exception e) {
			return Direction.CENTER;
		}
	}

	@Override
	public void showModuleUI(IModuleUI moduleUI, String location) {
		DockLayoutPanel.Direction direction = getDirection(location);

		this.moduleUIs.add(moduleUI);

	}

	@Override
	public void closeModuleUI(IModuleUI moduleUI) {
	}

}
