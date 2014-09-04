
package io.pelle.mango.server;

@org.springframework.web.bind.annotation.RestController
@org.springframework.web.bind.annotation.RequestMapping("baseentityservice")
public class BaseEntityServiceRestController  {

	@org.springframework.beans.factory.annotation.Autowired
	private io.pelle.mango.client.IBaseEntityService baseEntityService;
	
	public void setBaseEntityService(io.pelle.mango.client.IBaseEntityService baseEntityService)
	{
		this.baseEntityService = baseEntityService;
	}

	
	
	
		@org.springframework.web.bind.annotation.RequestMapping(value = "create", method = org.springframework.web.bind.annotation.RequestMethod.POST)
		public <CreateVOType extends io.pelle.mango.client.base.vo.IBaseVO>	 CreateVOType
		 create(@org.springframework.web.bind.annotation.RequestBody CreateVOType vo
		) {
			return this.baseEntityService.create(vo);
		}
	
	
		@org.springframework.web.bind.annotation.RequestMapping(value = "validate", method = org.springframework.web.bind.annotation.RequestMethod.POST)
		public <ValidateVOType extends io.pelle.mango.client.base.vo.IBaseVO>	 java.util.List<io.pelle.mango.client.base.messages.IValidationMessage>
		 validate(@org.springframework.web.bind.annotation.RequestBody ValidateVOType vo
		) {
			return this.baseEntityService.validate(vo);
		}
	
	
		@org.springframework.web.bind.annotation.RequestMapping(value = "validateandsave", method = org.springframework.web.bind.annotation.RequestMethod.POST)
		public <ValidateAndSaveVOType extends io.pelle.mango.client.base.vo.IBaseVO>	 io.pelle.mango.client.base.db.vos.Result<ValidateAndSaveVOType>
		 validateAndSave(@org.springframework.web.bind.annotation.RequestBody ValidateAndSaveVOType vo
		) {
			return this.baseEntityService.validateAndSave(vo);
		}
	
	
		@org.springframework.web.bind.annotation.RequestMapping(value = "validateandcreate", method = org.springframework.web.bind.annotation.RequestMethod.POST)
		public <ValidateAndCreateVOType extends io.pelle.mango.client.base.vo.IBaseVO>	 io.pelle.mango.client.base.db.vos.Result<ValidateAndCreateVOType>
		 validateAndCreate(@org.springframework.web.bind.annotation.RequestBody ValidateAndCreateVOType vo
		) {
			return this.baseEntityService.validateAndCreate(vo);
		}
	
	
		@org.springframework.web.bind.annotation.RequestMapping(value = "save", method = org.springframework.web.bind.annotation.RequestMethod.POST)
		public <SaveVOType extends io.pelle.mango.client.base.vo.IBaseVO>	 SaveVOType
		 save(@org.springframework.web.bind.annotation.RequestBody SaveVOType vo
		) {
			return this.baseEntityService.save(vo);
		}
	
	
		@org.springframework.web.bind.annotation.RequestMapping(value = "filter", method = org.springframework.web.bind.annotation.RequestMethod.POST)
		public <FilterVOType extends io.pelle.mango.client.base.vo.IBaseVO>	 java.util.List<FilterVOType>
		 filter(@org.springframework.web.bind.annotation.RequestBody io.pelle.mango.client.base.vo.query.SelectQuery<FilterVOType> selectQuery
		) {
			return this.baseEntityService.filter(selectQuery);
		}
	
	
	
	
		@org.springframework.web.bind.annotation.RequestMapping(value = "delete", method = org.springframework.web.bind.annotation.RequestMethod.POST)
		public <DeleteVOType extends io.pelle.mango.client.base.vo.IBaseVO>	 void
		 delete(@org.springframework.web.bind.annotation.RequestBody DeleteVOType vo
		) {
			 this.baseEntityService.delete(vo);
		}
	
	
	

}
