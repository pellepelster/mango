
package io.pelle.mango.test.client;

public class Navigation extends io.pelle.mango.client.base.modules.navigation.NavigationTreeElement {


	public io.pelle.mango.test.client.Dictionary1 DICTIONARY1 = new io.pelle.mango.test.client.Dictionary1();

	public Navigation() {
		super("Navigation");

		getChildren().add(DICTIONARY1);
		
		setLabel("Navigation");
		
		setModuleLocator("moduleUIName=ModuleNavigationOverview&" + io.pelle.mango.client.modules.BaseModuleNavigationModule.NAVIGATIONTREEELEMENTNAME_PARAMETER_ID + "=Navigation");
	}

}
