package io.pelle.mango.client.base.property;

import java.util.Collection;

public interface IPropertyCategory extends IBaseProperty {

	IPropertyGroup createGroup(String name);

	Collection<IProperty<?>> getAllProperties();

}
