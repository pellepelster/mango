package io.pelle.mango.client.web.modules.dictionary.container;

import io.pelle.mango.client.base.modules.dictionary.model.containers.IAssignmentTableModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IBaseContainerModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.ICompositeModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.ICustomCompositeModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IEditableTableModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IFileListModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IReferenceListModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IStateModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.ITabfolderModel;
import io.pelle.mango.client.web.MangoClientWeb;
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
	public BaseContainerElement<?, ?> createContainer(IBaseContainerModel baseContainerModel, BaseDictionaryElement parent) {

		if (baseContainerModel instanceof IReferenceListModel) {
			return new ReferenceList((IReferenceListModel) baseContainerModel, parent);
		} else if (baseContainerModel instanceof IFileListModel) {
			return new FileList((IFileListModel) baseContainerModel, parent);
		} else if (baseContainerModel instanceof ICustomCompositeModel) {
			ICustomCompositeModel customCompositeModel = (ICustomCompositeModel) baseContainerModel;
			return (BaseContainerElement<?, ?>) MangoClientWeb.getInstance().getMangoProvider().getCompositeFactory().create(customCompositeModel.getType(), customCompositeModel, parent);
		} else if (baseContainerModel instanceof ITabfolderModel) {
			return new TabFolder((ITabfolderModel) baseContainerModel, parent);
		} else if (baseContainerModel instanceof IStateModel) {
			return new StateContainer((IStateModel) baseContainerModel, parent);
		} else if (baseContainerModel instanceof ICompositeModel) {
			if (baseContainerModel.getParent() instanceof ITabfolderModel) {
				return new Tab((ICompositeModel) baseContainerModel, parent);
			} else {
				return new Composite((ICompositeModel) baseContainerModel, parent);
			}
		} else if (baseContainerModel instanceof IEditableTableModel) {
			return new EditableTable((IEditableTableModel) baseContainerModel, parent);
		} else if (baseContainerModel instanceof IAssignmentTableModel) {
			return new AssignmentTable((IAssignmentTableModel) baseContainerModel, parent);
		} else {
			throw new RuntimeException("unsupported container model type '" + baseContainerModel.getClass().getName() + "'");
		}
	}

}
