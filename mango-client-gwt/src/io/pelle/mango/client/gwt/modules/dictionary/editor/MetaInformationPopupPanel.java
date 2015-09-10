package io.pelle.mango.client.gwt.modules.dictionary.editor;

import io.pelle.mango.client.base.db.vos.IInfoVOEntity;
import io.pelle.mango.client.base.modules.dictionary.model.IDictionaryModel;
import io.pelle.mango.client.base.modules.dictionary.model.VOMetaModelProvider;
import io.pelle.mango.client.base.vo.IEntityDescriptor;
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

	public MetaInformationPopupPanel(DictionaryEditor<?> dictionaryEditor, IDictionaryModel dictionaryModel) {
		super();
		this.dictionaryEditor = dictionaryEditor;
		IEntityDescriptor<?> entityDescriptor = VOMetaModelProvider.getValueObjectEntityDescriptor(dictionaryModel.getVOClass().getName()); 

		grid = new FlexTable();

		int row = 0;
		grid.setHTML(row, 0, TEMPLATE.labelText(MangoClientWeb.getInstance().getMessages().dictionaryInfoCreateDate()));
		grid.setHTML(row, 1, formatLabelValue(null));

		row++;
		grid.setHTML(row, 0, TEMPLATE.labelText(MangoClientWeb.getInstance().getMessages().dictionaryInfoCreateUser()));
		grid.setHTML(row, 1, formatLabelValue(null));

		row++;
		grid.setHTML(row, 0, TEMPLATE.labelText(MangoClientWeb.getInstance().getMessages().dictionaryInfoUpdateDate()));
		grid.setHTML(row, 1, formatLabelValue(null));

		row++;
		grid.setHTML(row, 0, TEMPLATE.labelText(MangoClientWeb.getInstance().getMessages().dictionaryInfoUpdateUser()));
		grid.setHTML(row, 1, formatLabelValue(null));

		row++;
		grid.setHTML(row, 0, TEMPLATE.labelText(MangoClientWeb.getInstance().getMessages().apiLabel()));
		
		Anchor anchor = new Anchor(MangoClientWeb.getInstance().getMessages().entityApi(capitalize(entityDescriptor.getName())), com.google.gwt.core.client.GWT.getModuleBaseURL() + "../remote/api/entity/"
				+ entityDescriptor.getName().toLowerCase() + "/index");
		anchor.setTarget("_blank");
		grid.setWidget(row, 1, anchor);

		add(grid);
		updateMetainformation(dictionaryEditor.getMetaInformation().get());
	}

	private String capitalize(String input) {
		return input.substring(0, 1).toUpperCase() + input.substring(1);
	}
	
	@Override
	public void show() {
		updateMetainformation(dictionaryEditor.getMetaInformation().get());
		super.show();
	}

	public void updateMetainformation(IInfoVOEntity infoVOEntity) {

		grid.setHTML(0, 1, formatLabelValue(infoVOEntity.getCreateDate(), MangoClientWeb.getInstance().getMessages().undefined()));
		grid.setHTML(1, 1, formatLabelValue(infoVOEntity.getCreateUser(), MangoClientWeb.getInstance().getMessages().undefined()));
		grid.setHTML(2, 1, formatLabelValue(infoVOEntity.getUpdateDate(), MangoClientWeb.getInstance().getMessages().undefined()));
		grid.setHTML(3, 1, formatLabelValue(infoVOEntity.getUpdateUser(), MangoClientWeb.getInstance().getMessages().undefined()));
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
