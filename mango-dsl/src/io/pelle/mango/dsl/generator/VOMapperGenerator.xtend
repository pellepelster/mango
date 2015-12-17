package io.pelle.mango.dsl.generator

import com.google.inject.Inject
import io.pelle.mango.client.base.vo.IEntityVOMapper
import io.pelle.mango.dsl.generator.client.ClientNameUtils
import io.pelle.mango.dsl.generator.server.ServerNameUtils
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.Model

class VOMapperGenerator {

	@Inject 
	extension ServerNameUtils
	
	var ClientNameUtils clientNameUtils = new ClientNameUtils

	def compileVOMapper(Model model) '''
package «model.modelPackageName»;
	
import java.util.HashMap;
import java.util.Map;

@org.springframework.stereotype.Component
public class «model.voMapperName» extends io.pelle.mango.server.base.BaseEntityVOMapper implements «IEntityVOMapper.name» {
				
	@java.lang.SuppressWarnings("serial")
	private Map<Class<?>, Class<?>> entityVOMapper = new HashMap<Class<?>, Class<?>>() {
		{
			«FOR entity : model.eAllContents.toIterable.filter(Entity)»
				put(«clientNameUtils.voFullQualifiedName(entity)».class, «entity.entityFullQualifiedName».class);
				put(«entity.entityFullQualifiedName».class, «clientNameUtils.voFullQualifiedName(entity)».class);
			«ENDFOR»
		}
	};

	protected Map<Class<?>, Class<?>> getClassMappings() {
		return entityVOMapper;
	}

	public Class<?> getMappedClass(Class<?> clazz) {
		return entityVOMapper.get(clazz);
	}

}
'''	
}
