package io.pelle.mango.server.search;

import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.client.base.vo.query.SelectQuery;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class SerchServiceImpl {

	@Autowired(required = false)
	private List<SearchIndexBuilder> searchIndexBuilders = new ArrayList<SearchIndexBuilder>();

	private SelectQuery<? extends IVOEntity> getQuery(SearchIndexBuilder searchIndexBuilder) {

	}

}
