package io.pelle.mango.client.web.modules.dictionary.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.IReferenceControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IReferenceControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;
import io.pelle.mango.client.web.modules.dictionary.base.DictionaryUtil;

public class ReferenceControl<VOType extends IBaseVO> extends BaseDictionaryControl<IReferenceControlModel, VOType>  implements IReferenceControl<VOType>
{

	public ReferenceControl(IReferenceControlModel referenceControlModel, BaseDictionaryElement<? extends IBaseModel> parent)
	{
		super(referenceControlModel, parent);
	}

	@Override
	public String format()
	{
		if (getValue() != null && getValue() instanceof IBaseVO)
		{
			return DictionaryUtil.getLabel(getModel(), getValue());
		}
		else
		{
			return super.format();
		}
	}

	@Override
	protected ParseResult parseValueInternal(String valueString)
	{
		throw new RuntimeException("not implemented");
	}
}
