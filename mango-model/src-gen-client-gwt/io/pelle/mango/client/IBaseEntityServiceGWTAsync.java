package io.pelle.mango.client;

public interface IBaseEntityServiceGWTAsync {

	<NewVOType extends io.pelle.mango.client.base.vo.IBaseVO>
	
	void getNewVO(java.lang.String voClassName, 
	java.util.Map<String, String> properties
	, com.google.gwt.user.client.rpc.AsyncCallback<NewVOType> callback
	)
	;
	<CreateVOType extends io.pelle.mango.client.base.vo.IBaseVO>
	
	void create(CreateVOType vo
	, com.google.gwt.user.client.rpc.AsyncCallback<CreateVOType> callback
	)
	;
	<ValidateVOType extends io.pelle.mango.client.base.vo.IBaseVO>
	
	void validate(ValidateVOType vo
	, com.google.gwt.user.client.rpc.AsyncCallback<java.util.List<io.pelle.mango.client.base.messages.IValidationMessage>> callback
	)
	;
	<ValidateAndSaveVOType extends io.pelle.mango.client.base.vo.IBaseVO>
	
	void validateAndSave(ValidateAndSaveVOType vo
	, com.google.gwt.user.client.rpc.AsyncCallback<io.pelle.mango.client.base.db.vos.Result<ValidateAndSaveVOType>> callback
	)
	;
	<ValidateAndCreateVOType extends io.pelle.mango.client.base.vo.IBaseVO>
	
	void validateAndCreate(ValidateAndCreateVOType vo
	, com.google.gwt.user.client.rpc.AsyncCallback<io.pelle.mango.client.base.db.vos.Result<ValidateAndCreateVOType>> callback
	)
	;
	<SaveVOType extends io.pelle.mango.client.base.vo.IBaseVO>
	
	void save(SaveVOType vo
	, com.google.gwt.user.client.rpc.AsyncCallback<SaveVOType> callback
	)
	;
	<FilterVOType extends io.pelle.mango.client.base.vo.IBaseVO>
	
	void filter(io.pelle.mango.client.base.vo.query.SelectQuery<FilterVOType> selectQuery
	, com.google.gwt.user.client.rpc.AsyncCallback<java.util.List<FilterVOType>> callback
	)
	;
	
	void deleteAll(java.lang.String voClassName
	, com.google.gwt.user.client.rpc.AsyncCallback<Void> callback
	)
	;
	<DeleteVOType extends io.pelle.mango.client.base.vo.IBaseVO>
	
	void delete(DeleteVOType vo
	, com.google.gwt.user.client.rpc.AsyncCallback<Void> callback
	)
	;
	<ReadVOType extends io.pelle.mango.client.base.vo.IBaseVO>
	
	void read(java.lang.Long id, 
	java.lang.String voClassName
	, com.google.gwt.user.client.rpc.AsyncCallback<ReadVOType> callback
	)
	;
}
