package io.pelle.mango.demo.server;

import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.db.dao.IBaseEntityDAO;
import io.pelle.mango.demo.server.showcase.Country;
import io.pelle.mango.demo.server.test.Entity1;
import io.pelle.mango.gwt.commons.editableLabel.StringEditableLabel;

import org.junit.Ignore;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

public class DocumentationCode {

	@SuppressWarnings({ "null", "unused" })
	@Test
	@Ignore
	public void selectQuery2() {

		IBaseEntityDAO dao = null;

		SelectQuery<Country> query = SelectQuery.selectFrom(Country.class).
				where(Country.COUNTRYNAME.like("A%"));

		for (Country country : dao.filter(query)) {
			// do something
		}

	}

	@SuppressWarnings({ "null", "unused" })
	@Test
	@Ignore
	public void selectQuery3() {

		IBaseEntityDAO dao = null;

		SelectQuery<Country> query = SelectQuery.selectFrom(Country.class).
				where(Country.COUNTRYRATING.lessThan(5));

		for (Country country : dao.filter(query)) {
			// do something
		}

	}

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
