package io.pelle.mango.client.gwt.modules.dictionary.editor;

import io.pelle.mango.client.base.modules.dictionary.editor.IEditorUpdateListener;
import io.pelle.mango.client.gwt.modules.log.EndlessLogDataGrid;
import io.pelle.mango.client.web.modules.dictionary.editor.DictionaryEditor;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;

public class LogPopupPanel extends PopupPanel implements IEditorUpdateListener {

	private DictionaryEditor<?> dictionaryEditor;

	private EndlessLogDataGrid logDataGrid;

	public static final String CSS_LEVEL_STYLE_PREFIX = "log-level-";

	public LogPopupPanel(DictionaryEditor<?> dictionaryEditor) {
		super();
		this.dictionaryEditor = dictionaryEditor;

		logDataGrid = new EndlessLogDataGrid();
		logDataGrid.setWidth(Window.getClientWidth() / 3 + "px");
		logDataGrid.setHeight(Window.getClientHeight() / 2 + "px");
		add(logDataGrid);

		dictionaryEditor.addUpdateListener(this);
		onUpdate();
	}

	@Override
	public void show() {
		super.show();
	}

	@Override
	public void onUpdate() {
		logDataGrid.setReferencee(dictionaryEditor.getVO());
	}
}
