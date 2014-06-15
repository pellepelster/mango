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
package io.pelle.mango.client.web.modules.hierarchical;

import io.pelle.mango.client.DictionaryHierarchicalNodeVO;
import io.pelle.mango.client.IHierachicalServiceGWTAsync;
import io.pelle.mango.client.base.db.vos.IHierarchicalVO;
import io.pelle.mango.client.base.module.IModule;
import io.pelle.mango.client.base.module.ModuleUtils;
import io.pelle.mango.client.base.modules.dictionary.hooks.DictionaryHookRegistry;
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelProvider;
import io.pelle.mango.client.base.modules.dictionary.model.IDictionaryModel;
import io.pelle.mango.client.base.modules.hierarchical.HierarchicalConfigurationVO;
import io.pelle.mango.client.base.util.CollectionUtils;
import io.pelle.mango.client.base.util.SimpleCallback;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.modules.BaseModuleHierarchicalTreeModule;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.editor.DictionaryEditorModuleFactory;
import io.pelle.mango.client.web.modules.hierarchical.hooks.HierarchicalHookRegistry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class HierarchicalTreeModule extends BaseModuleHierarchicalTreeModule {
	public final static String MODULE_LOCATOR = ModuleUtils.getBaseModuleUrl(MODULE_ID);

	public final static String UI_MODULE_ID = MODULE_ID;

	private final static String UI_MODULE_LOCATOR = ModuleUtils.getBaseUIModuleUrl(UI_MODULE_ID);

	public final static String getUIModuleLocator(String hierarchicalTreeId) {
		return UI_MODULE_LOCATOR + "&" + HIERARCHICALTREEID_PARAMETER_ID + "=" + hierarchicalTreeId;
	}

	private HierarchicalConfigurationVO hierarchicalConfiguration;

	private final SimpleCallback<DictionaryHierarchicalNodeVO> nodeActivatedHandler = new SimpleCallback<DictionaryHierarchicalNodeVO>() {
		@Override
		public void onCallback(DictionaryHierarchicalNodeVO hierarchicalNodeVO) {
			if (HierarchicalHookRegistry.getInstance().hasActivationHook(getHierarchicalTreeId())) {
				HierarchicalHookRegistry.getInstance().getActivationHook(getHierarchicalTreeId()).onActivate(hierarchicalNodeVO);
			} else {
				openModuleForNode(hierarchicalNodeVO);
			}
		}

	};

	public HierarchicalTreeModule(String moduleUrl, final AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters) {
		super(moduleUrl, moduleCallback, parameters);

		final IHierachicalServiceGWTAsync hierachicalService = MangoClientWeb.getInstance().getRemoteServiceLocator().getHierachicalService();

		hierachicalService.getConfigurationById(getHierarchicalTreeId(), new AsyncCallback<HierarchicalConfigurationVO>() {

			/** {@inheritDoc} */
			@Override
			public void onFailure(Throwable caught) {
				moduleCallback.onFailure(caught);
			}

			@Override
			public void onSuccess(HierarchicalConfigurationVO result) {
				HierarchicalTreeModule.this.hierarchicalConfiguration = result;

				Set<String> dictionaryNames = new HashSet<String>();
				for (Map.Entry<String, List<String>> entry : HierarchicalTreeModule.this.hierarchicalConfiguration.getDictionaryHierarchy().entrySet()) {
					dictionaryNames.add(entry.getKey());

					for (String dictionaryName : entry.getValue()) {
						if (dictionaryName != null) {
							dictionaryNames.addAll(entry.getValue());
						}
					}
				}

				for (String dictionaryId : HierarchicalTreeModule.this.hierarchicalConfiguration.getDictionaryIds()) {
					List<String> childDictionaryIds = HierarchicalTreeModule.this.hierarchicalConfiguration.getChildDictionaryIds(dictionaryId);

					if (!childDictionaryIds.isEmpty()) {
						List<IDictionaryModel> childDictionaries = DictionaryModelProvider.getDictionaries(childDictionaryIds);
						DictionaryHookRegistry.getInstance().addEditorHook(dictionaryId, new HierarchicalEditorHook<IBaseVO>(childDictionaries));
					}
				}

				getModuleCallback().onSuccess(HierarchicalTreeModule.this);
			}
		});

	}

	public HierarchicalConfigurationVO getHierarchicalConfiguration() {
		return this.hierarchicalConfiguration;
	}

	public SimpleCallback<DictionaryHierarchicalNodeVO> getNodeActivatedHandler() {
		return this.nodeActivatedHandler;
	}

	@Override
	public boolean isInstanceOf(String moduleUrl) {
		return MODULE_ID.equals(ModuleUtils.getModuleId(moduleUrl)) && getHierarchicalTreeId().equals(ModuleUtils.getUrlParameter(moduleUrl, HIERARCHICALTREEID_PARAMETER_ID));
	}

	public static void openModuleForNode(DictionaryHierarchicalNodeVO hierarchicalNodeVO) {
		if (hierarchicalNodeVO.getVoId() == null) {
			HashMap<String, Object> parameters = CollectionUtils.getMap(IHierarchicalVO.FIELD_PARENT_CLASSNAME.getAttributeName(), hierarchicalNodeVO.getParentClassName(), IHierarchicalVO.FIELD_PARENT_ID.getAttributeName(),
					hierarchicalNodeVO.getParentVOId());

			DictionaryEditorModuleFactory.openEditor(hierarchicalNodeVO.getDictionaryName(), parameters);
		} else {
			DictionaryEditorModuleFactory.openEditorForId(hierarchicalNodeVO.getDictionaryName(), hierarchicalNodeVO.getVoId());
		}
	}

}
