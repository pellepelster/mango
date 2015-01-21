package io.pelle.mango.client.base.property;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface IPropertyCategory extends IBaseProperty {

	IPropertyGroup createGroup(String name);

	Collection<IProperty<?>> getAllProperties();

	List<IProperty<? extends Serializable>> getProperties();

	List<IPropertyCategory> getCategories();

	String getId();

	String getName();

	void setName(String name);

}
