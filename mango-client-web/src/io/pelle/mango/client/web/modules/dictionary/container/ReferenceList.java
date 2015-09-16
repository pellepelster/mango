package io.pelle.mango.client.web.modules.dictionary.container;

import io.pelle.mango.client.base.modules.dictionary.IUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.container.IReferenceList;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IReferenceListModel;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

public class ReferenceList extends BaseContainerElement<IReferenceListModel, IUpdateListener>implements IReferenceList {

	public ReferenceList(IReferenceListModel referenceListModel, BaseDictionaryElement<? extends IBaseModel> parent) {
		super(referenceListModel, parent);
	}

}
