package io.pelle.mango.db.vo;

import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IBaseVO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Collections2;

public class AttributesDescriptorQuery<T> implements Iterable<IAttributeDescriptor<T>> {

	private Class<? extends IBaseVO> voClass;

	private List<IAttributeDescriptor<T>> attributeDescriptors = new ArrayList<>();

	public AttributesDescriptorQuery(Class<? extends IBaseVO> voClass, Collection<IAttributeDescriptor<T>> attributeDescriptors) {
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
	public <C> AttributesDescriptorQuery<C> byType(Class<C> clazz) {
		return new AttributesDescriptorQuery(voClass, Collections2.filter(attributeDescriptors, VOClassPredicates.attributeDescriptorAttributeType(clazz)));
	}

	public List<IAttributeDescriptor<T>> getList() {
		return attributeDescriptors;
	}

	@SuppressWarnings("unchecked")
	public <A> List<AttributeDescriptorAnnotation<A>> byAnnotation(Class<A> annotationClass) {
		Collection<AttributeDescriptorAnnotation<?>> result = Collections2.transform(attributeDescriptors, VOClassFunctions.attributeDescriptorAnnotation(voClass, annotationClass));
		result = Collections2.filter(result, VOClassPredicates.attributeDescriptorAnnotationType(annotationClass));
		return new ArrayList(result);
	}

	@Override
	public Iterator<IAttributeDescriptor<T>> iterator() {
		return attributeDescriptors.iterator();
	}

}
