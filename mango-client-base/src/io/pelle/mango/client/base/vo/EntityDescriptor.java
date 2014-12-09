package io.pelle.mango.client.base.vo;

public class EntityDescriptor<T extends IVOEntity> implements IEntityDescriptor<T> {

	private Class<? extends IVOEntity> voEntityClass;

	public EntityDescriptor(Class<? extends IVOEntity> voEntityClass) {
		super();
		this.voEntityClass = voEntityClass;
	}

	@Override
	public Class<? extends IVOEntity> getVOEntityClass() {
		return voEntityClass;
	}

	@Override
	public IMetaDescriptor getParent() {
		return null;
	}

}
