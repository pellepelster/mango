package io.pelle.mango.server.validator;

import io.pelle.mango.client.IHierachicalService;
import io.pelle.mango.client.base.db.vos.IHierarchicalVO;
import io.pelle.mango.client.base.messages.IValidationMessage;
import io.pelle.mango.client.base.messages.ValidationMessage;
import io.pelle.mango.client.base.modules.hierarchical.HierarchicalConfigurationVO;
import io.pelle.mango.client.base.util.CollectionUtils;
import io.pelle.mango.client.modules.BaseDictionaryEditorModule;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class HierarchicalMandatoryValidator implements IValidator
{

	@Autowired
	private IHierachicalService hierachicalService;

	/** {@inheritDoc} */
	@Override
	public boolean canValidate(Object o)
	{
		return IHierarchicalVO.class.isAssignableFrom(o.getClass());
	}

	@Override
	public List<IValidationMessage> validate(Object o)
	{
		List<IValidationMessage> result = new ArrayList<IValidationMessage>();

		IHierarchicalVO hierarchicalVO = (IHierarchicalVO) o;

		if (hierarchicalVO.getData().containsKey(BaseDictionaryEditorModule.EDITORDICTIONARYNAME_PARAMETER_ID))
		{
			String dictionaryId = (String) hierarchicalVO.getData().get(BaseDictionaryEditorModule.EDITORDICTIONARYNAME_PARAMETER_ID);

			if (hierarchicalVO.getParent() == null && !HierarchicalConfigurationVO.isRootDictionary(dictionaryId, this.hierachicalService.getConfigurations()))
			{
				result.add(new ValidationMessage(ValidatorMessages.MANDATORY_ATTRIBUTE, CollectionUtils.getMap(IValidationMessage.ATTRIBUTE_CONTEXT_KEY,
						IHierarchicalVO.FIELD_PARENT.getAttributeName(), IValidationMessage.VOCLASS_CONTEXT_KEY, hierarchicalVO.getClass().getSimpleName())));

			}

		}

		return result;
	}

	public void setHierachicalService(IHierachicalService hierachicalService)
	{
		this.hierachicalService = hierachicalService;
	}
}
