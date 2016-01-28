package io.pelle.mango.demo.model.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import io.pelle.mango.db.voquery.EntityClassQuery;
import io.pelle.mango.demo.server.test.Entity1;
import io.pelle.mango.demo.server.test.Entity2;

public class EntityClassQueryTest {

	@Test
	public void testGetElementCollections() {

		Entity1 entity = new Entity1();

		List<String> result = EntityClassQuery.createQuery(entity).getElementCollections();
		assertEquals(2, result.size());
		assertThat(result, containsInAnyOrder("entity1_stringdatatype1list", "entity1_enumeration1datatypes"));
	}

	@Test
	public void testGetOneToManyJoinTables() {

		Entity2 entity = new Entity2();

		List<String> result = EntityClassQuery.createQuery(entity).getOneToManyJoinTables();
		assertEquals(1, result.size());
		assertThat(result, containsInAnyOrder("entity2_entity2_entity3datatypes_entity3"));
	}

}