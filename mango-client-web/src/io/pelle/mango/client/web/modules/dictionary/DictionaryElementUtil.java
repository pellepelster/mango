package io.pelle.mango.client.web.modules.dictionary;

import io.pelle.mango.client.base.modules.dictionary.BaseDictionaryElementUtil;
import io.pelle.mango.client.base.modules.dictionary.IBaseDictionaryElement;
import io.pelle.mango.client.base.modules.dictionary.IBaseRootElement;
import io.pelle.mango.client.base.modules.dictionary.controls.IBaseControl;
import io.pelle.mango.client.base.modules.dictionary.model.BaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;
import io.pelle.mango.client.web.modules.dictionary.container.BaseContainerElement;
import io.pelle.mango.client.web.modules.dictionary.container.Composite;
import io.pelle.mango.client.web.modules.dictionary.container.TableRow;
import io.pelle.mango.client.web.modules.dictionary.controls.BaseDictionaryControl;
import io.pelle.mango.client.web.modules.dictionary.editor.BaseRootElement;
import io.pelle.mango.client.web.modules.dictionary.editor.DictionaryEditor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

public class DictionaryElementUtil extends BaseDictionaryElementUtil {
	public static <ElementType> ElementType getElement(BaseRootElement<?> baseRootElement, BaseModel<ElementType> baseModel) {
		List<String> descriptorModelIds = getParentModelIds(baseModel);

		List<String> modelIds = DictionaryElementUtil.getParentModelIds(baseRootElement.getModel());

		removeLeadingModelIds(modelIds, descriptorModelIds);

		return getElement(baseRootElement.getRootComposite(), descriptorModelIds, 0);
	}

	public static Collection<BaseDictionaryElement<?>> getAllElements(BaseRootElement<?> baseRootElement) {
		Collection<BaseDictionaryElement<?>> elements = new ArrayList<BaseDictionaryElement<?>>();

		getAllElements(baseRootElement, elements);

		return elements;
	}

	public static Collection<BaseDictionaryElement<?>> getElementsForAttributePath(BaseRootElement<?> baseRootElement, final String attributePath) {
		return Collections2.filter(getAllElements(baseRootElement), new Predicate<BaseDictionaryElement<?>>() {

			@Override
			public boolean apply(BaseDictionaryElement<?> baseElement) {
				if (baseElement instanceof BaseDictionaryControl) {
					BaseDictionaryControl<?, ?> baseDictionaryControl = (BaseDictionaryControl<?, ?>) baseElement;

					return baseDictionaryControl.getModel().getAttributePath().equals(attributePath);
				}

				return false;
			}
		});
	}

	public static void getAllElements(BaseDictionaryElement<?> parentElement, Collection<BaseDictionaryElement<?>> elements) {
		for (BaseDictionaryElement<?> baseDictionaryElement : parentElement.getAllChildren()) {
			elements.add(baseDictionaryElement);
			getAllElements(baseDictionaryElement, elements);
		}
	}

	@SuppressWarnings("unchecked")
	private static <ElementType> ElementType getElement(Composite rootComposite, List<String> modelIds, int level) {

		ElementType element = getElement(rootComposite.getChildren(), modelIds, 0);

		if (element == null) {
			return (ElementType) getControl(rootComposite.getControls(), modelIds, level);
		} else {
			return element;
		}

	}

	@SuppressWarnings("unchecked")
	private static <ElementType> ElementType getElement(List<BaseContainerElement<?, ?>> baseContainers, List<String> modelIds, int level) {
		for (BaseContainerElement<?, ?> baseContainer : baseContainers) {
			if (baseContainer.getModel().getName().equals(modelIds.get(level))) {

				if (level == modelIds.size() - 1) {
					return (ElementType) baseContainer;
				} else {
					IBaseControl<?, ?> baseControl = getControl(baseContainer.getControls(), modelIds, level + 1);

					if (baseControl != null) {
						return (ElementType) baseControl;
					} else {
						ElementType controlType = getElement(baseContainer.getChildren(), modelIds, level + 1);

						if (controlType != null) {
							return controlType;
						}
					}
				}
			}
		}

		return null;
	}

	public static DictionaryEditor<?> getRootEditor(BaseDictionaryElement<?> baseDictionaryElement) {
		IBaseRootElement<?> baseRootElement = baseDictionaryElement.getRootElement();

		if (baseRootElement instanceof DictionaryEditor) {
			return (DictionaryEditor<?>) baseRootElement;
		} else {
			throw new RuntimeException("'" + baseDictionaryElement.getModel().getName() + "' has no root element of type DictionaryEditor");
		}

	}

	public static BaseDictionaryControl<?, ?> getControl(TableRow<?, ?> tableRow, List<String> modelIds) {
		return getControl(tableRow.getColumns(), modelIds, 0);
	}

	private static BaseDictionaryControl<?, ?> getControl(List<BaseDictionaryControl<?, ?>> baseControls, List<String> modelIds, int level) {
		if (level < modelIds.size()) {
			for (BaseDictionaryControl<?, ?> baseControl : baseControls) {
				if (baseControl.getModel().getName().equals(modelIds.get(level)) && level == modelIds.size() - 1) {
					return baseControl;
				}
			}
		}

		return null;
	}

	public static List<String> getParentModelIds(IBaseDictionaryElement<? extends IBaseModel> baseDictionaryElement) {
		List<String> modelIds = new ArrayList<String>();

		IBaseDictionaryElement<? extends IBaseModel> currentBaseDictionaryElement = baseDictionaryElement;

		while (currentBaseDictionaryElement != null) {
			modelIds.add(0, currentBaseDictionaryElement.getModel().getName());
			currentBaseDictionaryElement = currentBaseDictionaryElement.getParent();
		}

		return modelIds;
	}

	public static void removeLeadingModelIds(List<String> modelIdsToRemove, List<String> modelIds) {

		Iterator<String> modelIdsToRemoveIterator = modelIdsToRemove.iterator();
		Iterator<String> modelIdsIterator = modelIds.iterator();

		while (modelIdsToRemoveIterator.hasNext() && modelIdsIterator.hasNext()) {
			String modelIdToRemove = modelIdsToRemoveIterator.next();
			String modelId = modelIdsIterator.next();

			if (modelId.equals(modelIdToRemove)) {
				modelIdsIterator.remove();
			}
		}

	}

}
