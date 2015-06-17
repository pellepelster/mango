package io.pelle.mango.server.hierarchy;

import io.pelle.mango.client.base.db.vos.IHierarchicalVO;
import io.pelle.mango.client.base.messages.IValidationMessage;
import io.pelle.mango.client.base.messages.ValidationMessage;
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelProvider;
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelUtil;
import io.pelle.mango.client.base.modules.dictionary.model.IDictionaryModel;
import io.pelle.mango.client.base.modules.hierarchical.HierarchicalConfigurationVO;
import io.pelle.mango.client.hierarchy.IHierarchicalService;
import io.pelle.mango.server.validator.IValidator;
import io.pelle.mango.server.validator.ValidatorMessages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class HierarchyParentValidator implements IValidator {
	
	@Autowired
	private IHierarchicalService hierachicalService;
	
	/** {@inheritDoc} */
	@Override
	public boolean canValidate(Object o) {
		return IHierarchicalVO.class.isAssignableFrom(o.getClass());
	}

	@Override
	public List<IValidationMessage> validate(Object o) {
		return validateInternal(o, "");
	}

	public List<IValidationMessage> validateInternal(Object o, String parentPath) {
		
		IHierarchicalVO vo = (IHierarchicalVO) o;

		IDictionaryModel dictionaryModel = DictionaryModelProvider.getDictionaryModelForClass(vo.getClass());
		HierarchicalConfigurationVO hierarchicalConfiguration = hierachicalService.getConfigurationByDictionaryId(dictionaryModel.getName());
		
		List<String> parentsDictionaryIds =  hierarchicalConfiguration.getDictionaryHierarchy().get(dictionaryModel.getName());

		List<IValidationMessage> result = new ArrayList<IValidationMessage>();

		Map<String, Object> messageContext = new HashMap<String, Object>();
		messageContext.put(IValidationMessage.DICTIONARY_EDITOR_LABEL_CONTEXT_KEY, DictionaryModelUtil.getEditorLabel(dictionaryModel));
		messageContext.put(IValidationMessage.VOCLASS_CONTEXT_KEY, vo.getClass().getName());

		if (!parentsDictionaryIds.isEmpty() && vo.getParent() == null) {
			result.add(new ValidationMessage(ValidatorMessages.HIERARCHICAL_PARENT_MANDATORY, messageContext));
		}


		return result;
	}

}
