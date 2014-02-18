package io.pelle.mango.server.validator;

import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.db.util.BeanUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AnnotationIterator implements Iterable<IAttributeDescriptor<?>>, Iterator<IAttributeDescriptor<?>>
{
	private List<IAttributeDescriptor<?>> attributeDescriptors = new ArrayList<IAttributeDescriptor<?>>();

	public AnnotationIterator(Class<? extends IBaseVO> voClass, @SuppressWarnings("rawtypes") Class annotationClass)
	{
		this.attributeDescriptors = BeanUtils.getAnnotatedAttributes(voClass, annotationClass);
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasNext()
	{
		return this.attributeDescriptors.iterator().hasNext();
	}

	/** {@inheritDoc} */
	@Override
	public Iterator<IAttributeDescriptor<?>> iterator()
	{
		return this.attributeDescriptors.iterator();
	}

	/** {@inheritDoc} */
	@Override
	public IAttributeDescriptor<?> next()
	{
		return this.attributeDescriptors.iterator().next();
	}

	/** {@inheritDoc} */
	@Override
	public void remove()
	{
		throw new RuntimeException("not implemented");
	}

}
