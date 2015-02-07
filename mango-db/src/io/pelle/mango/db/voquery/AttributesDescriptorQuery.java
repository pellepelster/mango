package io.pelle.mango.db.voquery;

import io.pelle.mango.client.base.vo.AttributeDescriptor;
import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.server.base.query.BaseCollectionQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;

public class AttributesDescriptorQuery<T extends IAttributeDescriptor<?>> extends BaseCollectionQuery<T> {

	private Class<? extends IVOEntity> voEntityClass;

	public AttributesDescriptorQuery(Class<? extends IVOEntity> voEntityClass, Collection<T> attributeDescriptors) {
		super(attributeDescriptors);
		this.voEntityClass = voEntityClass;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static AttributesDescriptorQuery<?> createQuery(Class<? extends IVOEntity> voEntityClass, IAttributeDescriptor<?>[] attributeDescriptors) {
		return new AttributesDescriptorQuery(voEntityClass, Arrays.asList(attributeDescriptors));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <C extends IAttributeDescriptor<?>> AttributesDescriptorQuery<C> byType(Class<C> clazz) {
		return new AttributesDescriptorQuery(voEntityClass, Collections2.filter(getCollection(), Predicates.instanceOf(clazz)));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <C extends IAttributeDescriptor<?>> AttributesDescriptorQuery<C> byName(final String attributeName) {
		return new AttributesDescriptorQuery(voEntityClass, Collections2.filter(getCollection(),
				io.pelle.mango.db.voquery.Predicates.attributeDescriptorByName(attributeName)));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AttributesDescriptorQuery<T> naturalKeys() {

		List<T> naturalKeyAttributeDescriptors = new ArrayList(Collections2.filter(getCollection(), new Predicate<T>() {
			@Override
			public boolean apply(T atttributeDescriptor) {
				return atttributeDescriptor.getNaturalKeyOrder() != AttributeDescriptor.NO_NATURAL_KEY;
			}
		}));

		Collections.sort(naturalKeyAttributeDescriptors, new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				return Integer.compare(o1.getNaturalKeyOrder(), o2.getNaturalKeyOrder());
			}
		});

		return new AttributesDescriptorQuery(voEntityClass, naturalKeyAttributeDescriptors);
	}

}
