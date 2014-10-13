package io.pelle.mango.demo.client;

import io.pelle.mango.client.gwt.GWTLayoutFactory;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.module.ModuleHandler;
import io.pelle.mango.client.web.modules.navigation.ModuleNavigationModule;
import io.pelle.mango.test.client.MangoDemoClientConfiguration;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel.Direction;

public class DemoClient implements EntryPoint {

	private static final DemoResources RESOURCES = GWT.create(DemoResources.class);

	/** {@inheritDoc} */
	@Override
	public void onModuleLoad() {
		try {
			MangoClientWeb.getInstance().setLayoutFactory(new GWTLayoutFactory(Unit.PX));

			init();
		} catch (Exception e) {
			GWT.log(e.getMessage(), e);
		}

		ModuleHandler.getInstance().startUIModule(ModuleNavigationModule.NAVIGATION_UI_MODULE_LOCATOR, Direction.WEST.toString());

	}

	public void init() {
		MangoDemoClientConfiguration.registerAll();

		// MangoDemoNavigationTree.ROOT.MASTERDATA.ADRESS.setImageResource(RESOURCES.address());

		// HierarchicalHookRegistry.getInstance().addActivationHook(TestClientHierarchicalConfiguration.ID,
		// new BaseActivationHook()
		// {
		// @Override
		// public void onActivate(DictionaryHierarchicalNodeVO
		// hierarchicalNodeVO)
		// {
		// if (hierarchicalNodeVO.getVoId() != null)
		// {
		// ModuleHandler.getInstance().startModule(TestModule1.MODULE_ID,
		// TestModule1.getParameterMap(hierarchicalNodeVO.getDictionaryName(),
		// hierarchicalNodeVO.getVoId()));
		// }
		// else
		// {
		// super.onActivate(hierarchicalNodeVO);
		// }
		// }
		// });
		// ModuleFactoryRegistry.getInstance().addModuleFactory(TestModule1.MODULE_ID,
		// new TestModule1Factory());
		// ModuleUIFactoryRegistry.getInstance().addModuleFactory(TestModule1.class,
		// new TestModule1UIFactory());
	}
}
