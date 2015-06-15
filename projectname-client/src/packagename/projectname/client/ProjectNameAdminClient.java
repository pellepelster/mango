package packagename.projectname.client;

import io.pelle.mango.client.gwt.MangoBootstrapLayoutFactory;

import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.module.ModuleHandler;
import io.pelle.mango.client.web.modules.navigation.ModuleNavigationModule;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel.Direction;
import packagename.projectname.client.ProjectNameClientConfiguration;

public class ProjectNameAdminClient implements EntryPoint {
	private static final ProjectNameResources RESOURCES = GWT
			.create(ProjectNameResources.class);

	/** {@inheritDoc} */
	@Override
	public void onModuleLoad() {
		
		MangoBootstrapLayoutFactory gwtLayoutFactory = new MangoBootstrapLayoutFactory();
		MangoClientWeb.getInstance().setLayoutFactory(gwtLayoutFactory);
		
		init();
		
		ModuleHandler.getInstance().startUIModule(ModuleNavigationModule.NAVIGATION_UI_MODULE_LOCATOR, Direction.WEST.toString());
	}

	public void init() {
		ProjectNameClientConfiguration.registerAll();
	}
}