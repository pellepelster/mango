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
package io.pelle.mango.client.web.modules.dictionary.layout;

import io.pelle.mango.client.base.MangoClientBase;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IBaseTableModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IEnumerationControlModel;

public final class WidthCalculationStrategy {
	private static WidthCalculationStrategy instance;

	public final static float CONTROL_COLUMN_FACTOR_DEFAULT = 1.1f;

	public final static int CONTROL_COLUMN_OFFSET_DEFAULT = 0;

	public final static float CONTROL_FACTOR_DEFAULT = 1.1f;

	public final static int CONTROL_OFFSET_DEFAULT = 0;

	public final static float TABLE_FACTOR_DEFAULT = 1.1f;

	public final static int TABLE_OFFSET_DEFAULT = 0;

	private float getWidth(int characters, boolean uppercase) {
		return getWidth(characters, uppercase, 1f);
	}

	private float getWidth(int characters, boolean uppercase, float factor) {
		float width = characters * MangoClientBase.getInstance().getAverageCharacterWidth(uppercase);

		return width * factor;
	}

	// public String getPxWidth(int characters, boolean uppercase, float factor)
	// {
	// return getWidth(characters, uppercase, factor) + "px";
	// }

	public static WidthCalculationStrategy getInstance() {
		if (instance == null) {
			instance = new WidthCalculationStrategy();
		}

		return instance;
	}

	private WidthCalculationStrategy() {
	}

	private boolean isUppercase(IBaseControlModel baseControlModel) {
		boolean uppercase = false;

		if (baseControlModel instanceof IEnumerationControlModel) {
			IEnumerationControlModel<?> enumerationControlModel = (IEnumerationControlModel) baseControlModel;

			String allEnumValues = "";
			for (String enumValue : enumerationControlModel.getEnumerationMap().values()) {
				allEnumValues += enumValue;
			}

			uppercase = allEnumValues.toUpperCase().equals(allEnumValues);

		}

		return uppercase;
	}

	private int addFactorAndOffset(float width, float factor, int offset) {
		return Math.round(width * factor + offset);
	}

	private float getControlWidthInternal(IBaseControlModel baseControlModel) {
		return getWidth(baseControlModel.getWidthHint(), isUppercase(baseControlModel));
	}

	private int getControlWidth(IBaseControlModel baseControlModel) {
		return addFactorAndOffset(getControlWidthInternal(baseControlModel), CONTROL_FACTOR_DEFAULT, CONTROL_OFFSET_DEFAULT);
	}

	public String getControlWidthCss(IBaseControlModel baseControlModel) {
		return getControlWidth(baseControlModel) + "px";
	}

	public int getControlColumnWidth(IBaseControlModel baseControlModel) {
		return addFactorAndOffset(getControlWidthInternal(baseControlModel), CONTROL_COLUMN_FACTOR_DEFAULT, CONTROL_COLUMN_OFFSET_DEFAULT);
	}

	private int getTableWidth(IBaseTableModel baseTableModel) {
		int width = 0;

		for (IBaseControlModel baseControlModel : baseTableModel.getControls()) {
			width += getControlColumnWidth(baseControlModel);
		}

		return addFactorAndOffset(width, TABLE_FACTOR_DEFAULT, TABLE_OFFSET_DEFAULT);
	}

	public String getTableWidthCss(IBaseTableModel baseTableModel) {
		return getTableWidth(baseTableModel) + "px";
	}

}
