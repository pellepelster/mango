package io.pelle.mango.client.web.modules.dictionary.container;

import java.util.HashMap;
import java.util.Map;

import io.pelle.mango.client.base.modules.dictionary.model.containers.IAssignmentTableModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IBaseContainerModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.ICompositeModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.ICustomCompositeModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IEditableTableModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IFileListModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.ITabfolderModel;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;
import io.pelle.mango.client.web.util.CustomCompositeProvider;

public class ContainerFactory {

	private static ContainerFactory instance;

	public static ContainerFactory getInstance() {

		if (instance == null) {
			instance = new ContainerFactory();
		}

		return instance;
	}

	private Map<String, String> customCompositeFactories = new HashMap<String, String>();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BaseContainerElement<?, ?> createContainer(IBaseContainerModel baseContainerModel, BaseDictionaryElement parent) {

		if (baseContainerModel instanceof IFileListModel) {
			return new FileList((IFileListModel) baseContainerModel, parent);
		} else if (baseContainerModel instanceof ICustomCompositeModel) {

			ICustomCompositeModel customCompositeModel = (ICustomCompositeModel) baseContainerModel;

			String customCompositeFactoryName = customCompositeFactories.get(customCompositeModel.getType());

			if (customCompositeFactoryName == null) {
				throw new RuntimeException("unsupported custom container type '" + customCompositeModel.getType() + "'");
			}

			return (BaseContainerElement<?, ?>) CustomCompositeProvider.getInstance().create(customCompositeFactoryName, customCompositeModel, parent);

		} else if (baseContainerModel instanceof ITabfolderModel) {
			return new TabFolder((ITabfolderModel) baseContainerModel, parent);
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

	public void registerCustomCompositeFactory(String type, String customCompositeFactoryName) {
		customCompositeFactories.put(type, customCompositeFactoryName);
	}

}
