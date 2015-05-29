package io.pelle.mango.dsl.generator.client

import com.google.inject.Inject
import com.google.inject.Injector
import io.pelle.mango.client.base.db.vos.IHierarchicalVO
import io.pelle.mango.dsl.ModelUtil
import io.pelle.mango.dsl.generator.GeneratorConstants
import io.pelle.mango.dsl.generator.util.AttributeGeneratorFactory
import io.pelle.mango.dsl.generator.util.NameUtils
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.Enumeration
import io.pelle.mango.dsl.mango.EnumerationDataType
import io.pelle.mango.dsl.mango.EnumerationEntityAttribute
import io.pelle.mango.dsl.mango.EnumerationValue
import io.pelle.mango.dsl.mango.Model
import io.pelle.mango.dsl.mango.PackageDeclaration
import java.util.List

class ClientNameUtils extends NameUtils {

	@Inject
	Injector injector

	static class HierachyParentAttributeGenerator extends AttributeGeneratorFactory.AttributeGenerator {
	
		private new(String attributeName, Class<?> attributeType, Injector injector) {
			super(attributeName, attributeType, injector)
		}
	
		override changeTrackingSetter() '''
			public void set«attributeName.toFirstUpper»(«attributeType.name» «attributeName») {
				getChangeTracker().addChange("«attributeName»", «attributeName»);
				setParentClassName(«attributeName».getClass().getName());
				setParentId(«attributeName».getId());
				this.«attributeName» = «attributeName»;
			}
		'''
	
	}
	
	@Inject
	extension AttributeGeneratorFactory attributeGeneratorFactory
	
	def hierarchicalVOAttributes() {
		
		var List<AttributeGeneratorFactory.AttributeGenerator> hierarchicalVOAttributes = newArrayList

		hierarchicalVOAttributes.add(attributeGeneratorFactory.createAttributeGenerator("parentClassName", typeof(String)))
		hierarchicalVOAttributes.add(attributeGeneratorFactory.createAttributeGenerator("parentId", typeof(Long)))
		hierarchicalVOAttributes.add(new HierachyParentAttributeGenerator("parent", typeof(IHierarchicalVO), injector))
		hierarchicalVOAttributes.add(attributeGeneratorFactory.createAttributeGenerator("hasChildren", typeof(Boolean)))

		return hierarchicalVOAttributes
	}	
	
	override dispatch String getPackageName(PackageDeclaration packageDeclaration) {
		if (packageDeclaration.eContainer instanceof Model)
		{
			packageDeclaration.packageName.packageName + "." + GeneratorConstants.CLIENT_PACKAGE_POSTFIX
		}
		else
		{
			combinePackageName(getPackageName(packageDeclaration.eContainer), packageDeclaration.packageName.packageName)
		}
	}

	override def modelPackageName(Model model) {
		return ModelUtil.getSingleRootPackage(model).packageName.packageName + "." + GeneratorConstants.CLIENT_PACKAGE_POSTFIX
	}
	
	//-------------------------------------------------------------------------
	// vo
	//-------------------------------------------------------------------------
	def voName(Entity entity) {
		return entity.name.toFirstUpper + "VO";
	}
	
	def voFullQualifiedName(Entity entity) {
		return getPackageName(entity) + "." + voName(entity);
	}

	def voFullQualifiedFileName(Entity entity) {
		return voFullQualifiedName(entity).replaceAll("\\.", "/")  + ".java";
	}
	
	//-------------------------------------------------------------------------
	// enumeration
	//-------------------------------------------------------------------------
	def enumerationValueName(EnumerationValue enumerationValue) {
		return enumerationValue.name.toUpperCase;
	}

	def dispatch enumerationName(Enumeration enumeration) {
		return enumeration.name.toUpperCase;
	}

	def dispatch String enumerationName(EnumerationEntityAttribute enumerationEntityAttribute) {
		return enumerationEntityAttribute.type.enumerationName;
	}

	def dispatch String enumerationName(EnumerationDataType enumerationDataType) {
		return enumerationDataType.enumeration.enumerationName;
	}

	def enumerationFullQualifiedName(Enumeration enumeration) {
		return getPackageName(enumeration) + "." + enumerationName(enumeration);
	}

	def enumerationFullQualifiedFileName(Enumeration enumeration) {
		return enumerationFullQualifiedName(enumeration).replaceAll("\\.", "/")  + ".java";
	}

	def enumerationFullQualifiedName(EnumerationEntityAttribute enumerationEntityAttribute) {
		return getPackageName(enumerationEntityAttribute) + "." + enumerationName(enumerationEntityAttribute);
	}

	//-------------------------------------------------------------------------
	// enumeration value parser
	//-------------------------------------------------------------------------
	def enumerationValueParserName(Model model) {
		return model.modelName.toFirstUpper + "EnumerationValueParser";
	}

	def enumerationValueParserFullQualifiedName(Model model) {
		return model.modelPackageName + "." + model.enumerationValueParserName;
	}

	def enumerationValueParserFullQualifiedFileName(Model model) {
		return model.enumerationValueParserFullQualifiedName.replaceAll("\\.", "/")  + ".java";
	}
	
}
