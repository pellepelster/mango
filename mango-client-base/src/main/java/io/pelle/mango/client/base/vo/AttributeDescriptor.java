package io.pelle.mango.client.base.vo;

import io.pelle.mango.client.base.vo.query.IBooleanExpression;

import com.google.common.base.Objects;
import com.google.common.base.Optional;

@SuppressWarnings("serial")
public class AttributeDescriptor<AttributeType> implements IAttributeDescriptor<AttributeType> {

	private final IMetaDescriptor<?> parent;

	private final String attributeName;

	private final Class<?> attributeType;

	private final Class<?> attributeListType;

	private final boolean mandatory;

	private final int naturalKeyOrder;

	public static final int NO_NATURAL_KEY = -1;

	public AttributeDescriptor(IMetaDescriptor<?> parent, String attributeName, Class<?> attributeType) {
		this(parent, attributeName, attributeType, attributeType, false, NO_NATURAL_KEY);
	}

	public AttributeDescriptor(IMetaDescriptor<?> parent, String attributeName, Class<?> attributeType, boolean mandatory) {
		this(parent, attributeName, attributeType, attributeType, mandatory, NO_NATURAL_KEY);
	}

	public AttributeDescriptor(IMetaDescriptor<?> parent, String attributeName, Class<?> attributeType, Class<?> attributeListType) {
		this(parent, attributeName, attributeType, attributeListType, false, NO_NATURAL_KEY);
	}

	public AttributeDescriptor(IMetaDescriptor<?> parent, String attributeName, Class<?> attributeType, Class<?> attributeListType, boolean mandatory, int naturalKeyOrder) {
		super();

		this.parent = parent;
		this.attributeName = attributeName;

		if (attributeType.equals(attributeListType)) {
			this.attributeType = attributeType;
			this.attributeListType = attributeType;
		} else {
			this.attributeType = attributeType;
			this.attributeListType = attributeListType;
		}

		this.mandatory = mandatory;
		this.naturalKeyOrder = naturalKeyOrder;
	}

	/** {@inheritDoc} */
	@Override
	public String getAttributeName() {
		return attributeName;
	}

	/** {@inheritDoc} */
	@Override
	public Class<?> getAttributeType() {
		return attributeType;
	}

	/** {@inheritDoc} */
	@Override
	public Class<?> getListAttributeType() {
		return attributeListType;
	}

	/** {@inheritDoc} */
	@Override
	public IMetaDescriptor<?> getParent() {
		return parent;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isMandatory() {
		return mandatory;
	}

	/** {@inheritDoc} */
	@Override
	public int getNaturalKeyOrder() {
		return naturalKeyOrder;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("attributeName", attributeName).add("attributeType", attributeType).toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T path(T attributeDescriptor) {

		if (attributeDescriptor instanceof StringAttributeDescriptor) {
			StringAttributeDescriptor stringAttributeDescriptor = (StringAttributeDescriptor) attributeDescriptor;
			return (T) stringAttributeDescriptor.cloneWithNewParent(this);
		} else if (attributeDescriptor instanceof EnumerationAttributeDescriptor) {
			EnumerationAttributeDescriptor enumerationAttributeDescriptor = (EnumerationAttributeDescriptor) attributeDescriptor;
			return (T) enumerationAttributeDescriptor.cloneWithNewParent(this);
		} else if (attributeDescriptor instanceof LongAttributeDescriptor) {
			LongAttributeDescriptor longAttributeDescriptor = (LongAttributeDescriptor) attributeDescriptor;
			return (T) longAttributeDescriptor.cloneWithNewParent(this);
		} else if (attributeDescriptor instanceof EntityAttributeDescriptor) {
			EntityAttributeDescriptor entityAttributeDescriptor = (EntityAttributeDescriptor) attributeDescriptor;
			return (T) entityAttributeDescriptor.cloneWithNewParent(this);
		}

		throw new RuntimeException("unsupported attribute descriptor type '" + attributeDescriptor.getClass() + "'");

	}

	@Override
	public IBooleanExpression eq(AttributeType value) {
		return null;
	}

	@Override
	public Optional<IBooleanExpression> search(String value) {
		return Optional.absent();
	}

	@Override
	public Class<AttributeType> getVOEntityClass() {
		return null;
	}

}