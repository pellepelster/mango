package io.pelle.mango.gwt.commons;

import io.pelle.mango.gwt.commons.toastr.Toastr;
import io.pelle.mango.gwt.commons.toastr.ToastrCallback;

import com.google.gwt.user.client.Window;

public class DemoCode {

	public void toastrDemoCode() {

		Toastr.setCloseButton(true);
		Toastr.setOnClick(new ToastrCallback() {
			@Override
			public void run() {
				Window.alert("notification closed");
			}
		});

		Toastr.info("Info", "My info notification");

	}

}
