package io.pelle.mango;
	
import java.util.HashMap;
import java.util.Map;

@org.springframework.stereotype.Component
public class MangoVOMapper extends io.pelle.mango.server.base.BaseEntityVOMapper implements io.pelle.mango.client.base.vo.IEntityVOMapper {
				
	@java.lang.SuppressWarnings("serial")
	private Map<Class<?>, Class<?>> entityVOMapper = new HashMap<Class<?>, Class<?>>() {
		{
			put(io.pelle.mango.client.DictionaryLabelSearchIndexVO.class, io.pelle.mango.DictionaryLabelSearchIndex.class);
			put(io.pelle.mango.DictionaryLabelSearchIndex.class, io.pelle.mango.client.DictionaryLabelSearchIndexVO.class);
			put(io.pelle.mango.client.DictionaryHierarchicalNodeVO.class, io.pelle.mango.DictionaryHierarchicalNode.class);
			put(io.pelle.mango.DictionaryHierarchicalNode.class, io.pelle.mango.client.DictionaryHierarchicalNodeVO.class);
			put(io.pelle.mango.client.FileVO.class, io.pelle.mango.File.class);
			put(io.pelle.mango.File.class, io.pelle.mango.client.FileVO.class);
		}
	};

	protected Map<Class<?>, Class<?>> getClassMappings() {
		return entityVOMapper;
	}

	public Class<?> getMappedClass(Class<?> clazz) {
		return entityVOMapper.get(clazz);
	}

}
