package io.pelle.mango.client.base.vo;

public class EntityDescriptor<T extends IVOEntity> implements IEntityDescriptor<T> {

	private Class<? extends IVOEntity> voEntityClass;

	private String label;

	private String pluralLabel;

	public EntityDescriptor(Class<? extends IVOEntity> voEntityClass, String label, String pluralLabel) {
		super();
		this.voEntityClass = voEntityClass;
		this.label = label;
		this.pluralLabel = pluralLabel;
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

}
