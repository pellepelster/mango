package io.pelle.mango.test;
	
import java.util.HashMap;
import java.util.Map;

@org.springframework.stereotype.Component
public class MangoDemoVOMapper extends io.pelle.mango.server.base.BaseEntityVOMapper implements io.pelle.mango.client.base.vo.IEntityVOMapper {
				
	@java.lang.SuppressWarnings("serial")
	private Map<Class<?>, Class<?>> entityVOMapper = new HashMap<Class<?>, Class<?>>() {
		{
			put(io.pelle.mango.test.client.Entity1VO.class, io.pelle.mango.test.Entity1.class);
			put(io.pelle.mango.test.Entity1.class, io.pelle.mango.test.client.Entity1VO.class);
			put(io.pelle.mango.test.client.Entity2VO.class, io.pelle.mango.test.Entity2.class);
			put(io.pelle.mango.test.Entity2.class, io.pelle.mango.test.client.Entity2VO.class);
			put(io.pelle.mango.test.client.Entity3VO.class, io.pelle.mango.test.Entity3.class);
			put(io.pelle.mango.test.Entity3.class, io.pelle.mango.test.client.Entity3VO.class);
			put(io.pelle.mango.test.client.Entity4VO.class, io.pelle.mango.test.Entity4.class);
			put(io.pelle.mango.test.Entity4.class, io.pelle.mango.test.client.Entity4VO.class);
			put(io.pelle.mango.test.client.Entity5VO.class, io.pelle.mango.test.Entity5.class);
			put(io.pelle.mango.test.Entity5.class, io.pelle.mango.test.client.Entity5VO.class);
			put(io.pelle.mango.test.client.Entity6VO.class, io.pelle.mango.test.Entity6.class);
			put(io.pelle.mango.test.Entity6.class, io.pelle.mango.test.client.Entity6VO.class);
			put(io.pelle.mango.test.client.Entity7VO.class, io.pelle.mango.test.Entity7.class);
			put(io.pelle.mango.test.Entity7.class, io.pelle.mango.test.client.Entity7VO.class);
		}
	};

	protected Map<Class<?>, Class<?>> getClassMappings() {
		return entityVOMapper;
	}

	public Class<?> getMappedClass(Class<?> clazz) {
		return entityVOMapper.get(clazz);
	}

}
