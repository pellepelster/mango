package io.pelle.mango.client.core.property;

import io.pelle.mango.client.base.property.IProperty;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class PropertyBuilder {

	private List<IProperty<?>> properties = new ArrayList<IProperty<?>>();

	public List<AsyncCallback<IProperty<?>>> callbacks = new ArrayList<AsyncCallback<IProperty<?>>>();

	private static PropertyBuilder instance = null;

	public PropertyBuilder() {
	}

	public static PropertyBuilder getInstance() {

		if (instance == null) {
			instance = new PropertyBuilder();
		}

		return instance;
	}

	private void addPropertyInternal(IProperty<?> property) {
		properties.add(property);

		for (AsyncCallback<IProperty<?>> callback : callbacks) {
			callback.onSuccess(property);
		}
	}

	public StringPropertyBuilder createStringProperty(String key) {
		StringPropertyBuilder result = new StringPropertyBuilder(key);
		addPropertyInternal(result);
		return result;
	}

	public IntegerPropertyBuilder createIntegerProperty(String key) {
		IntegerPropertyBuilder result = new IntegerPropertyBuilder(key);
		addPropertyInternal(result);
		return result;
	}

	public BooleanPropertyBuilder createBooleanProperty(String key) {
		BooleanPropertyBuilder result = new BooleanPropertyBuilder(key);
		addPropertyInternal(result);
		return result;
	}

	public void addPropertyCallback(AsyncCallback<IProperty<?>> callback) {

		callbacks.add(callback);

		for (IProperty<?> property : properties) {
			callback.onSuccess(property);
		}

	}

	public Optional<IProperty<?>> getProperty(final String key) {

		return Iterables.tryFind(properties, new Predicate<IProperty<?>>() {
			@Override
			public boolean apply(IProperty<?> property) {
				return property.getKey().equals(key);
			}
		});
	}
}
