package io.pelle.mango.client.web.test;

import io.pelle.mango.client.base.db.vos.UUID;
import io.pelle.mango.client.base.layout.IModuleUI;
import io.pelle.mango.client.base.modules.dictionary.model.BaseModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.module.ModuleHandler;
import io.pelle.mango.client.web.modules.dictionary.editor.DictionaryEditorModule;
import io.pelle.mango.client.web.modules.dictionary.search.DictionarySearchModule;
import io.pelle.mango.client.web.test.modules.dictionary.BaseAsyncTestItem;
import io.pelle.mango.client.web.test.modules.dictionary.DictionaryEditorModuleTestUIAsyncHelper;
import io.pelle.mango.client.web.test.modules.dictionary.DictionarySearchModuleTestUIAsyncHelper;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.google.common.base.Optional;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class MangoAsyncGwtTestHelper<VOType extends IBaseVO> extends GWTTestCase {

	private RecursiveAsyncCallback recursiveAsyncCallback;

	public interface AsyncTestItem {

		void run(AsyncCallback<Object> asyncCallback);

		String getDescription();

	};

	private LinkedList<AsyncTestItem> asyncTestItems = new LinkedList<AsyncTestItem>();

	private Map<String, Object> asyncTestItemResults = new HashMap<String, Object>();

	public MangoAsyncGwtTestHelper() {
		this.toString();
	}

	public MangoAsyncGwtTestHelper<VOType> deleteAllVOs(final Class<VOType> voClass) {
		this.asyncTestItems.add(new BaseAsyncTestItem() {

			@Override
			public void run(final AsyncCallback<Object> asyncCallback) {
				MangoClientWeb.getInstance().getRemoteServiceLocator().getBaseEntityService().deleteAll(voClass.getName(), new BaseErrorAsyncCallback() {
					@Override
					public void onSuccess(Object result) {
						asyncCallback.onSuccess(MangoAsyncGwtTestHelper.this);
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

		this.asyncTestItems.add(new BaseAsyncTestItem() {
			@Override
			public void run(final AsyncCallback<Object> asyncCallback) {

				AsyncCallback<IModuleUI> moduleCallback = new BaseErrorAsyncCallback<IModuleUI>() {

					@Override
					public void onSuccess(IModuleUI result) {
						asyncTestItemResults.put(uuid, result);
						asyncCallback.onSuccess(result);
					}
				};

				ModuleHandler.getInstance().startUIModule(DictionarySearchModule.geSearchModuleLocator(baseModel.getName()), null, new HashMap<String, Object>(), Optional.of(moduleCallback));

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

		this.asyncTestItems.add(new BaseAsyncTestItem() {
			@Override
			public void run(final AsyncCallback<Object> asyncCallback) {

				AsyncCallback<IModuleUI> moduleCallback = new BaseErrorAsyncCallback<IModuleUI>() {

					@Override
					public void onSuccess(IModuleUI result) {
						asyncTestItemResults.put(uuid, result);
						asyncCallback.onSuccess(result);
					}
				};

				ModuleHandler.getInstance().startUIModule(DictionaryEditorModule.getModuleUrlForDictionary(baseModel.getName()), null, new HashMap<String, Object>(), Optional.of(moduleCallback));
			}

			@Override
			public String getDescription() {
				return "openEditor(" + baseModel.getName() + ")";
			}
		});

		return new DictionaryEditorModuleTestUIAsyncHelper<VOType>(uuid, this.asyncTestItems, this.asyncTestItemResults);
	}

	public DictionaryEditorModuleTestUIAsyncHelper<VOType> openEditor(final BaseModel baseModel, final long id) {
		final String uuid = UUID.uuid();

		this.asyncTestItems.add(new BaseAsyncTestItem() {
			@Override
			public void run(final AsyncCallback<Object> asyncCallback) {

				AsyncCallback<IModuleUI> moduleCallback = new BaseErrorAsyncCallback<IModuleUI>() {

					@Override
					public void onSuccess(IModuleUI result) {
						asyncTestItemResults.put(uuid, result);
						asyncCallback.onSuccess(result);
					}
				};

				ModuleHandler.getInstance().startUIModule(DictionaryEditorModule.getModuleUrlForDictionary(baseModel.getName(), id), null, new HashMap<String, Object>(), Optional.of(moduleCallback));
			}

			@Override
			public String getDescription() {
				return "openEditor(" + baseModel.getName() + ")";
			}
		});

		return new DictionaryEditorModuleTestUIAsyncHelper<VOType>(uuid, this.asyncTestItems, this.asyncTestItemResults);
	}

	public void runAsyncTests() {

		MangoAsyncGwtTestHelper.this.delayTestFinish(1000 * 60 * 5);

		AsyncTestItem asyncTestItem = this.asyncTestItems.removeFirst();

		if (this.recursiveAsyncCallback == null) {

			GWT.log("runnung async test item '" + asyncTestItem.getDescription() + "', async test items left: " + asyncTestItems.size());

			final RecursiveAsyncCallback recursiveAsyncCallback = new RecursiveAsyncCallback(this.asyncTestItems, new BaseErrorAsyncCallback<Object>() {
				@Override
				public void onSuccess(Object result) {

					if (asyncTestItems.isEmpty()) {
						MangoAsyncGwtTestHelper.this.finishTest();
					}
				}

			});

			this.recursiveAsyncCallback = recursiveAsyncCallback;
		}
		asyncTestItem.run(this.recursiveAsyncCallback);
	}
}
