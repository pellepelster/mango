package io.pelle.mango.client.web.modules.dictionary.container;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;

import io.pelle.mango.client.base.modules.dictionary.container.IUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IBaseContainerModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;
import io.pelle.mango.client.web.modules.dictionary.controls.BaseDictionaryControl;
import io.pelle.mango.client.web.modules.dictionary.controls.ControlFactory;

public abstract class BaseContainerElement<ModelType extends IBaseContainerModel> extends BaseDictionaryElement<ModelType> {

	private static final int DEFAULT_COLUMN_COUNT = 1;

	private List<BaseDictionaryControl<?, ?>> controls = new ArrayList<BaseDictionaryControl<?, ?>>();

	private List<BaseContainerElement<?>> children = new ArrayList<BaseContainerElement<?>>();

	private List<IUpdateListener> updateListeners = new ArrayList<>();

	@SuppressWarnings("static-access")
	public BaseContainerElement(ModelType baseContainer, BaseDictionaryElement<? extends IBaseModel> parent) {
		super(baseContainer, parent);

		if (!baseContainer.getChildren().isEmpty()) {
			for (IBaseContainerModel baseContainerModel : baseContainer.getChildren()) {
				this.children.add(ContainerFactory.getInstance().createContainer(baseContainerModel, this));
			}

		} else if (!baseContainer.getControls().isEmpty()) {
			for (IBaseControlModel baseControlModel : baseContainer.getControls()) {
				this.controls.add(ControlFactory.getInstance().createControl(baseControlModel, this));
			}
		}
	}

	public List<BaseDictionaryControl<? extends IBaseControlModel, ?>> getControls() {
		return this.controls;
	}

	public List<BaseContainerElement<?>> getChildren() {
		return this.children;
	}

	@Override
	public List<? extends BaseDictionaryElement<?>> getAllChildren() {
		List<BaseDictionaryElement<?>> allChildren = new ArrayList<BaseDictionaryElement<?>>();

		allChildren.addAll(this.children);
		allChildren.addAll(this.controls);

		return allChildren;
	}

	public int getColummCount() {
		if (getModel().getLayout() != null && getModel().getLayout().getColumns() > 0) {
			return getModel().getLayout().getColumns();
		} else {
			return DEFAULT_COLUMN_COUNT;
		}

	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("name", getModel().getName()).toString();
	}

	public void addUpdateListener(IUpdateListener updateListener) {
		updateListeners.add(updateListener);
	}

}
