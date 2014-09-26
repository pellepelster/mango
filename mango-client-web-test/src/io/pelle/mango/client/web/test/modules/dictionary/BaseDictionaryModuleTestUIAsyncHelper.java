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
package io.pelle.mango.client.web.test.modules.dictionary;

import io.pelle.mango.client.base.db.vos.UUID;
import io.pelle.mango.client.base.modules.dictionary.model.controls.BigDecimalControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.BooleanControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.ControlGroupModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.DateControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.EnumerationControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IntegerControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.ReferenceControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.TextControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.test.MangoAsyncGwtTestHelper.AsyncTestItem;
import io.pelle.mango.client.web.test.modules.dictionary.controls.BigDecimalControlTestAsyncHelper;
import io.pelle.mango.client.web.test.modules.dictionary.controls.BooleanControlTestAsyncHelper;
import io.pelle.mango.client.web.test.modules.dictionary.controls.ControlGroupTestAsyncHelper;
import io.pelle.mango.client.web.test.modules.dictionary.controls.DateControlTestAsyncHelper;
import io.pelle.mango.client.web.test.modules.dictionary.controls.EnumerationControlTestAsyncHelper;
import io.pelle.mango.client.web.test.modules.dictionary.controls.IntegerControlTestAsyncHelper;
import io.pelle.mango.client.web.test.modules.dictionary.controls.ReferenceControlTestAsyncHelper;
import io.pelle.mango.client.web.test.modules.dictionary.controls.TextControlTestAsyncHelper;

import java.util.LinkedList;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class BaseDictionaryModuleTestUIAsyncHelper<T extends BaseDictionaryModuleTestUI> extends BaseAsyncHelper<T> {
	public BaseDictionaryModuleTestUIAsyncHelper(String asynTestItemResultId, LinkedList<AsyncTestItem> asyncTestItems, Map<String, Object> asyncTestItemResults) {
		super(asynTestItemResultId, asyncTestItems, asyncTestItemResults);
	}

	public TextControlTestAsyncHelper getTextControlTest(final TextControlModel controlModel) {

		final String uuid = UUID.uuid();

		this.addAsyncTestItem(new BaseAsyncTestItem() {
			@Override
			public void run(AsyncCallback<Object> asyncCallback) {
				BaseDictionaryModuleTestUIAsyncHelper.this.getAsyncTestItemResults().put(uuid, getAsyncTestItemResult().getTextControlTest(controlModel));
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}

			@Override
			public String getDescription() {
				return "getTextControlTest(" + controlModel.getName() + ")";
			}
		});

		return new TextControlTestAsyncHelper(uuid, this.getAsyncTestItems(), this.getAsyncTestItemResults());
	}

	public ControlGroupTestAsyncHelper getControlGroupTest(final ControlGroupModel controlModel) {

		final String uuid = UUID.uuid();

		this.addAsyncTestItem(new BaseAsyncTestItem() {
			@Override
			public void run(AsyncCallback<Object> asyncCallback) {
				BaseDictionaryModuleTestUIAsyncHelper.this.getAsyncTestItemResults().put(uuid, getAsyncTestItemResult().getControlGroupTest(controlModel));
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}

			@Override
			public String getDescription() {
				return "getControlGroupTest(" + controlModel.getName() + ")";
			}
		});

		return new ControlGroupTestAsyncHelper(uuid, this.getAsyncTestItems(), this.getAsyncTestItemResults());
	}

	public BigDecimalControlTestAsyncHelper getBigDecimalControlTest(final BigDecimalControlModel controlModel) {
		final String uuid = UUID.uuid();

		this.addAsyncTestItem(new BaseAsyncTestItem() {
			@Override
			public void run(AsyncCallback<Object> asyncCallback) {
				BaseDictionaryModuleTestUIAsyncHelper.this.getAsyncTestItemResults().put(uuid, getAsyncTestItemResult().getBigDecimalControlTest(controlModel));
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}

			@Override
			public String getDescription() {
				return "getBigDecimalControlTest(" + controlModel.getName() + ")";
			}
		});

		return new BigDecimalControlTestAsyncHelper(uuid, this.getAsyncTestItems(), this.getAsyncTestItemResults());
	}

	public BooleanControlTestAsyncHelper getBooleanControlTest(final BooleanControlModel controlModel) {
		final String uuid = UUID.uuid();

		this.addAsyncTestItem(new BaseAsyncTestItem() {
			@Override
			public void run(AsyncCallback<Object> asyncCallback) {
				BaseDictionaryModuleTestUIAsyncHelper.this.getAsyncTestItemResults().put(uuid, getAsyncTestItemResult().getBooleanControlTest(controlModel));
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}

			@Override
			public String getDescription() {
				return "getBooleanControlTest(" + controlModel.getName() + ")";
			}
		});

		return new BooleanControlTestAsyncHelper(uuid, this.getAsyncTestItems(), this.getAsyncTestItemResults());
	}

	public DateControlTestAsyncHelper getDateControlTest(final DateControlModel controlModel) {
		final String uuid = UUID.uuid();

		this.addAsyncTestItem(new BaseAsyncTestItem() {
			@Override
			public void run(AsyncCallback<Object> asyncCallback) {
				BaseDictionaryModuleTestUIAsyncHelper.this.getAsyncTestItemResults().put(uuid, getAsyncTestItemResult().getDateControlTest(controlModel));
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}

			@Override
			public String getDescription() {
				return "getDateControlTest(" + controlModel.getName() + ")";
			}
		});

		return new DateControlTestAsyncHelper(uuid, this.getAsyncTestItems(), this.getAsyncTestItemResults());
	}

	public IntegerControlTestAsyncHelper getIntegerControlTest(final IntegerControlModel controlModel) {
		final String uuid = UUID.uuid();

		this.addAsyncTestItem(new BaseAsyncTestItem() {
			@Override
			public void run(AsyncCallback<Object> asyncCallback) {
				BaseDictionaryModuleTestUIAsyncHelper.this.getAsyncTestItemResults().put(uuid, getAsyncTestItemResult().getIntegerControlTest(controlModel));
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}

			@Override
			public String getDescription() {
				return "getIntegerControlTest(" + controlModel.getName() + ")";
			}
		});

		return new IntegerControlTestAsyncHelper(uuid, this.getAsyncTestItems(), this.getAsyncTestItemResults());
	}

	public EnumerationControlTestAsyncHelper getEnumerationControlTest(final EnumerationControlModel controlModel) {
		final String uuid = UUID.uuid();

		this.addAsyncTestItem(new BaseAsyncTestItem() {
			@Override
			public void run(AsyncCallback<Object> asyncCallback) {
				BaseDictionaryModuleTestUIAsyncHelper.this.getAsyncTestItemResults().put(uuid, getAsyncTestItemResult().getEnumerationControlTest(controlModel));
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}

			@Override
			public String getDescription() {
				return "getEnumerationControlTest(" + controlModel.getName() + ")";
			}
		});

		return new EnumerationControlTestAsyncHelper(uuid, this.getAsyncTestItems(), this.getAsyncTestItemResults());
	}

	public <ReferenceVOType extends IBaseVO> ReferenceControlTestAsyncHelper getReferenceControlTest(final ReferenceControlModel<ReferenceVOType> controlModel) {
		final String uuid = UUID.uuid();

		this.addAsyncTestItem(new BaseAsyncTestItem() {
			@Override
			public void run(AsyncCallback<Object> asyncCallback) {
				BaseDictionaryModuleTestUIAsyncHelper.this.getAsyncTestItemResults().put(uuid, getAsyncTestItemResult().getReferenceControlTest(controlModel));
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}

			@Override
			public String getDescription() {
				return "getReferenceControlTest(" + controlModel.getName() + ")";
			}
		});

		return new ReferenceControlTestAsyncHelper<ReferenceVOType>(uuid, this.getAsyncTestItems(), this.getAsyncTestItemResults());
	}
}
