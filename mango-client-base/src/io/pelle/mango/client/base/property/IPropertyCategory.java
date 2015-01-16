package io.pelle.mango.client.base.property;

import java.util.Collection;
import java.util.List;

public interface IPropertyCategory extends IBaseProperty {

	IPropertyGroup createGroup(String name);

	Collection<IProperty<?>> getAllProperties();

	List<IProperty<?>> getProperties();

	List<IPropertyCategory> getCategories();

	String getName();

}
