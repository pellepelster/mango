package io.pelle.mango.client.web.modules.dictionary.query;

import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.web.modules.dictionary.container.BaseContainerElement;
import io.pelle.mango.client.web.modules.dictionary.container.Composite;
import io.pelle.mango.client.web.modules.dictionary.controls.BaseDictionaryControl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DictionaryCompositeQuery {

	private Composite composite;

	private DictionaryCompositeQuery(Composite composite) {
		this.composite = composite;
	}

	public static DictionaryCompositeQuery create(Composite composite) {
		return new DictionaryCompositeQuery(composite);
	}

	private void recurseContainerModels(List<BaseDictionaryControl<? extends IBaseControlModel, ?>> baseControls, BaseContainerElement<?> baseContainerElement) {
		baseControls.addAll(baseContainerElement.getControls());
		recurseContainerModels(baseControls, baseContainerElement.getChildren());
	}

	private void recurseContainerModels(List<BaseDictionaryControl<? extends IBaseControlModel, ?>> baseControls, List<BaseContainerElement<?>> baseContainerElements) {
		for (BaseContainerElement<?> baseContainerElement : baseContainerElements) {
			recurseContainerModels(baseControls, baseContainerElement);
		}
	}

	public Collection<BaseDictionaryControl<? extends IBaseControlModel, ?>> getAllControls() {

		List<BaseDictionaryControl<? extends IBaseControlModel, ?>> baseControls = new ArrayList<BaseDictionaryControl<? extends IBaseControlModel, ?>>();

		baseControls.addAll(composite.getControls());
		recurseContainerModels(baseControls, this.composite.getChildren());

		return baseControls;
	}

}
