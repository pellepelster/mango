package io.pelle.mango.dsl.generator.client

import com.google.inject.Inject
import com.google.inject.Injector
import io.pelle.mango.client.base.db.vos.IHierarchicalVO
import io.pelle.mango.dsl.generator.util.AttributeGeneratorFactory
import io.pelle.mango.dsl.generator.util.BaseAttributeUtils
import io.pelle.mango.dsl.generator.util.TypeUtils
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.ValueObject
import io.pelle.mango.dsl.mango.ValueObjectType
import java.util.List

class ClientTypeUtils extends TypeUtils {

	@Inject
	extension ClientNameUtils nameUtils

	@Inject
	extension ClientAttributeUtils attributeUtils

	@Inject
	extension AttributeGeneratorFactory attributeGeneratorFactory

	@Inject
	Injector injector
	
	static class HierachyParentAttributeGenerator extends AttributeGeneratorFactory.AttributeGenerator {
	
		private new(String attributeName, Class<?> attributeType, Injector injector, TypeUtils typeUtils, BaseAttributeUtils attributeUtils) {
			super(attributeName, attributeType, injector, typeUtils, attributeUtils)
		}
	
		override changeTrackingSetter() '''
			public void set«attributeName.toFirstUpper»(«attributeType.name» «attributeName») {
				getMetadata().addChange("«attributeName»", «attributeName»);
				
				if (parent == null) {
					setParentClassName(null);
					setParentId(null);
				} else {
					setParentClassName(«attributeName».getClass().getName());
					setParentId(«attributeName».getId());
				}
				this.«attributeName» = «attributeName»;
			}
		'''
	
	}
	
	def hierarchicalVOAttributes() {
		
		var List<AttributeGeneratorFactory.AttributeGenerator> hierarchicalVOAttributes = newArrayList

		hierarchicalVOAttributes.add(attributeGeneratorFactory.createAttributeGenerator("parentClassName", typeof(String), this, attributeUtils))
		hierarchicalVOAttributes.add(attributeGeneratorFactory.createAttributeGenerator("parentId", typeof(Long), this, attributeUtils))
		hierarchicalVOAttributes.add(new HierachyParentAttributeGenerator("parent", typeof(IHierarchicalVO), injector, this, attributeUtils))
		hierarchicalVOAttributes.add(attributeGeneratorFactory.createAttributeGenerator("hasChildren", typeof(Boolean), this, attributeUtils))

		return hierarchicalVOAttributes
	}	


	override String entityVOFullQualifiedName(Entity entity) {
		nameUtils.voFullQualifiedName(entity)
	}

	override dispatch String getType(Entity entity) {
		entity.voFullQualifiedName
	}

	override dispatch String getTypeClass(Entity entity) {
		getType(entity) + ".class"
	}
	
	def dispatch String getRawTypeClass(ValueObject valueObject) {
		valueObject.voFullQualifiedName + ".class"
	}
	
	override dispatch String getTypeClass(ValueObject valueObject) {
		getType(valueObject) + ".class"
	}
	
	
	def dispatch String getType(ValueObjectType valueObjectType) {
		return valueObjectType.type.voFullQualifiedName
	}

	def dispatch String getType(ValueObject valueObject) {
		return valueObject.voFullQualifiedName
	}

	override getAttributeUtils() {
		attributeUtils
	}

	override getAttributeGeneratorFactory() {
		attributeGeneratorFactory
	}

}
