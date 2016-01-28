package io.pelle.mango.client.base.modules.dictionary.container;

import io.pelle.mango.client.base.modules.dictionary.IUpdateListener;

public interface IBaseContainer<UpdateListenerType extends IUpdateListener> {
	
	void addUpdateListener(UpdateListenerType updateListener);

	boolean isEnabled();

}
