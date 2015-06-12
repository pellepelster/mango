package io.pelle.mango.server.search;

import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.client.base.vo.StringAttributeDescriptor;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.client.search.ISearchService;
import io.pelle.mango.client.search.SearchResultItem;
import io.pelle.mango.server.search.SearchIndexBuilder.DictionaryIndex;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;

public class SearchServiceImpl implements ISearchService {

	@Autowired
	private IBaseEntityService baseEntityService;

	@Autowired(required = false)
	private List<SearchIndexBuilder> searchIndexBuilders = new ArrayList<SearchIndexBuilder>();

	private SelectQuery<? extends IBaseVO> getSelectQuery(
			DictionaryIndex voEntityIndex, String value) {

		SelectQuery<? extends IBaseVO> query = (SelectQuery<? extends IBaseVO>) SelectQuery.selectFrom(voEntityIndex
				.getVOEntityClass());

		for (IAttributeDescriptor<?> attributeDescriptor : voEntityIndex
				.getAttributeDescriptors()) {

			if (attributeDescriptor instanceof StringAttributeDescriptor) {
				StringAttributeDescriptor stringAttributeDescriptor = (StringAttributeDescriptor) attributeDescriptor;
				query.addWhereOr(stringAttributeDescriptor.eq(value));
			}

		}

		return query;
	}

	private SearchIndexBuilder getSearchIndexBuilder(final String indexId) {

		Optional<SearchIndexBuilder> result = Iterables.tryFind(
				searchIndexBuilders, new Predicate<SearchIndexBuilder>() {

					@Override
					public boolean apply(SearchIndexBuilder input) {
						return input.getIndexId().equals(indexId);
					}
				});

		if (result.isPresent()) {
			return result.get();
		} else {
			throw new RuntimeException(String.format(
					"search index with id '%s' not found", indexId));
		}
	}

	@Override
	public List<SearchResultItem> search(String indexId, String search) {

		List<SearchResultItem> searchResults = new ArrayList<SearchResultItem>();

		for (final DictionaryIndex dictionaryIndex : getSearchIndexBuilder(
				indexId).getVOEntities()) {

			SelectQuery<? extends IBaseVO> selectQuery = getSelectQuery(dictionaryIndex, search);

			List<? extends IBaseVO> result = baseEntityService.filter(selectQuery);

			searchResults.addAll(Collections2.transform(result,
					new Function<IVOEntity, SearchResultItem>() {

						@Override
						public SearchResultItem apply(IVOEntity input) {
							SearchResultItem searchResultItem = new SearchResultItem();

							searchResultItem.setDictionaryId(dictionaryIndex
									.getDictionaryId());
							searchResultItem.setId(input.getId());
							return searchResultItem;
						}
					}));

		}

		return searchResults;
	}

}
