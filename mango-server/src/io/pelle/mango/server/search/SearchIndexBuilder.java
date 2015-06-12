package io.pelle.mango.server.search;

import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.db.voquery.AttributesDescriptorQuery;

import java.util.ArrayList;
import java.util.List;

public class SearchIndexBuilder {

	private String searchIndexId;

	private Class<? extends IVOEntity> voEntityClass;

	private List<IAttributeDescriptor<?>> attributeDescriptors = new ArrayList<IAttributeDescriptor<?>>();

	public SearchIndexBuilder(String searchIndexId) {
		super();
		this.searchIndexId = searchIndexId;
	}

	public static SearchIndexBuilder createIndex(String searchIndexId) {
		return new SearchIndexBuilder(searchIndexId);
	}

	public SearchIndexBuilder forEntity(Class<? extends IVOEntity> voEntityClass) {
		this.voEntityClass = voEntityClass;
		return this;
	}

	public SearchIndexBuilder addAttributes(IAttributeDescriptor<?>... attributeDescriptors) {

		for (IAttributeDescriptor<?> attributeDescriptor : attributeDescriptors) {
			if (AttributesDescriptorQuery.createQuery(voEntityClass).has(attributeDescriptor)) {
				this.attributeDescriptors.add(attributeDescriptor);
			} else {
				throw new RuntimeException(String.format("entity vo class '%s' has no attribute descriptor '%s'", voEntityClass, attributeDescriptor));
			}
		}

		return this;
	}
}
