package io.pelle.mango.client.web.modules.dictionary.result;

import io.pelle.mango.client.base.modules.dictionary.model.search.IResultModel;
import io.pelle.mango.client.base.modules.dictionary.search.IDictionaryResult;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;
import io.pelle.mango.client.web.modules.dictionary.container.BaseTableElement;

public class DictionaryResult<VOType extends IBaseVO> extends BaseTableElement<VOType, IResultModel> implements IDictionaryResult
{
	public final static String CONTROL_FIRST_EDIT_DATA_KEY = "CONTROL_FIRST_EDIT_DATA_KEY";

	public DictionaryResult(IResultModel resultModel, BaseDictionaryElement parent)
	{
		super(resultModel, parent);
	}
}
