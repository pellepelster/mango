package io.pelle.mango.client.gwt.utils;

import org.gwtbootstrap3.client.ui.ListGroup;
import org.gwtbootstrap3.client.ui.ListGroupItem;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class ListGroupWithEmptyPlaceholder extends ListGroup {

	private final ListGroupItem NO_FILES_ENTRY;

	public ListGroupWithEmptyPlaceholder(String emptyPlaceholderText) {
		super();

		NO_FILES_ENTRY = new ListGroupItem();
		NO_FILES_ENTRY.add(new HTML(emptyPlaceholderText));
	}

	private void beforeAdd() {

		if (getChildren().contains(NO_FILES_ENTRY)) {
			super.remove(NO_FILES_ENTRY);

		}
	}

	private void afterRemove() {

		if (getChildren().size() == 0) {
			super.add(NO_FILES_ENTRY);

		}
	}

	@Override
	public void add(Widget child) {
		beforeAdd();
		super.add(child);
	}

	@Override
	public void insert(Widget child, int beforeIndex) {
		beforeAdd();
		super.insert(child, beforeIndex);
	}

	@Override
	public void add(IsWidget child) {
		beforeAdd();
		super.add(child);
	}

	@Override
	public boolean remove(IsWidget child) {
		boolean result = super.remove(child);

		afterRemove();

		return result;
	}

}
