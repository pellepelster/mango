/*
 * generated by Xtext
 */
package io.pelle.mango.dsl.generator.server.service

import com.google.inject.Inject
import io.pelle.mango.dsl.generator.client.ClientNameUtils
import io.pelle.mango.dsl.generator.client.ClientTypeUtils
import io.pelle.mango.dsl.generator.client.web.BaseServices
import io.pelle.mango.dsl.generator.server.ServerNameUtils
import io.pelle.mango.dsl.generator.util.ServiceUtils
import io.pelle.mango.dsl.mango.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

class RestServices {

	@Inject
	extension ServerNameUtils server

	@Inject
	extension ServiceUtils
	
	@Inject
	extension BaseServices
	
	@Inject extension ClientTypeUtils
	
	ClientNameUtils clientNameUtils = new ClientNameUtils
	
	def restServiceController(Service service) '''

	package «service.packageName»;
	
	@«RestController.name»
	@«RequestMapping.name»("«service.restMapping»")
	public class «service.restControllerName»  {

		@«Autowired.name»
		private «clientNameUtils.serviceInterfaceFullQualifiedName(service)» «service.variableName»;
		
		public «service.restControllerName»(«clientNameUtils.serviceInterfaceFullQualifiedName(service)» «service.variableName»)
		{
			this.«service.variableName» = «service.variableName»;
		}
	
		«FOR serviceMethod : service.remoteMethods»
		
			«IF serviceMethod.methodParameters.size == 1 && !serviceMethod.methodParameters.onlySimpleTypes && (serviceMethod.returnType == null || !serviceMethod.returnType.simpleType)»
			@«RequestMapping.name»(value = "«serviceMethod.restMapping»", method = «RequestMethod.name».POST)
			public «IF serviceMethod.genericTypeDefinition != null»«serviceMethod.genericTypeDefinition.genericTypeDefinition»	«ENDIF» «serviceMethod.serviceMethodReturnType» «serviceMethod.name.toFirstLower»(@«RequestBody.name» «serviceMethod.methodParameters.methodParameters») {
				«IF serviceMethod.hasReturn»return«ENDIF» this.«service.variableName».«serviceMethod.name.toFirstLower»(«serviceMethod.methodParameters.get(0).name.toFirstLower»);
			}
			«ENDIF»
		
		«ENDFOR»
	
	}
	'''

/*    @RequestMapping(value = "checkUserNameExists/{userName}")
    @Transactional(readOnly = true)
    public boolean checkUserNameExists(@PathVariable String userName) {
        return userService.checkUserNameExists(userName);
    }

    @RequestMapping(value = "registerUser", method = RequestMethod.POST)
    @Transactional()
    public UserRegisterResultVO registerUser(@RequestBody UserRegisterVO userRegisterVO, HttpServletResponse response) {
        return userService.registerUser(userRegisterVO, Optional.of(response));
    }

    @RequestMapping(value = "filter", method = RequestMethod.POST)
    @Transactional()
    public FilterPagingResultVO filter(@RequestBody FilterPagingRequestVO filterPagingRequestVO) {
        return userDAO.filter(filterPagingRequestVO.getQueryString(), filterPagingRequestVO.getFirstResult(), filterPagingRequestVO.getMaxResults());
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public BaseLoginResultVO login(@RequestBody UserLoginVO userLoginVO, HttpServletResponse response) {
        return userService.login(userLoginVO.getUserName(), userLoginVO.getUserPassword(), Optional.of(response));
    }

    @RequestMapping(value = "updateProfileField", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional()
    public String updateProfileField(@RequestBody ProfileFieldUpdateVO profileFieldUpdateVO) {
        Optional<BaseUser> baseUser = getCurrentUser();

        if (baseUser.isPresent())
        {
            return updateProfileField(baseUser.get().getName(), profileFieldUpdateVO);
        }
        else
        {
            throw new RuntimeException("no user found");
        }
    }

    @RequestMapping(value = "deleteAll", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional()
    public void deleteAll() {
        // TODO refactor userdao to remove dependent carts
        cartDAO.deleteAll();
        userDAO.deleteAll();
    }


    } */
	
}