package io.pelle.mango.client.web.test.sync;

import io.pelle.mango.client.base.layout.IModuleUI;
import io.pelle.mango.client.base.modules.dictionary.model.editor.EditorModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.module.ModuleHandler;
import io.pelle.mango.client.web.modules.dictionary.editor.DictionaryEditorModule;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;

import java.util.HashMap;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.SettableFuture;
import com.google.gwt.user.client.rpc.AsyncCallback;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class MangoClientSyncWebTest {

	private static MangoClientSyncWebTest instance;

	private SyncLayoutFactory layoutFactory;

	private MangoClientSyncWebTest() {
		this.layoutFactory = new SyncLayoutFactory();
		MangoClientWeb.getInstance().setLayoutFactory(this.layoutFactory);
	}

	public static MangoClientSyncWebTest getInstance() {
		if (instance == null) {
			instance = new MangoClientSyncWebTest();
		}

		return instance;
	}

	public <T extends IModuleUI> void startUIModule(final String moduleLocator, Class<T> moduleType, final String location, final AsyncCallback<T> asyncCallback) {
		ModuleHandler.getInstance().startUIModule(moduleLocator, null, new HashMap<String, Object>(), Optional.fromNullable((AsyncCallback<IModuleUI>) asyncCallback));
	}

	public void deleteAllVOs(Class<? extends IBaseVO> voClass, final AsyncCallback asyncCallback) {
		MangoClientWeb.getInstance().getRemoteServiceLocator().getBaseEntityService().deleteAll(voClass.getName(), new BaseErrorAsyncCallback() {
			@Override
			public void onSuccess(Object result) {
				asyncCallback.onSuccess(null);
			}
		});
	}

	public <VOTYPE extends IBaseVO> DictionaryEditorModuleSyncTestUI<VOTYPE> openEditor(final EditorModel<VOTYPE> baseModel) {

		final SettableFuture<IModuleUI> future = SettableFuture.<IModuleUI> create();

		AsyncCallback<IModuleUI> callback = new AsyncCallback<IModuleUI>() {

			@Override
			public void onFailure(Throwable caught) {
				future.setException(caught);
			}

			@Override
			public void onSuccess(IModuleUI result) {
				future.set(result);
			}
		};

		ModuleHandler.getInstance().startUIModule(DictionaryEditorModule.getModuleUrlForDictionary(baseModel.getParent().getName()), null, new HashMap<String, Object>(), Optional.of(callback));

		try {
			return (DictionaryEditorModuleSyncTestUI) future.get();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
