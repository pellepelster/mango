package io.pelle.mango.demo.server;

import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.db.dao.IBaseEntityDAO;
import io.pelle.mango.demo.server.test.Entity1;
import io.pelle.mango.gwt.commons.StringEditableLabel;

import org.junit.Ignore;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

public class DocumentationCode {

	@Test
	@Ignore
	public void selectQuery1() {

		IBaseEntityDAO dao = null;

		SelectQuery<Entity1> query = SelectQuery.selectFrom(Entity1.class).where(Entity1.STRINGDATATYPE1.eq("abc"));

		for (Entity1 entity1 : dao.filter(query)) {
			// do something
		}

	}

	@Test
	@Ignore
	public void editableLabel() {

		StringEditableLabel editableLabel = new StringEditableLabel();

		editableLabel.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				GWT.log("value changed to: '" + event.getValue() + "'");
			}
		});

		// CSS styles for the control
		editableLabel.addControlStyle("controlCSSStyle");

		// CSS styles for the OK/Cancel button
		editableLabel.addButtonStyle("buttonCSSStyle");

		// CSS class to be added if the control contains erroneous input
		editableLabel.setErrorStyle("errorCSSStyle");

	}

}
