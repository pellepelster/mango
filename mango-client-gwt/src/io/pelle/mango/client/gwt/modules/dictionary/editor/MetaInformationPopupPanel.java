package io.pelle.mango.client.gwt.modules.dictionary.editor;

import io.pelle.mango.client.base.db.vos.IInfoVOEntity;
import io.pelle.mango.client.base.modules.dictionary.model.IDictionaryModel;
import io.pelle.mango.client.base.modules.dictionary.model.VOMetaModelProvider;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.editor.DictionaryEditor;

import java.util.Date;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.PopupPanel;

public class MetaInformationPopupPanel extends PopupPanel {

	public static final String DEFAULT_NULL_VALUE = "-";

	public interface HtmlLabelTemplate extends SafeHtmlTemplates {

		@Template("<strong>{0}</strong>:")
		SafeHtml labelText(String labelText);
	}

	private HtmlLabelTemplate TEMPLATE = GWT.create(HtmlLabelTemplate.class);

	private FlexTable grid;

	private DictionaryEditor<?> dictionaryEditor;

	public MetaInformationPopupPanel(DictionaryEditor<?> dictionaryEditor) {
		super();
		this.dictionaryEditor = dictionaryEditor;

		grid = new FlexTable();

		int row = 0;
		grid.setHTML(row, 0, TEMPLATE.labelText(MangoClientWeb.MESSAGES.dictionaryInfoCreateDate()));
		grid.setHTML(row, 1, formatLabelValue(null));

		row++;
		grid.setHTML(row, 0, TEMPLATE.labelText(MangoClientWeb.MESSAGES.dictionaryInfoCreateUser()));
		grid.setHTML(row, 1, formatLabelValue(null));

		row++;
		grid.setHTML(row, 0, TEMPLATE.labelText(MangoClientWeb.MESSAGES.dictionaryInfoUpdateDate()));
		grid.setHTML(row, 1, formatLabelValue(null));

		row++;
		grid.setHTML(row, 0, TEMPLATE.labelText(MangoClientWeb.MESSAGES.dictionaryInfoUpdateUser()));
		grid.setHTML(row, 1, formatLabelValue(null));

		row++;
		grid.getFlexCellFormatter().setColSpan(row, 0, 2);
		Anchor anchor = new Anchor(MangoClientWeb.MESSAGES.entityApi(), com.google.gwt.core.client.GWT.getModuleBaseURL() + "../remote/entity/"
				+ VOMetaModelProvider.getEntityDescriptor((IDictionaryModel) dictionaryEditor.getParent()).getName().toLowerCase() + "/api/index");
		anchor.setTarget("_blank");
		grid.setWidget(row, 0, anchor);

		add(grid);
		updateMetainformation(dictionaryEditor.getMetaInformation().get());
	}

	@Override
	public void show() {
		updateMetainformation(dictionaryEditor.getMetaInformation().get());
		super.show();
	}

	public void updateMetainformation(IInfoVOEntity infoVOEntity) {

		grid.setHTML(0, 1, formatLabelValue(infoVOEntity.getCreateDate(), MangoClientWeb.MESSAGES.undefined()));
		grid.setHTML(1, 1, formatLabelValue(infoVOEntity.getCreateUser(), MangoClientWeb.MESSAGES.undefined()));
		grid.setHTML(2, 1, formatLabelValue(infoVOEntity.getUpdateDate(), MangoClientWeb.MESSAGES.undefined()));
		grid.setHTML(3, 1, formatLabelValue(infoVOEntity.getUpdateUser(), MangoClientWeb.MESSAGES.undefined()));
	}

	public static String formatLabelValue(Object labelValue, String defaultNullValue) {

		String formattedValue = null;

		if (labelValue == null) {
			formattedValue = defaultNullValue;
		} else if (labelValue instanceof Date) {
			formattedValue = DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_SHORT).format((Date) labelValue);
		} else {
			formattedValue = labelValue.toString();
		}

		return formattedValue;
	}

	public static String formatLabelValue(Object labelValue) {
		return formatLabelValue(labelValue, DEBUG_ID_PREFIX);
	}
}
