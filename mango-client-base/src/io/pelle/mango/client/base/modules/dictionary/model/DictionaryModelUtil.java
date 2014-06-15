/**
 * Copyright (c) 2013 Christian Pelster.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Christian Pelster - initial API and implementation
 */
package io.pelle.mango.client.base.modules.dictionary.model;

import io.pelle.mango.client.base.modules.dictionary.controls.IBaseLookupControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IAssignmentTableModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IBaseContainerModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.ICompositeModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IReferenceControlModel;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;

public final class DictionaryModelUtil {

	public static String getEditorLabel(IBaseControlModel baseControlModel) {
		return Objects.firstNonNull(baseControlModel.getEditorLabel(), getLabel(baseControlModel));
	}

	public static String getFilterLabel(IBaseControlModel baseControlModel) {
		return Objects.firstNonNull(baseControlModel.getFilterLabel(), getLabel(baseControlModel));
	}

	public static String getColumnLabel(IBaseControlModel baseControlModel) {
		return Objects.firstNonNull(baseControlModel.getColumnLabel(), getLabel(baseControlModel));
	}

	public static String getLabel(IBaseControlModel baseControlModel) {
		return Objects.firstNonNull(baseControlModel.getLabel(), baseControlModel.getName());
	}

	public static String getEditorLabel(IDictionaryModel dictionaryModel) {
		return Objects.firstNonNull(dictionaryModel.getEditorModel().getLabel(), getLabel(dictionaryModel));
	}

	public static String getLabel(IDictionaryModel dictionaryModel) {
		return Objects.firstNonNull(dictionaryModel.getLabel(), dictionaryModel.getName());
	}

	public static List<IBaseControlModel> getLabelControlsWithFallback(IReferenceControlModel referenceControlModel, IDictionaryModel dictionaryModel) {
		if (!referenceControlModel.getLabelControls().isEmpty()) {
			return referenceControlModel.getLabelControls();
		} else {
			return getLabelControlsWithFallback(dictionaryModel);
		}
	}

	public static List<IBaseControlModel> getLabelControlsWithFallback(IDictionaryModel dictionaryModel) {
		if (!dictionaryModel.getLabelControls().isEmpty()) {
			return dictionaryModel.getLabelControls();
		} else {
			return dictionaryModel.getSearchModel().getResultModel().getControls();
		}
	}

	public static void createReferenceContainerList(IBaseContainerModel parentContainerModel, List<String> dictionaryNames) {

		String dictionaryName = null;

		if (parentContainerModel instanceof IAssignmentTableModel) {
			IAssignmentTableModel assignmentTableModel = (IAssignmentTableModel) parentContainerModel;
			dictionaryName = assignmentTableModel.getDictionaryName();
		}

		if (dictionaryName != null && !dictionaryNames.contains(dictionaryName)) {
			dictionaryNames.add(dictionaryName);
		}

		for (IBaseContainerModel baseContainerModel : parentContainerModel.getChildren()) {
			createReferenceContainerList(baseContainerModel, dictionaryNames);
		}
	}

	public static void createReferenceControlsList(IBaseContainerModel parentContainerModel, List<String> dictionaryNames) {

		createReferenceControlsList(parentContainerModel.getControls(), dictionaryNames);

		for (IBaseContainerModel baseContainerModel : parentContainerModel.getChildren()) {
			createReferenceControlsList(baseContainerModel, dictionaryNames);
		}
	}

	public static void createReferenceControlsList(List<IBaseControlModel> baseControlModels, List<String> dictionaryNames) {
		String dictionaryName = null;

		for (IBaseControlModel baseControlModel : baseControlModels) {
			if (baseControlModel instanceof IBaseLookupControlModel) {
				IBaseLookupControlModel baseLookupControlModel = (IBaseLookupControlModel) baseControlModel;
				dictionaryName = baseLookupControlModel.getDictionaryName();

				if (dictionaryName != null && !dictionaryNames.contains(dictionaryName)) {
					dictionaryNames.add(dictionaryName);
				}

			}
		}

	}

	public static List<String> getReferencedDictionaryModels(ICompositeModel parentCompositeModel) {
		List<String> result = new ArrayList<String>();

		createReferenceContainerList(parentCompositeModel, result);
		createReferenceControlsList(parentCompositeModel, result);

		return result;
	}

	public static List<String> getReferencedDictionaryModels(IDictionaryModel dictionaryModel) {
		List<String> result = new ArrayList<String>();

		createReferenceControlsList(dictionaryModel.getLabelControls(), result);

		createReferenceControlsList(dictionaryModel.getSearchModel().getResultModel().getControls(), result);

		return result;
	}

	public static IBaseModel getRootModel(IBaseModel baseModel) {
		IBaseModel result = null;

		while (baseModel.getParent() != null) {
			result = baseModel;
		}

		return result;
	}

	public static String getDebugId(IBaseControlModel baseControlModel) {
		String debugId = "";
		String delimiter = "";

		IBaseModel baseModel = baseControlModel;

		do {
			if (!ICompositeModel.ROOT_COMPOSITE_NAME.equals(baseModel.getName())) {
				debugId = baseModel.getName() + delimiter + debugId;
				delimiter = "-";
			}

			baseModel = baseModel.getParent();
		} while (baseModel != null);

		return debugId;
	}

	private DictionaryModelUtil() {
	}

}
