package io.pelle.mango.client.base.module;

import io.pelle.mango.client.base.layout.IModuleUI;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

public abstract class ModuleUtils {
	public static final String getBaseModuleUrl(String moduleId) {
		return IModule.MODULE_ID_PARAMETER_NAME + "=" + moduleId;
	}

	public static final String getBaseUIModuleUrl(String uiModuleId) {
		return IModuleUI.UI_MODULE_ID_PARAMETER_NAME + "=" + uiModuleId;
	}

	public static boolean urlContainsModuleId(String moduleUrl, String moduleId) {
		return moduleId.equals(getModuleId(moduleUrl));
	}

	public static String getModuleId(String moduleUrl) {
		return getUrlParameter(moduleUrl, IModule.MODULE_ID_PARAMETER_NAME);
	}

	public static String getUIModuleId(String moduleUrl) {
		return getUrlParameter(moduleUrl, IModuleUI.UI_MODULE_ID_PARAMETER_NAME);
	}

	public static String getUrlParameter(String moduleUrl, String parameterName) {
		Map<String, String> urlSegments = getUrlParametersInternal(moduleUrl);
		return urlSegments.get(parameterName);
	}

	public static boolean hasUrlParameter(String moduleUrl, String parameterName) {
		return getUrlParameter(moduleUrl, parameterName) != null;
	}

	public static Long getLongUrlParameter(String moduleUrl, String parameterName) {
		String parameter = getUrlParameter(moduleUrl, parameterName);

		try {
			return Long.parseLong(parameter);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static boolean hasLongUrlParameter(String moduleUrl, String parameterName) {
		return getLongUrlParameter(moduleUrl, parameterName) != null;
	}

	public static String concatenate(String moduleUrl1, String moduleUrl2) {
		return Joiner.on("&").join(moduleUrl1, moduleUrl2);
	}

	private static Map<String, String> getUrlParametersInternal(String moduleUrl) {
		return Splitter.on("&").withKeyValueSeparator("=").split(moduleUrl);
	}

	public static Map<String, Object> getUrlParameters(String moduleUrl) {
		Map<String, Object> result = new HashMap<String, Object>();

		result.putAll(Splitter.on("&").withKeyValueSeparator("=").split(moduleUrl));

		return result;
	}
}