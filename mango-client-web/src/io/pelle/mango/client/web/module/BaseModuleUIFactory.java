package io.pelle.mango.client.web.module;

import io.pelle.mango.client.base.layout.IModuleUI;
import io.pelle.mango.client.base.module.ModuleUtils;

public abstract class BaseModuleUIFactory<ContainerType, ModuleType> implements IModuleUIFactory<ContainerType, ModuleType> {
	private final String[] uiModuleIds;

	public BaseModuleUIFactory(String[] uiModuleIds) {
		super();
		this.uiModuleIds = uiModuleIds;
	}

	@Override
	public boolean supports(String moduleUrl) {
		for (String uiModuleId : this.uiModuleIds) {
			if (supports(moduleUrl, uiModuleId)) {
				return true;
			}
		}

		return false;
	}

	public boolean supports(String moduleUrl, String uiModuleId) {
		return (uiModuleId.equals(ModuleUtils.getUrlParameter(moduleUrl, IModuleUI.UI_MODULE_ID_PARAMETER_NAME)));
	}

}
