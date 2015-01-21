package io.pelle.mango.client.gwt.modules.dictionary;

import com.google.gwt.user.cellview.client.CellTable;

public interface BaseCellTableResources extends CellTable.Resources {

	/**
	 * The styles applied to the table.
	 */
	interface TableStyle extends CellTable.Style {
	}

	@Override
	@Source({ CellTable.Style.DEFAULT_CSS, "io/pelle/mango/client/gwt/modules/dictionary/mango-celltable.css" })
	TableStyle cellTableStyle();

}