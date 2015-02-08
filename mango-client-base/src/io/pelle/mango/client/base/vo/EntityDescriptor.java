package io.pelle.mango.client.base.vo;

import com.google.common.base.Objects;

@SuppressWarnings("serial")
public class EntityDescriptor<T extends IVOEntity> implements IEntityDescriptor<T> {

	private Class<? extends IVOEntity> voEntityClass;

	private String label;

	private String pluralLabel;

	private String name;

	public EntityDescriptor() {
	}

	public EntityDescriptor(Class<? extends IVOEntity> voEntityClass, String name, String label, String pluralLabel) {
		super();
		this.voEntityClass = voEntityClass;
		this.name = name;
		this.label = label != null && !label.isEmpty() ? label : null;
		this.pluralLabel = pluralLabel != null && !pluralLabel.isEmpty() ? pluralLabel : null;
	}

	@Override
	public Class<? extends IVOEntity> getVOEntityClass() {
		return voEntityClass;
	}

	@Override
	public IMetaDescriptor getParent() {
		return null;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public String getPluralLabel() {
		return pluralLabel;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("class", voEntityClass).add("label", label).add("pluralLabel", pluralLabel).toString();
	}

	@Override
	public String getName() {
		return name;
	}

}
