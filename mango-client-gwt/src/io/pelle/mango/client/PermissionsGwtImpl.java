
package io.pelle.mango.client;

import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;

import io.pelle.mango.client.gwt.utils.CustomGwtComposite;
import io.pelle.mango.client.web.modules.dictionary.container.IContainer;

@CustomGwtComposite("permissions")
public class PermissionsGwtImpl implements IContainer<Panel> {

	private Div panel = new Div();

	public PermissionsGwtImpl(PermissionsImpl container) {
		super();
		panel.add(new HTML("sss"));
	}

	@Override
	public Panel getContainer() {
		return panel;
	}

}
