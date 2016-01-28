package io.pelle.mango.client.base.modules.dictionary;

import io.pelle.mango.client.base.modules.dictionary.model.BaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.ICompositeModel;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;

public class BaseDictionaryElementUtil {

	public static String getModelId(BaseModel<?> baseModel) {
		List<String> modelIds = getParentModelIds(baseModel);
		return Joiner.on("/").skipNulls().join(modelIds).toString();
	}

	public static String getModelId(IBaseModel baseModel) {
		List<String> modelIds = getParentModelIds(baseModel);
		return Joiner.on("/").skipNulls().join(modelIds).toString();
	}

	public static List<String> getParentModelIds(BaseModel<?> baseModel) {

		List<String> modelIds = new ArrayList<String>();

		IBaseModel currentModel = baseModel;

		while (currentModel != null) {
			if (!currentModel.getName().equals(ICompositeModel.ROOT_COMPOSITE_NAME))
				modelIds.add(0, currentModel.getName());
			currentModel = currentModel.getParent();
		}

		return modelIds;
	}

	public static List<String> getParentModelIds(IBaseModel baseModel) {
		List<String> modelIds = new ArrayList<String>();

		IBaseModel currentModel = baseModel;

		while (currentModel != null) {
			if (!ICompositeModel.ROOT_COMPOSITE_NAME.equals(currentModel.getName())) {
				modelIds.add(0, currentModel.getName());
			}

			currentModel = currentModel.getParent();
		}

		return modelIds;
	}
}
