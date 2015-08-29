package io.pelle.mango.client.base.modules.dictionary.container;

import java.util.List;

import io.pelle.mango.client.base.modules.dictionary.IUpdateListener;

public interface ITabFolder extends IBaseContainer<IUpdateListener> {

	List<ITab> getTabs();
}
