package io.pelle.mango.dsl.generator.client

import com.google.inject.Inject
import io.pelle.mango.client.base.modules.dictionary.model.IEnumerationConverter
import io.pelle.mango.dsl.mango.Enumeration
import io.pelle.mango.dsl.mango.Model
import java.util.HashMap
import java.util.Map
import java.util.Collections

class EnumerationGenerator {

	@Inject
	extension ClientNameUtils

	def compileEnumeration(Enumeration enumeration) '''
		package «getPackageName(enumeration)»;
		
		public enum «enumeration.enumerationName» {
		
			«FOR enumerationValue : enumeration.enumerationValues SEPARATOR ", "»
				«enumerationValue.enumerationValueName»
			«ENDFOR»
		
		}
	'''

	def compileEnumerationValueParser(Model model) '''
		package «model.modelPackageName»;
		
		public class «model.enumerationValueParserName» implements «IEnumerationConverter.name» {
		
			«FOR enumeration : model.eAllContents.toIterable.filter(Enumeration)»
			@java.lang.SuppressWarnings("serial")
			private «Map.name»<«String.name», «String.name»> «enumeration.name.toUpperCase» = «Collections.name».unmodifiableMap(new «HashMap.name»<«String.name», «String.name»>() {
				{
					«FOR enumValue : enumeration.enumerationValues»
					put("«enumValue.enumerationValueName»", «IF enumValue.value == null»"«enumValue.enumerationValueName»"«ELSE»"«enumValue.value»"«ENDIF»);
					«ENDFOR»
				}
			});
			«ENDFOR»
			
			@Override
			public Object parseEnum(String enumerationName, String enumerationValue) {
		
			«FOR enumeration : model.eAllContents.toIterable.filter(Enumeration)»
			if ("«enumeration.enumerationFullQualifiedName»".equals(enumerationName)) {
				return «enumeration.enumerationFullQualifiedName».valueOf(enumerationValue);
			}
			«ENDFOR»
			
			return null;
			}
		
			@Override
			public «Map.name»<String, String> getEnumValues(String enumerationName) {
		
			«FOR enumeration : model.eAllContents.toIterable.filter(Enumeration)»
			if ("«enumeration.enumerationFullQualifiedName»".equals(enumerationName)) {
				return «enumeration.name.toUpperCase»;
			}
			«ENDFOR»
			
			return null;
			}
		}
	'''

}
