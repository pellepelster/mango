package io.pelle.mango.client.web.test.modules.dictionary.controls;

import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.test.MangoAsyncGwtTestCase.AsyncTestItem;

import java.util.LinkedList;
import java.util.Map;

public class ReferenceControlTestAsyncHelper<VOType extends IBaseVO> extends BaseControlTestAsyncHelper<ReferenceControlTest<VOType>, VOType> {

	public ReferenceControlTestAsyncHelper(String asyncTestItemResultId, LinkedList<AsyncTestItem> asyncTestItems, Map<String, Object> asyncTestItemResults) {
		super(asyncTestItemResultId, asyncTestItems, asyncTestItemResults);
	}

}
