package io.pelle.mango.db.voquery;

import io.pelle.mango.client.base.vo.AttributeDescriptor;
import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IBaseVO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;

public class AttributesDescriptorQuery<T extends IAttributeDescriptor<?>> implements Iterable<T> {

	private Class<? extends IBaseVO> voClass;

	private List<T> attributeDescriptors = new ArrayList<>();

	public AttributesDescriptorQuery(Class<? extends IBaseVO> voClass, Collection<T> attributeDescriptors) {
		this.attributeDescriptors = new ArrayList<>(attributeDescriptors);
		this.voClass = voClass;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static AttributesDescriptorQuery<?> createQuery(Class<? extends IBaseVO> voClass, IAttributeDescriptor<?>[] attributeDescriptors) {
		return new AttributesDescriptorQuery(voClass, Arrays.asList(attributeDescriptors));
	}

	public int getCount() {
		return attributeDescriptors.size();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <C extends IAttributeDescriptor<?>> AttributesDescriptorQuery<C> byType(Class<C> clazz) {
		return new AttributesDescriptorQuery(voClass, Collections2.filter(attributeDescriptors, Predicates.instanceOf(clazz)));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AttributesDescriptorQuery<T> naturalKeys() {
		
		List<T> naturalKeyAttributeDescriptors = new ArrayList(Collections2.filter(attributeDescriptors, new Predicate<T>() {
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
		
		return new AttributesDescriptorQuery(voClass, naturalKeyAttributeDescriptors);
	}

	
	@Override
	public Iterator<T> iterator() {
		return attributeDescriptors.iterator();
	}

}