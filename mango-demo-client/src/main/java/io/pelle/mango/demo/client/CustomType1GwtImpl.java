
package io.pelle.mango.demo.client;

import org.gwtbootstrap3.client.ui.html.Div;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Panel;

import io.pelle.mango.client.base.modules.dictionary.IUpdateListener;
import io.pelle.mango.client.gwt.utils.CustomGwtComposite;
import io.pelle.mango.client.web.modules.dictionary.container.IContainer;

@CustomGwtComposite("customtype1")
public class CustomType1GwtImpl implements IContainer<Panel>, ClickHandler, IUpdateListener {

	private Div panel = new Div();

	public CustomType1GwtImpl(CustomType1Impl container) {
		super();
	}

	@Override
	public void onUpdate() {
	}

	@Override
	public void onClick(ClickEvent event) {
	}

	@Override
	public Panel getContainer() {
		return panel;
	}

}
