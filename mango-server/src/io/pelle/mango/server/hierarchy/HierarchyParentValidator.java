package io.pelle.mango.server.hierarchy;

import io.pelle.mango.client.base.db.vos.IHierarchicalVO;
import io.pelle.mango.client.base.messages.IValidationMessage;
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelProvider;
import io.pelle.mango.client.base.modules.dictionary.model.IDictionaryModel;
import io.pelle.mango.client.base.modules.hierarchical.HierarchicalConfigurationVO;
import io.pelle.mango.client.hierarchy.IHierarchicalService;
import io.pelle.mango.server.validator.IValidator;

import java.util.ArrayList;
import java.util.List;

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
		HierarchicalConfigurationVO hierarchicalConfigurationVO = hierachicalService.getConfigurationByDictionaryId(dictionaryModel.getName());
		
		List<IValidationMessage> result = new ArrayList<IValidationMessage>();


		return result;
	}

}
