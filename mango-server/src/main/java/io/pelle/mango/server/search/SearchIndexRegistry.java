package io.pelle.mango.server.search;

import io.pelle.mango.db.dao.IBaseEntityDAO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class SearchIndexRegistry {

	@Autowired
	private IBaseEntityDAO baseEntityDAO;

	private List<SearchIndexBuilder> searchIndexBuilders = new ArrayList<SearchIndexBuilder>();

	@Autowired(required = false)
	public void addSearchIndexBuilders(List<SearchIndexBuilder> searchIndexBuilders) {

		for (SearchIndexBuilder searchIndexBuilder : searchIndexBuilders) {
			baseEntityDAO.registerCallback(null);
		}

	}

}
