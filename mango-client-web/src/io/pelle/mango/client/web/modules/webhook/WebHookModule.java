/**
 * Copyright (c) 2013 Christian Pelster.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Christian Pelster - initial API and implementation
 */
package io.pelle.mango.client.web.modules.webhook;

import io.pelle.mango.client.api.webhook.BaseWebhookModule;
import io.pelle.mango.client.api.webhook.WebhookDefinition;
import io.pelle.mango.client.base.module.IModule;
import io.pelle.mango.client.base.module.ModuleUtils;
import io.pelle.mango.client.web.MangoClientWeb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class WebHookModule extends BaseWebhookModule {

	public final static String MODULE_LOCATOR = ModuleUtils.getBaseModuleUrl(MODULE_ID);

	public final static String UI_MODULE_ID = MODULE_ID;

	public final static String UI_MODULE_LOCATOR = ModuleUtils.getBaseUIModuleUrl(UI_MODULE_ID);

	private List<WebhookDefinition> webHookDefinitions = new ArrayList<WebhookDefinition>();

	public WebHookModule(String moduleUrl, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters) {
		super(moduleUrl, moduleCallback, parameters);

		MangoClientWeb.getInstance().getRemoteServiceLocator().getWebhookService().getWebhookDefinitions(new AsyncCallback<List<WebhookDefinition>>() {

			@Override
			public void onSuccess(List<WebhookDefinition> result) {
				webHookDefinitions.addAll(result);
				getModuleCallback().onSuccess(WebHookModule.this);
			}

			@Override
			public void onFailure(Throwable caught) {
				getModuleCallback().onFailure(caught);
			}
		});
	}

	@Override
	public String getTitle() {
		return MangoClientWeb.MESSAGES.webHooksTitle();
	}

	@Override
	public boolean isInstanceOf(String moduleUrl) {
		return MODULE_ID.equals(ModuleUtils.getModuleId(moduleUrl));
	}

}
