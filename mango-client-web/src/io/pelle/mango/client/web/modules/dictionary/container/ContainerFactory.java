package io.pelle.mango.client.web.modules.dictionary.container;

import io.pelle.mango.client.base.modules.dictionary.model.containers.IAssignmentTableModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IBaseContainerModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.ICompositeModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IEditableTableModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.ITabfolderModel;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

public class ContainerFactory {
	private static ContainerFactory instance;

	public static ContainerFactory getInstance() {
		if (instance == null) {
			instance = new ContainerFactory();
		}

		return instance;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static BaseContainerElement<? extends IBaseContainerModel> createContainer(IBaseContainerModel baseContainerModel, BaseDictionaryElement parent) {
		if (baseContainerModel instanceof ITabfolderModel) {
			return new TabFolder((ITabfolderModel) baseContainerModel, parent);
		} else if (baseContainerModel instanceof ICompositeModel) {
			return new Composite((ICompositeModel) baseContainerModel, parent);
		} else if (baseContainerModel instanceof IEditableTableModel) {
			return new EditableTable((IEditableTableModel) baseContainerModel, parent);
		} else if (baseContainerModel instanceof IAssignmentTableModel) {
			return new AssignmentTable((IAssignmentTableModel) baseContainerModel, parent);
		} else {
			throw new RuntimeException("unsupported cntainer model type '" + baseContainerModel.getClass().getName() + "'");
		}
	}

}
