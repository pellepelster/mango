
package io.pelle.mango.server;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

@org.springframework.web.bind.annotation.RestController
@org.springframework.web.bind.annotation.RequestMapping("baseentityservice")
public class BaseEntityServiceRestController  {

	@org.springframework.beans.factory.annotation.Autowired
	private io.pelle.mango.client.IBaseEntityService baseEntityService;
	
	public void setBaseEntityService(io.pelle.mango.client.IBaseEntityService baseEntityService)
	{
		this.baseEntityService = baseEntityService;
	}

	@RequestMapping(value = "getnewvo/{voClassName}/{properties}")
	public <NewVOType extends io.pelle.mango.client.base.vo.IBaseVO> NewVOType
	 getNewVO(@RequestParam java.lang.String voClassName, @RequestParam java.util.Map<String, String> properties) {
		return this.baseEntityService.getNewVO(voClassName,properties);
	}
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public <CreateVOType extends io.pelle.mango.client.base.vo.IBaseVO> CreateVOType
	 create(@RequestBody CreateVOType vo
	) {
		return this.baseEntityService.create(vo);
	}
	@RequestMapping(value = "validate", method = RequestMethod.POST)
	public <ValidateVOType extends io.pelle.mango.client.base.vo.IBaseVO> java.util.List<io.pelle.mango.client.base.messages.IValidationMessage>
	 validate(@RequestBody ValidateVOType vo
	) {
		return this.baseEntityService.validate(vo);
	}
	@RequestMapping(value = "validateandsave", method = RequestMethod.POST)
	public <ValidateAndSaveVOType extends io.pelle.mango.client.base.vo.IBaseVO> io.pelle.mango.client.base.db.vos.Result<ValidateAndSaveVOType>
	 validateAndSave(@RequestBody ValidateAndSaveVOType vo
	) {
		return this.baseEntityService.validateAndSave(vo);
	}
	@RequestMapping(value = "validateandcreate", method = RequestMethod.POST)
	public <ValidateAndCreateVOType extends io.pelle.mango.client.base.vo.IBaseVO> io.pelle.mango.client.base.db.vos.Result<ValidateAndCreateVOType>
	 validateAndCreate(@RequestBody ValidateAndCreateVOType vo
	) {
		return this.baseEntityService.validateAndCreate(vo);
	}
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public <SaveVOType extends io.pelle.mango.client.base.vo.IBaseVO> SaveVOType
	 save(@RequestBody SaveVOType vo
	) {
		return this.baseEntityService.save(vo);
	}
	@RequestMapping(value = "filter", method = RequestMethod.POST)
	public <FilterVOType extends io.pelle.mango.client.base.vo.IBaseVO> java.util.List<FilterVOType>
	 filter(@RequestBody io.pelle.mango.client.base.vo.query.SelectQuery<FilterVOType> selectQuery
	) {
		return this.baseEntityService.filter(selectQuery);
	}
	@RequestMapping(value = "deleteall/{voClassName}")
	public  void
	 deleteAll(@RequestParam java.lang.String voClassName) {
		 this.baseEntityService.deleteAll(voClassName);
	}
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public <DeleteVOType extends io.pelle.mango.client.base.vo.IBaseVO> void
	 delete(@RequestBody DeleteVOType vo
	) {
		 this.baseEntityService.delete(vo);
	}
	@RequestMapping(value = "read/{id}/{voClassName}")
	public <ReadVOType extends io.pelle.mango.client.base.vo.IBaseVO> ReadVOType
	 read(@RequestParam java.lang.Long id, @RequestParam java.lang.String voClassName) {
		return this.baseEntityService.read(id,voClassName);
	}
}
