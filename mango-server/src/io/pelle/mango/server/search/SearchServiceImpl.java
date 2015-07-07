package io.pelle.mango.server.search;

import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.StringAttributeDescriptor;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.client.search.ISearchService;
import io.pelle.mango.client.search.SearchResultItem;
import io.pelle.mango.client.web.modules.dictionary.base.DictionaryUtil;
import io.pelle.mango.server.search.SearchIndexBuilder.DictionaryIndex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class SearchServiceImpl implements ISearchService {

	private static final int MAX_SEARCH_RESULTS = 50;

	@Autowired
	private IBaseEntityService baseEntityService;

	private List<SearchIndexBuilder> searchIndexBuilders = new ArrayList<SearchIndexBuilder>();

	private SearchIndexBuilder defaultSearchIndexBuilder = null;

	private Optional<SelectQuery<? extends IBaseVO>> getSelectQuery(DictionaryIndex voEntityIndex, String value) {

		SelectQuery<? extends IBaseVO> query = (SelectQuery<? extends IBaseVO>) SelectQuery.selectFrom(voEntityIndex.getVOEntityClass());

		for (IAttributeDescriptor<?> attributeDescriptor : voEntityIndex.getAttributeDescriptors()) {

			if (attributeDescriptor instanceof StringAttributeDescriptor) {
				StringAttributeDescriptor stringAttributeDescriptor = (StringAttributeDescriptor) attributeDescriptor;
				query.addWhereOr(stringAttributeDescriptor.caseInsensitiveLike(value));
			} else {
				return Optional.absent();
			}
		}

		return Optional.<SelectQuery<? extends IBaseVO>> of(query);
	}

	private SearchIndexBuilder getSearchIndexBuilder(final String indexId) {

		Optional<SearchIndexBuilder> result = Iterables.tryFind(searchIndexBuilders, new Predicate<SearchIndexBuilder>() {

			@Override
			public boolean apply(SearchIndexBuilder input) {
				return input.getIndexId().equals(indexId);
			}
		});

		if (result.isPresent()) {
			return result.get();
		} else {

			if (indexId == null) {
				if (defaultSearchIndexBuilder != null) {
					return defaultSearchIndexBuilder;
				} else {
					throw new RuntimeException(String.format("no default search not found", indexId));
				}
			} else {
				throw new RuntimeException(String.format("search index with id '%s' not found", indexId));
			}
		}
	}

	@Override
	public List<SearchResultItem> search(String indexId, String search) {

		if (StringUtils.isEmpty(search)) {
			return Collections.emptyList();
		}

		List<SearchResultItem> searchResults = new ArrayList<SearchResultItem>();

		for (final DictionaryIndex dictionaryIndex : getSearchIndexBuilder(indexId).getVOEntities()) {

			Optional<SelectQuery<? extends IBaseVO>> selectQuery = getSelectQuery(dictionaryIndex, search);

			if (selectQuery.isPresent()) {
				selectQuery.get().loadNaturalKeyReferences(true);
				List<? extends IBaseVO> result = baseEntityService.filter(selectQuery.get());

				searchResults.addAll(Collections2.transform(result, new Function<IBaseVO, SearchResultItem>() {

					@Override
					public SearchResultItem apply(IBaseVO baseVO) {
						SearchResultItem searchResultItem = new SearchResultItem();

						searchResultItem.setDictionaryId(dictionaryIndex.getDictionaryId());
						searchResultItem.setId(baseVO.getId());
						searchResultItem.setLabel(DictionaryUtil.getLabel(dictionaryIndex.getDictionaryModel(), baseVO));

						for (IAttributeDescriptor<?> attributeDescriptor : dictionaryIndex.getAttributeDescriptors()) {
							String attributeName = attributeDescriptor.getAttributeName();
							searchResultItem.getAttributes().put(attributeName, Objects.toString(baseVO.get(attributeName)));
						}

						return searchResultItem;
					}
				}));
			}

		}

		return Lists.newArrayList(Iterables.limit(searchResults, MAX_SEARCH_RESULTS));
	}

	@Autowired(required = false)
	public void setSearchIndexBuilders(List<SearchIndexBuilder> searchIndexBuilders) {
		this.searchIndexBuilders = searchIndexBuilders;

		for (SearchIndexBuilder searchIndexBuilder : searchIndexBuilders) {
			if (searchIndexBuilder.isDefault()) {
				defaultSearchIndexBuilder = searchIndexBuilder;
			}
		}
	}

	@Override
	public List<SearchResultItem> searchDefaultIndex(String searchText) {
		return search(null, searchText);
	}

}
