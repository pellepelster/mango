package io.pelle.mango.client.web.modules.dictionary.container;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;

import io.pelle.mango.client.base.modules.dictionary.IUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.container.IBaseContainer;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IBaseContainerModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;
import io.pelle.mango.client.web.modules.dictionary.controls.BaseDictionaryControl;
import io.pelle.mango.client.web.modules.dictionary.controls.ControlFactory;

public abstract class BaseContainerElement<ModelType extends IBaseContainerModel, UpdateListenerType extends IUpdateListener> extends BaseDictionaryElement<ModelType>
		implements IBaseContainer<UpdateListenerType> {

	private static final int DEFAULT_COLUMN_COUNT = 1;

	private List<BaseDictionaryControl<?, ?>> controls = new ArrayList<BaseDictionaryControl<?, ?>>();

	private List<BaseContainerElement<?, ?>> children = new ArrayList<BaseContainerElement<?, ?>>();

	private List<UpdateListenerType> updateListeners = new ArrayList<>();

	private boolean enabled = true;

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

	public List<UpdateListenerType> getUpdateListeners() {
		return updateListeners;
	}

	public List<BaseDictionaryControl<? extends IBaseControlModel, ?>> getControls() {
		return this.controls;
	}

	public List<BaseContainerElement<?, ?>> getChildren() {
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

	protected void fireUpdateListeners() {
		for (IUpdateListener updateListener : updateListeners) {
			updateListener.onUpdate();
		}
	}

	@Override
	public void addUpdateListener(UpdateListenerType updateListener) {
		updateListeners.add(updateListener);
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		
		boolean doFireUpdateListener = this.enabled != enabled;
		
		this.enabled = enabled;
		
		if (doFireUpdateListener) {
			fireUpdateListeners();
		}
		
	}
}
