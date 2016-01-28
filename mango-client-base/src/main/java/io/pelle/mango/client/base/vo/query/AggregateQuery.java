package io.pelle.mango.client.base.vo.query;

import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IVOEntity;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class AggregateQuery<T extends IVOEntity> extends BaseQuery<T, AggregateQuery<T>> {

	private List<IAttributeDescriptor<?>> sums = new ArrayList<IAttributeDescriptor<?>>();

	public AggregateQuery() {
		super();
	}

	public AggregateQuery(Class<? extends IVOEntity> from) {
		getFroms().add(new Entity(aliasProvider, from.getName()));
	}

	public static final <T extends IVOEntity> AggregateQuery<T> aggregateFrom(Class<T> entity) {
		return new AggregateQuery<T>(entity);
	}

	public AggregateQuery<T> from(Class<? extends IVOEntity> from) {
		getFroms().add(new Entity(aliasProvider, from.getName()));
		return this;
	}

	@Override
	public AggregateQuery<T> getQuery() {
		return this;
	}

	public AggregateQuery<T> sum(IAttributeDescriptor<?> attributeDescriptor) {
		sums.add(attributeDescriptor);
		return this;
	}

	public List<IAttributeDescriptor<?>> getSums() {
		return sums;
	}

}
