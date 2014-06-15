package io.pelle.mango.client.base.vo;

public class AttributeDescriptor<AttributeType> implements IAttributeDescriptor<AttributeType> {

	private final IEntityDescriptor<?> parent;

	private final String attributeName;

	private final Class<?> attributeType;

	private final Class<?> attributeListType;

	public AttributeDescriptor(IEntityDescriptor<?> parent, String attributeName, Class<?> attributeType) {
		this(parent, attributeName, attributeType, attributeType);
	}

	public AttributeDescriptor(IEntityDescriptor<?> parent, String attributeName, Class<?> attributeType, Class<?> attributeListType) {
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

	@Override
	public IEntityDescriptor<?> getParent() {
		return parent;
	}

}