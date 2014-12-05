package io.pelle.mango.client.gwt.modules.dictionary.editor;

import io.pelle.mango.client.gwt.modules.log.EndlessDataGrid;
import io.pelle.mango.client.gwt.modules.log.LogDataGridCallback;
import io.pelle.mango.client.gwt.modules.log.LogModuleUI;
import io.pelle.mango.client.log.LogEntryVO;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.editor.DictionaryEditor;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;

public class LogPopupPanel extends PopupPanel {

	private DictionaryEditor<?> dictionaryEditor;

	public LogPopupPanel(DictionaryEditor<?> dictionaryEditor) {
		super();
		this.dictionaryEditor = dictionaryEditor;

		EndlessDataGrid<LogEntryVO> dataGrid = new EndlessDataGrid<LogEntryVO>();

		dataGrid.setWidth(Window.getClientWidth() / 3 + "px");
		dataGrid.setHeight(Window.getClientHeight() / 2 + "px");

		dataGrid.setDataGridCallback(LogDataGridCallback.createWithReference(dictionaryEditor.getVO()));
		dataGrid.addColumn(LogModuleUI.LOG_TIMESTAMP_COLUMN, MangoClientWeb.MESSAGES.logTimestampTitle());
		dataGrid.addColumn(LogModuleUI.LOG_MESSAGE_COLUMN, MangoClientWeb.MESSAGES.logMessageTitle());

		add(dataGrid);
	}

	@Override
	public void show() {
		super.show();
	}

}
