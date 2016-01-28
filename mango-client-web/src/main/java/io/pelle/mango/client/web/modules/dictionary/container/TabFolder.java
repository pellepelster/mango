package io.pelle.mango.client.web.modules.dictionary.container;

import java.util.ArrayList;
import java.util.List;

import io.pelle.mango.client.base.modules.dictionary.IUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.container.ITab;
import io.pelle.mango.client.base.modules.dictionary.container.ITabFolder;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.ITabfolderModel;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

public class TabFolder extends BaseContainerElement<ITabfolderModel, IUpdateListener> implements ITabFolder {

	private List<ITab> tabs = new ArrayList<>();

	public TabFolder(ITabfolderModel tabfolderModel, BaseDictionaryElement<? extends IBaseModel> parent) {

		super(tabfolderModel, parent);
		
		for (BaseContainerElement<?, ?> baseContainerElement : getChildren()) {
			
			if (baseContainerElement instanceof ITab) {
				tabs.add((ITab) baseContainerElement);
			}
			
		}

	}

	@Override
	public List<ITab> getTabs() {
		return tabs;
	}

	public ITab getActiveTab() {
		if (tabs.size() > 0) {
			return tabs.get(0);
		} else {
			return null;
		}
	}

}
