package io.pelle.mango.client.web.test;

import io.pelle.mango.client.base.db.vos.UUID;
import io.pelle.mango.client.base.layout.IModuleUI;
import io.pelle.mango.client.base.modules.dictionary.model.BaseModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.module.ModuleHandler;
import io.pelle.mango.client.web.modules.dictionary.editor.DictionaryEditorModule;
import io.pelle.mango.client.web.modules.dictionary.search.DictionarySearchModule;
import io.pelle.mango.client.web.test.modules.dictionary.DictionaryEditorModuleTestUIAsyncHelper;
import io.pelle.mango.client.web.test.modules.dictionary.DictionarySearchModuleTestUIAsyncHelper;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class MangoAsyncGwtTestCase<VOType extends IBaseVO> extends GWTTestCase {
	public interface AsyncTestItem {
		void run(AsyncCallback<Object> asyncCallback);

		String getDescription();
	};

	private LinkedList<AsyncTestItem> asyncTestItems = new LinkedList<AsyncTestItem>();

	private Map<String, Object> asyncTestItemResults = new HashMap<String, Object>();

	public MangoAsyncGwtTestCase() {
	}

	public MangoAsyncGwtTestCase<VOType> deleteAllVOs(final Class<VOType> voClass) {
		this.asyncTestItems.add(new AsyncTestItem() {

			@Override
			public void run(final AsyncCallback<Object> asyncCallback) {
				MangoClientWeb.getInstance().getRemoteServiceLocator().getBaseEntityService().deleteAll(voClass.getName(), new BaseErrorAsyncCallback() {
					@Override
					public void onSuccess(Object result) {
						asyncCallback.onSuccess(MangoAsyncGwtTestCase.this);
					}
				});
			}

			@Override
			public String getDescription() {
				return "deleteAllVOs(" + voClass + ")";
			}
		});

		return this;
	}

	public DictionarySearchModuleTestUIAsyncHelper<VOType> openSearch(final BaseModel baseModel) {
		final String uuid = UUID.uuid();

		this.asyncTestItems.add(new AsyncTestItem() {
			@Override
			public void run(final AsyncCallback<Object> asyncCallback) {

				ModuleHandler.getInstance().startUIModule(DictionarySearchModule.geSearchModuleLocator(baseModel.getName()), null, new HashMap<String, Object>(), new BaseErrorAsyncCallback<IModuleUI>() {

					@Override
					public void onSuccess(IModuleUI result) {
						MangoAsyncGwtTestCase.this.asyncTestItemResults.put(uuid, result);
						asyncCallback.onSuccess(result);
					}
				});

			}

			@Override
			public String getDescription() {
				return "openSearch(" + baseModel.getName() + ")";
			}
		});

		return new DictionarySearchModuleTestUIAsyncHelper<VOType>(uuid, this.asyncTestItems, this.asyncTestItemResults);
	}

	public DictionaryEditorModuleTestUIAsyncHelper<VOType> openEditor(final BaseModel baseModel) {
		final String uuid = UUID.uuid();

		this.asyncTestItems.add(new AsyncTestItem() {
			@Override
			public void run(final AsyncCallback<Object> asyncCallback) {
				ModuleHandler.getInstance().startUIModule(DictionaryEditorModule.getModuleUrlForDictionary(baseModel.getName()), null, new HashMap<String, Object>(), new BaseErrorAsyncCallback<IModuleUI>() {

					@Override
					public void onSuccess(IModuleUI result) {
						MangoAsyncGwtTestCase.this.asyncTestItemResults.put(uuid, result);
						asyncCallback.onSuccess(result);
					}
				});
			}

			@Override
			public String getDescription() {
				return "openEditor(" + baseModel.getName() + ")";
			}
		});

		return new DictionaryEditorModuleTestUIAsyncHelper<VOType>(uuid, this.asyncTestItems, this.asyncTestItemResults);
	}

	public void runAsyncTests() {
		AsyncTestItem asyncTestItem = this.asyncTestItems.removeFirst();

		GWT.log("runnung async test item '" + asyncTestItem.toString() + "'");
		asyncTestItem.run(new RecursiveAsyncCallback(this.asyncTestItems, new BaseErrorAsyncCallback<Object>() {
			@Override
			public void onSuccess(Object result) {
				MangoAsyncGwtTestCase.this.asyncTestItemResults.clear();
				finishTest();
			}

		}));

		delayTestFinish(1000 * 60 * 5);
	}
}
