package io.pelle.mango.client.gwt.modules.dictionary;

import com.google.gwt.user.cellview.client.DataGrid;

public interface BaseDataGridResources extends DataGrid.Resources {

	interface DataGridStyle extends DataGrid.Style {
	}

	@Override
	@Source({ DataGrid.Style.DEFAULT_CSS, "io/pelle/mango/client/gwt/modules/dictionary/mango-datagrid.css" })
	public DataGrid.Style dataGridStyle();

}