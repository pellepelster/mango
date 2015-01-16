package io.pelle.mango.client.core.property;

import io.pelle.mango.client.base.property.IProperty;
import io.pelle.mango.client.base.property.IPropertyCategory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class PropertyCategoryBuilder extends BasePropertiesBuilder implements IPropertyCategory {

	private List<PropertyCategoryBuilder> categories = new ArrayList<PropertyCategoryBuilder>();

	private List<PropertyGroupBuilder> groups = new ArrayList<PropertyGroupBuilder>();

	private Predicate<BasePropertiesBuilder> getByName(final String name) {
		return new Predicate<BasePropertiesBuilder>() {

			@Override
			public boolean apply(BasePropertiesBuilder input) {
				return Objects.equal(input.getName(), name);
			}
		};
	}

	public PropertyCategoryBuilder(String name) {
		super(name);
	}

	public PropertyCategoryBuilder createCategory(String name) {

		Optional<PropertyCategoryBuilder> category = Iterables.tryFind(categories, getByName(name));

		if (!category.isPresent()) {
			category = Optional.of(new PropertyCategoryBuilder(name));
			categories.add(category.get());
		}

		return category.get();
	}

	public PropertyGroupBuilder createGroup(String name) {

		Optional<PropertyGroupBuilder> group = Iterables.tryFind(groups, getByName(name));

		if (!group.isPresent()) {
			group = Optional.of(new PropertyGroupBuilder(name));
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

	// PropertyCategoryBuilder(PropertyCategory category) {
	// super(category);
	// }

	// private PropertyCategory category;
	//
	// public BaseCategoryBuilder(PropertyCategory category) {
	// super();
	// this.category = category;
	// }
	//
	// public PropertyCategoryBuilder createCategory(String categoryName) {
	//
	// Optional<PropertyCategory> optionalCategory = null;
	// //Iterables.tryFind(category.getPropertyCategories(),
	// PropertyService1.categoryByName(categoryName));
	//
	// if (!optionalCategory.isPresent()) {
	// optionalCategory = Optional.of(new PropertyCategory());
	// optionalCategory.get().setName(categoryName);
	// category.getPropertyCategories().add(optionalCategory.get());
	// }
	//
	// return new PropertyCategoryBuilder(optionalCategory.get());
	// }
	//
	//
	// protected PropertyCategory getCategory() {
	// return category;
	// }
}
