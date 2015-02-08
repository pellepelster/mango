package io.pelle.mango.server.entity;

import io.pelle.mango.client.base.db.vos.Result;
import io.pelle.mango.client.base.messages.IValidationMessage;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.query.DeleteQuery;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.db.dao.IBaseVODAO;
import io.pelle.mango.db.util.BeanUtils;
import io.pelle.mango.server.validator.IValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConstructorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class BaseEntityServiceImpl implements IBaseEntityService {

	@Autowired
	private IBaseVODAO baseVODAO;

	@Override
	public <CreateVOType extends IBaseVO> CreateVOType create(CreateVOType vo) {
		return baseVODAO.create(vo);
	}

	@Autowired(required = false)
	private List<IValidator> validators = new ArrayList<IValidator>();

	@Override
	public <ValidateVOType extends IBaseVO> List<IValidationMessage> validate(ValidateVOType vo) {

		List<IValidationMessage> validationMessages = new ArrayList<IValidationMessage>();

		for (IValidator validator : this.validators) {
			if (validator.canValidate(vo)) {
				validationMessages.addAll(validator.validate(vo));
			}
		}

		return validationMessages;
	}

	@Override
	public <ValidateAndSaveVOType extends IBaseVO> Result<ValidateAndSaveVOType> validateAndSave(ValidateAndSaveVOType vo) {

		Result<ValidateAndSaveVOType> result = new Result<ValidateAndSaveVOType>();

		List<IValidationMessage> validationMessages = validate(vo);
		result.getValidationMessages().addAll(validationMessages);

		if (validationMessages.isEmpty()) {
			vo = save(vo);
		}
		result.setVO(vo);

		return result;
	}

	@Override
	public <ValidateAndCreateVOType extends IBaseVO> Result<ValidateAndCreateVOType> validateAndCreate(ValidateAndCreateVOType vo) {

		Result<ValidateAndCreateVOType> result = new Result<ValidateAndCreateVOType>();

		List<IValidationMessage> validationMessages = validate(vo);

		result.getValidationMessages().addAll(validationMessages);

		if (validationMessages.isEmpty()) {
			vo = create(vo);
		}
		result.setVO(vo);

		return result;
	}

	@Override
	public <SaveVOType extends IBaseVO> SaveVOType save(SaveVOType vo) {
		return baseVODAO.save(vo);
	}

	@Override
	public <FilterVOType extends IBaseVO> List<FilterVOType> filter(SelectQuery<FilterVOType> selectQuery) {
		return baseVODAO.filter(selectQuery);
	}

	@Override
	public <DeleteVOType extends IBaseVO> void delete(DeleteVOType vo) {
		baseVODAO.delete(vo);
	}

	public void setBaseVODAO(IBaseVODAO baseVODAO) {
		this.baseVODAO = baseVODAO;
	}

	@Override
	public <NewVOType extends IBaseVO> NewVOType getNewVO(String voClassName, Map<String, String> properties) {

		try {
			Class<?> voClass = Class.forName(voClassName);
			@SuppressWarnings("unchecked")
			NewVOType vo = (NewVOType) ConstructorUtils.invokeConstructor(voClass, new Object[0]);

			return vo;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <ReadVOType extends IBaseVO> ReadVOType read(Long id, String voClassName) {
		return (ReadVOType) baseVODAO.read(id, BeanUtils.getVOClass(voClassName));
	}

	@Override
	public void deleteAll(String voClassName) {
		baseVODAO.deleteAll(BeanUtils.getVOClass(voClassName));
	}

	public void setValidators(List<IValidator> validators) {
		this.validators = validators;
	}

	@Override
	public <DeleteVOType extends IBaseVO> void deleteQuery(DeleteQuery<DeleteVOType> deleteQuery) {
		baseVODAO.deleteQuery(deleteQuery);
	}
}
