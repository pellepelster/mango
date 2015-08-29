package io.pelle.mango.client.base.modules.dictionary;

import java.util.Collection;

public interface IListUpdateListener<ListType> extends IUpdateListener {
	
	void onAdded(Collection<ListType> added);

	void onRemoved(Collection<ListType> removed);

}