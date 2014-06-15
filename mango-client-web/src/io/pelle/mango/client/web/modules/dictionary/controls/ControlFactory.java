package io.pelle.mango.client.web.modules.dictionary.controls;

import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBigDecimalControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBooleanControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IDateControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IEnumerationControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IFileControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IHierarchicalControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IIntegerControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IReferenceControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.ITextControlModel;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

public class ControlFactory
{
	private static ControlFactory instance;

	public static ControlFactory getInstance()
	{
		if (instance == null)
		{
			instance = new ControlFactory();
		}

		return instance;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static BaseDictionaryControl createControl(IBaseControlModel baseControlModel, BaseDictionaryElement<? extends IBaseModel> parent)
	{
		if (baseControlModel instanceof ITextControlModel)
		{
			return new TextControl((ITextControlModel) baseControlModel, parent);
		}
		else if (baseControlModel instanceof IIntegerControlModel)
		{
			return new IntegerControl((IIntegerControlModel) baseControlModel, parent);
		}
		else if (baseControlModel instanceof IDateControlModel)
		{
			return new DateControl((IDateControlModel) baseControlModel, parent);
		}
		else if (baseControlModel instanceof IBooleanControlModel)
		{
			return new BooleanControl((IBooleanControlModel) baseControlModel, parent);
		}
		else if (baseControlModel instanceof IEnumerationControlModel)
		{
			return new EnumerationControl((IEnumerationControlModel) baseControlModel, parent);
		}
		else if (baseControlModel instanceof IReferenceControlModel)
		{
			return new ReferenceControl((IReferenceControlModel) baseControlModel, parent);
		}
		else if (baseControlModel instanceof IBigDecimalControlModel)
		{
			return new BigDecimalControl((IBigDecimalControlModel) baseControlModel, parent);
		}
		else if (baseControlModel instanceof IHierarchicalControlModel)
		{
			return new HierarchicalControl((IHierarchicalControlModel) baseControlModel, parent);
		}
		else if (baseControlModel instanceof IFileControlModel)
		{
			return new FileControl((IFileControlModel) baseControlModel, parent);
		}
		else
		{
			throw new RuntimeException("unsupported control model type '" + baseControlModel.getClass().getName() + "'");
		}
	}

}
