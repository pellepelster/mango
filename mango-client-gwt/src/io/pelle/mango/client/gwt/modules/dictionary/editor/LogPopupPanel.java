package io.pelle.mango.client.gwt.modules.dictionary.editor;

import io.pelle.mango.client.base.modules.dictionary.editor.IEditorUpdateListener;
import io.pelle.mango.client.gwt.modules.log.EndlessDataGrid;
import io.pelle.mango.client.gwt.modules.log.LogDataGridCallback;
import io.pelle.mango.client.gwt.modules.log.LogModuleUI;
import io.pelle.mango.client.log.LogEntryVO;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.editor.DictionaryEditor;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;

public class LogPopupPanel extends PopupPanel implements IEditorUpdateListener {

	private DictionaryEditor<?> dictionaryEditor;

	private EndlessDataGrid<LogEntryVO> dataGrid;
	
	public LogPopupPanel(DictionaryEditor<?> dictionaryEditor) {
		super();
		this.dictionaryEditor = dictionaryEditor;

		dataGrid = new EndlessDataGrid<LogEntryVO>();

		dataGrid.setWidth(Window.getClientWidth() / 3 + "px");
		dataGrid.setHeight(Window.getClientHeight() / 2 + "px");

		dictionaryEditor.addUpdateListener(this);
		dataGrid.addColumn(LogModuleUI.LOG_TIMESTAMP_COLUMN, MangoClientWeb.MESSAGES.logTimestampTitle());
		dataGrid.addColumn(LogModuleUI.LOG_MESSAGE_COLUMN, MangoClientWeb.MESSAGES.logMessageTitle());

		add(dataGrid);
		
		onUpdate();
	}

	@Override
	public void show() {
		super.show();
	}

	@Override
	public void onUpdate() {
		dataGrid.setDataGridCallback(LogDataGridCallback.createWithReference(dictionaryEditor.getVO()));
	}

}
