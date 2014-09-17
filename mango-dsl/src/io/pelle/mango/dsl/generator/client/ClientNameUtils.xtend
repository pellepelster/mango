package io.pelle.mango.dsl.generator.client

import io.pelle.mango.dsl.ModelUtil
import io.pelle.mango.dsl.generator.GeneratorConstants
import io.pelle.mango.dsl.generator.util.NameUtils
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.Enumeration
import io.pelle.mango.dsl.mango.EnumerationDataType
import io.pelle.mango.dsl.mango.EnumerationEntityAttribute
import io.pelle.mango.dsl.mango.Model
import io.pelle.mango.dsl.mango.PackageDeclaration

class ClientNameUtils extends NameUtils {
	
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

	
}
