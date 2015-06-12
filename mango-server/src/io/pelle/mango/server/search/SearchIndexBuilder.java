package io.pelle.mango.server.search;

import io.pelle.mango.client.base.modules.dictionary.model.IDictionaryModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.db.voquery.AttributesDescriptorQuery;

import java.util.ArrayList;
import java.util.List;

public class SearchIndexBuilder {

	public class DictionaryIndex {

		private IDictionaryModel dictionaryModel;

		private List<IAttributeDescriptor<?>> attributeDescriptors = new ArrayList<IAttributeDescriptor<?>>();

		public DictionaryIndex(IDictionaryModel dictionaryModel) {
			this.dictionaryModel = dictionaryModel;
		}
		
		public DictionaryIndex addAttributes(IBaseControlModel... baseControlModels) {
			
			for (IBaseControlModel baseControlModel : baseControlModels) {
				
				AttributesDescriptorQuery< ? extends IAttributeDescriptor<?>> query = AttributesDescriptorQuery.createQuery(dictionaryModel.getVOClass()).byName(baseControlModel.getAttributePath());
				
				if (query.hasExactlyOne()) {
					this.attributeDescriptors.add(query.getSingleResult());
				} else {
					throw new RuntimeException(String.format("entity vo class '%s' has no attribute descriptor for path '%s'", dictionaryModel.getVOClass(), baseControlModel.getAttributePath()));
				}
			}
			
			return this;
		}
		
		public DictionaryIndex addAttributes(IAttributeDescriptor<?>... attributeDescriptors) {

			for (IAttributeDescriptor<?> attributeDescriptor : attributeDescriptors) {
				if (AttributesDescriptorQuery.createQuery(dictionaryModel.getVOClass()).has(attributeDescriptor)) {
					this.attributeDescriptors.add(attributeDescriptor);
				} else {
					throw new RuntimeException(String.format("entity vo class '%s' has no attribute descriptor '%s'", dictionaryModel.getVOClass(), attributeDescriptor));
				}
			}

			return this;
		}

		public Class<? extends IVOEntity> getVOEntityClass() {
			return dictionaryModel.getVOClass();
		}
		
		public List<IAttributeDescriptor<?>> getAttributeDescriptors() {
			return attributeDescriptors;
		}

		public String getDictionaryId() {
			return dictionaryModel.getName();
		}
	}
	
	
	private String indexId;

	private List<DictionaryIndex> voEntities = new ArrayList<DictionaryIndex>();


	public SearchIndexBuilder(String searchIndexId) {
		super();
		this.indexId = searchIndexId;
	}

	public static SearchIndexBuilder createBuilder(String searchIndexId) {
		return new SearchIndexBuilder(searchIndexId);
	}

	public DictionaryIndex forDictionary(IDictionaryModel dictionaryModel) {
		
		DictionaryIndex searchIndexBuilderEntity = new DictionaryIndex(dictionaryModel); 
		voEntities.add(searchIndexBuilderEntity);
		return searchIndexBuilderEntity;
	}

	public String getIndexId() {
		return indexId;
	}
	
	public List<DictionaryIndex> getVOEntities() {
		return voEntities;
	}
}
