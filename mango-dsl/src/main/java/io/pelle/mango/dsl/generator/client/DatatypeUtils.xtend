package io.pelle.mango.dsl.generator.client

import io.pelle.mango.dsl.mango.BaseDataType
import io.pelle.mango.dsl.mango.BaseDataTypeLabel
import io.pelle.mango.dsl.mango.BaseDataTypeWidth

class DatatypeUtils {

	def <T> getEntityOption(BaseDataType baseDatatype, Class<T> datatypePropertyType) {
		if (baseDatatype.baseDatatypeProperties != null) {
			return baseDatatype.baseDatatypeProperties.findFirst[e|datatypePropertyType.isAssignableFrom(e.class)] as T
		} else {
			return null;
		}
	}
	
	def <T> boolean hasEntityOption(BaseDataType baseDatatype, Class<T> datatypePropertyType) {
		return getEntityOption(baseDatatype, datatypePropertyType) != null;
	}
	
	def boolean hasLabel(BaseDataType baseDatatype) {
		return getEntityOption(baseDatatype, BaseDataTypeLabel) != null;
	}
	
	def getLabel(BaseDataType baseDatatype) {
		return getEntityOption(baseDatatype, BaseDataTypeLabel).label;
	}
	
	def boolean hasWidth(BaseDataType baseDatatype) {
		return getEntityOption(baseDatatype, BaseDataTypeWidth) != null && getEntityOption(baseDatatype, BaseDataTypeWidth).width > 0;
	}
	
	def getWidth(BaseDataType baseDatatype) {
		return getEntityOption(baseDatatype, BaseDataTypeWidth).width;
	}
	
}
