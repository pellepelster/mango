package io.pelle.mango.client.gwt.utils;

import io.pelle.mango.client.base.modules.dictionary.container.IBaseTable;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.GwtStyles;

import com.google.common.base.Objects;
import com.google.gwt.user.cellview.client.RowStyles;

public class MangoRowStyles<VOType extends IBaseVO> implements RowStyles<IBaseTable.ITableRow<VOType>> {

	@SuppressWarnings("rawtypes")
	private static MangoRowStyles DEFAULT = new MangoRowStyles();

	@SuppressWarnings("unchecked")
	public static <VOType extends IBaseVO> MangoRowStyles<VOType> getDefault() {
		return DEFAULT;
	}

	private MangoRowStyles() {
	}

	@Override
	public String getStyleNames(IBaseTable.ITableRow<VOType> row, int rowIndex) {

		String rowStyle = "";

		rowStyle += Objects.firstNonNull(row.getStyleNames(), "");

		if (rowIndex % 2 == 0) {
			rowStyle += " " + GwtStyles.TALE_ROW_EVEN;
		} else {
			rowStyle += " " + GwtStyles.TALE_ROW_UNEVEN;
		}

		return rowStyle;
	}

}
