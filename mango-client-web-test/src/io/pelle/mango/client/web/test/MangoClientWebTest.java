package io.pelle.mango.client.web.test;

import io.pelle.mango.client.base.layout.IModuleUI;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.module.ModuleHandler;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class MangoClientWebTest {

	private static MangoClientWebTest instance;

	private JunitLayoutFactory junitLayoutFactory;

	private MangoClientWebTest() {
		this.junitLayoutFactory = new JunitLayoutFactory();
		MangoClientWeb.getInstance().setLayoutFactory(this.junitLayoutFactory);
	}

	public static MangoClientWebTest getInstance() {
		if (instance == null) {
			instance = new MangoClientWebTest();
		}

		return instance;
	}

	public <T extends IModuleUI> void startUIModule(final String moduleLocator, Class<T> moduleType, final String location, final AsyncCallback<T> asyncCallback) {
		ModuleHandler.getInstance().startUIModule(moduleLocator, null, new HashMap<String, Object>(), (AsyncCallback<IModuleUI>) asyncCallback);
	}

	public void deleteAllVOs(Class<? extends IBaseVO> voClass, final AsyncCallback asyncCallback) {
		MangoClientWeb.getInstance().getRemoteServiceLocator().getBaseEntityService().deleteAll(voClass.getName(), new BaseErrorAsyncCallback() {
			@Override
			public void onSuccess(Object result) {
				asyncCallback.onSuccess(null);
			}
		});
	}

}
