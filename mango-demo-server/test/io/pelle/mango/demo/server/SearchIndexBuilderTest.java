package io.pelle.mango.demo.server;

import io.pelle.mango.demo.client.test.Entity1VO;
import io.pelle.mango.demo.client.test.Entity2VO;
import io.pelle.mango.demo.server.test.Entity1;
import io.pelle.mango.server.search.SearchIndexBuilder;

import org.junit.Test;

public class SearchIndexBuilderTest {

	@Test
	public void testCreateSearchIndexForEntity() {
		SearchIndexBuilder.createIndex("index1").forEntity(Entity1.class).addAttributes(Entity1VO.STRINGDATATYPE1);
	}

	@Test
	public void testCreateSearchIndexForVO() {
		SearchIndexBuilder.createIndex("index2").forEntity(Entity1VO.class).addAttributes(Entity1VO.STRINGDATATYPE1);
	}

	@Test
	public void testCreateSearchIndexForVOInvalidAttribute() {
		SearchIndexBuilder.createIndex("index2").forEntity(Entity1VO.class).addAttributes(Entity2VO.STRINGDATATYPE2);
	}

}
