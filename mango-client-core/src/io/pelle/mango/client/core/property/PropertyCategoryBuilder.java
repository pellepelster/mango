package io.pelle.mango.client.core.property;

import io.pelle.mango.client.base.property.IProperty;
import io.pelle.mango.client.base.property.IPropertyCategory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class PropertyCategoryBuilder extends BasePropertiesBuilder implements IPropertyCategory, Serializable {

	private List<PropertyCategoryBuilder> categories = new ArrayList<PropertyCategoryBuilder>();

	private List<PropertyGroupBuilder> groups = new ArrayList<PropertyGroupBuilder>();

	private Predicate<BasePropertiesBuilder> getById(final String id) {
		return new Predicate<BasePropertiesBuilder>() {

			@Override
			public boolean apply(BasePropertiesBuilder input) {
				return Objects.equal(input.getId(), id);
			}
		};
	}

	public PropertyCategoryBuilder() {
		super();
	}

	public PropertyCategoryBuilder(String id) {
		super(id);
	}

	public PropertyCategoryBuilder createCategory(String id) {

		Optional<PropertyCategoryBuilder> category = Iterables.tryFind(categories, getById(id));

		if (!category.isPresent()) {
			category = Optional.of(new PropertyCategoryBuilder(id));
			categories.add(category.get());
		}

		return category.get();
	}

	public PropertyGroupBuilder createGroup(String id) {

		Optional<PropertyGroupBuilder> group = Iterables.tryFind(groups, getById(id));

		if (!group.isPresent()) {
			group = Optional.of(new PropertyGroupBuilder(id));
			groups.add(group.get());
		}

		return group.get();
	}

	@Override
	public Collection<IProperty<?>> getAllProperties() {

		Collection<IProperty<?>> result = new ArrayList<IProperty<?>>();
		result.addAll(getProperties());

		for (PropertyGroupBuilder propertyGroup : groups) {
			result.addAll(propertyGroup.getProperties());
		}

		return result;
	}

	@Override
	public List<IPropertyCategory> getCategories() {
		return Lists.newArrayList(Iterables.transform(categories, new Function<PropertyCategoryBuilder, IPropertyCategory>() {
			@Override
			public IPropertyCategory apply(PropertyCategoryBuilder input) {
				return input;
			}
		}));
	}

}
