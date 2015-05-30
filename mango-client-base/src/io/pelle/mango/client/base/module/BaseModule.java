package io.pelle.mango.client.base.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Splitter;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class BaseModule implements IModule {
	private final List<IModuleUpdateListener> updateListeners = new ArrayList<IModuleUpdateListener>();

	private final AsyncCallback<IModule> moduleCallback;

	protected final Map<String, Object> parameters;

	public static final String MODULE_COUNTER_PARAMETER_ID = "moduleCounter";

	public static final String MODULE_TITLE_PARAMETER_ID = "moduleTitle";

	private String moduleUrl;

	private String moduleId;

	protected AsyncCallback<IModule> getModuleCallback() {
		return this.moduleCallback;
	}

	@Override
	public void onClose() {
	}

	public BaseModule(String moduleId, String moduleUrl, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters) {
		this.moduleId = moduleId;
		this.moduleUrl = moduleUrl;
		this.moduleCallback = moduleCallback;
		this.parameters = parameters;

		parseUrl(moduleUrl);
	}

	public void addUpdateListener(IModuleUpdateListener updateListener) {
		this.updateListeners.add(updateListener);
	}

	private void parseUrl(String moduleUrl) {
		Map<String, String> urlSegments = Splitter.on("&").withKeyValueSeparator("=").split(moduleUrl);
		this.parameters.putAll(urlSegments);
	}

	public Map<String, Object> getParameters() {
		return this.parameters;
	}

	public boolean hasParameter(String parameterName) {
		return this.parameters.containsKey(parameterName);
	}

	public Object getParameter(String parameterName) {
		return this.parameters.get(parameterName);
	}

	public String getStringParameter(String parameterName, String defaultString) {
		if (hasParameter(parameterName)) {
			return this.parameters.get(parameterName).toString();
		} else {
			return defaultString;
		}
	}

	public String getStringParameter(String parameterName) {
		if (hasParameter(parameterName)) {
			return this.parameters.get(parameterName).toString();
		} else {
			return null;
		}
	}

	@Override
	public String getTitle() {
		if (hasParameter(MODULE_TITLE_PARAMETER_ID)) {
			return this.parameters.get(MODULE_TITLE_PARAMETER_ID).toString();
		} else {
			return getModuleUrl();
		}
	}

	@Override
	public int getOrder() {
		if (hasParameter(MODULE_COUNTER_PARAMETER_ID)) {
			return Integer.parseInt(this.parameters.get(MODULE_COUNTER_PARAMETER_ID).toString());
		} else {
			throw new RuntimeException("module parameter '" + MODULE_COUNTER_PARAMETER_ID + "' not found");
		}

	}

	@Override
	public void updateUrl(String moduleUrl) {
		parseUrl(moduleUrl);
		fireUpdateListeners();
	}

	protected void fireUpdateListeners() {
		for (IModuleUpdateListener updateListener : this.updateListeners) {
			updateListener.onUpdate();
		}
	}

	@Override
	public boolean isInstanceOf(String moduleUrl) {
		return this.moduleId.equals(ModuleUtils.getModuleId(moduleUrl));
	}

	@Override
	public String getHelpText() {
		return null;
	}

	@Override
	public String getModuleUrl() {
		return this.moduleUrl;
	}

}
