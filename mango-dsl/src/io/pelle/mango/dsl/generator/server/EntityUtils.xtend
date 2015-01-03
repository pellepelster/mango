package io.pelle.mango.dsl.generator.server

import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.EntityDisableIdField
import io.pelle.mango.dsl.mango.EntityLabelField
import io.pelle.mango.dsl.mango.EntityOptionsContainer
import io.pelle.mango.dsl.mango.EntityPluralLabelField

class EntityUtils {

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
		return entity.getEntityOption(typeof(EntityLabelField)) != null
	}

	def <T> getLabel(Entity entity) {
		return entity.getEntityOption(typeof(EntityLabelField)).label
	}

	def <T> hasPluralLabel(Entity entity) {
		return entity.getEntityOption(typeof(EntityPluralLabelField)) != null
	}

	def <T> getPluralLabel(Entity entity) {
		return entity.getEntityOption(typeof(EntityPluralLabelField)).pluralLabel
	}

	def <T> entityDisableIdField(Entity entity) {
		return Boolean.TRUE.equals(entity.getEntityOption(typeof(EntityDisableIdField)))
	}
}
