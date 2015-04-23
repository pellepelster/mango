package io.pelle.mango.dsl.generator.server

import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.EntityDisableIdField
import io.pelle.mango.dsl.mango.EntityLabelField
import io.pelle.mango.dsl.mango.EntityOptionsContainer
import io.pelle.mango.dsl.mango.EntityPluralLabelField
import io.pelle.mango.dsl.mango.EntityNaturalKeyFields
import java.util.Collections
import io.pelle.mango.dsl.mango.EntityHierarchical

class EntityUtils {

	def compileNaturalKey(Entity entity) '''
		«IF entity.hasNaturalKeyFields»
			@java.lang.Override
			public String getNaturalKey() 
			{
				java.lang.StringBuffer sb = new java.lang.StringBuffer();
				«FOR naturalKeyAttribute : entity.naturalKeyFields SEPARATOR "sb.append(\", \");"»
				sb.append(this.get«naturalKeyAttribute.name.toFirstUpper()»());
				«ENDFOR»
				return sb.toString();
			}

			@Override
			public boolean hasNaturalKey() {
				return true;
			}
			
		«ENDIF»
	'''

	def <T> getEntityOption(EntityOptionsContainer entityOptionsContainer, Class<T> entityOptionType) {
		return entityOptionsContainer.options.findFirst[e|entityOptionType.isAssignableFrom(e.class)] as T
	}

	def <T> getEntityOption(Entity entity, Class<T> entityOptionType) {
		if (entity.entityOptions != null) {
			return getEntityOption(entity.entityOptions, entityOptionType)
		} else {
			return null;
		}
	}

	def <T> hasLabel(Entity entity) {
		return entity.getEntityOption(typeof(EntityLabelField)) != null && !entity.getEntityOption(typeof(EntityLabelField)).label.empty
	}

	def <T> getLabel(Entity entity) {
		if (hasLabel(entity)) {
			return entity.getEntityOption(typeof(EntityLabelField)).label
		} else {
			return null
		}
	}

	def <T> getNaturalKeyFields(Entity entity) {
		if (hasNaturalKeyFields(entity)) {
			return entity.getEntityOption(typeof(EntityNaturalKeyFields)).naturalKeyAttributes
		} else {
			Collections.emptyList
		}
	}

	def <T> hasPluralLabel(Entity entity) {
		return entity.getEntityOption(typeof(EntityPluralLabelField)) != null
	}

	def <T> hasNaturalKeyFields(Entity entity) {
		return entity.getEntityOption(typeof(EntityNaturalKeyFields)) != null
	}
	def <T> getPluralLabel(Entity entity) {
		if (hasPluralLabel(entity)) {
			return entity.getEntityOption(typeof(EntityPluralLabelField)).pluralLabel
		} else {
			return null
		}
	}

	def <T> entityDisableIdField(Entity entity) {
		return Boolean.TRUE.equals(entity.getEntityOption(typeof(EntityDisableIdField)))
	}

	def <T> entityHierarchical(Entity entity) {
		return Boolean.TRUE.equals(entity.getEntityOption(typeof(EntityHierarchical)))
	}
}
