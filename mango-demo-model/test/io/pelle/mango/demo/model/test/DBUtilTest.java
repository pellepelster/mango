package io.pelle.mango.demo.model.test;

import static io.pelle.mango.client.base.vo.query.SelectQuery.selectFrom;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.db.util.DBUtil;
import io.pelle.mango.test.server.Entity1;
import io.pelle.mango.test.server.Entity2;

import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Test;

public class DBUtilTest extends TestCase {

	@Test
	public void testGetClassLoadAssociationsWithJoin() {

		SelectQuery<Entity1> query = selectFrom(Entity1.class).join(Entity1.ENTITY2DATATYPE, Entity2.ENTITY3DATATYPES);

		Map<Class<?>, Set<String>> classLoadAssociations = DBUtil.getClassLoadAssociations(query);

		assertThat(classLoadAssociations.get(Entity1.class), hasItem("entity2Datatype"));
		assertThat(classLoadAssociations.get(Entity2.class), hasItem("entity3Datatypes"));
	}

	@Test
	public void testGetClassLoadAssociationsFirstLevel() {

		SelectQuery<Entity1> query = selectFrom(Entity1.class);

		Map<Class<?>, Set<String>> classLoadAssociations = DBUtil.getClassLoadAssociations(query);

		assertThat(classLoadAssociations.get(Entity1.class), hasItem("entity2Datatype"));
	}

}
