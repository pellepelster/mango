package io.pelle.mango.client;

public interface IBaseEntityService {
	<NewVOType extends io.pelle.mango.client.base.vo.IBaseVO>
	NewVOType
	 getNewVO(java.lang.String voClassName, 
	 java.util.Map<String, String> properties
	 );
	<CreateVOType extends io.pelle.mango.client.base.vo.IBaseVO>
	CreateVOType
	 create(CreateVOType vo
	 );
	<ValidateVOType extends io.pelle.mango.client.base.vo.IBaseVO>
	java.util.List<io.pelle.mango.client.base.messages.IValidationMessage>
	 validate(ValidateVOType vo
	 );
	<ValidateAndSaveVOType extends io.pelle.mango.client.base.vo.IBaseVO>
	io.pelle.mango.client.base.db.vos.Result<ValidateAndSaveVOType>
	 validateAndSave(ValidateAndSaveVOType vo
	 );
	<ValidateAndCreateVOType extends io.pelle.mango.client.base.vo.IBaseVO>
	io.pelle.mango.client.base.db.vos.Result<ValidateAndCreateVOType>
	 validateAndCreate(ValidateAndCreateVOType vo
	 );
	<SaveVOType extends io.pelle.mango.client.base.vo.IBaseVO>
	SaveVOType
	 save(SaveVOType vo
	 );
	<FilterVOType extends io.pelle.mango.client.base.vo.IBaseVO>
	java.util.List<FilterVOType>
	 filter(io.pelle.mango.client.base.vo.query.SelectQuery<FilterVOType> selectQuery
	 );
	void
	 deleteAll(java.lang.String voClassName
	 );
	<DeleteVOType extends io.pelle.mango.client.base.vo.IBaseVO>
	void
	 delete(DeleteVOType vo
	 );
	<ReadVOType extends io.pelle.mango.client.base.vo.IBaseVO>
	ReadVOType
	 read(java.lang.Long id, 
	 java.lang.String voClassName
	 );
}
