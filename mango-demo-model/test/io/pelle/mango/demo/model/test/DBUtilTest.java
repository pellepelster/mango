package io.pelle.mango.demo.model.test;

import static io.pelle.mango.client.base.vo.query.SelectQuery.selectFrom;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;
import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.db.util.DBUtil;
import io.pelle.mango.demo.client.test.Entity5VO;
import io.pelle.mango.demo.client.test.Entity6VO;
import io.pelle.mango.demo.server.test.Entity1;
import io.pelle.mango.demo.server.test.Entity2;

import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Test;

public class DBUtilTest extends TestCase {

	@Test
	public void testGetClassLoadAssociationsWithJoin() {

		SelectQuery<Entity1> query = selectFrom(Entity1.class).join(Entity1.ENTITY2DATATYPE, Entity2.ENTITY3DATATYPES);

		Map<Class<? extends IVOEntity>, Set<String>> classLoadAssociations = DBUtil.getLoadAssociations(query);

		assertThat(classLoadAssociations.get(Entity1.class), hasItem("entity2Datatype"));
		assertThat(classLoadAssociations.get(Entity2.class), hasItem("entity3Datatypes"));
	}

	@Test
	public void testGetClassLoadAssociationsFirstLevel() {

		SelectQuery<Entity1> query = selectFrom(Entity1.class);

		Map<Class<? extends IVOEntity>, Set<String>> classLoadAssociations = DBUtil.getLoadAssociations(query);

		assertThat(classLoadAssociations.get(Entity1.class), hasItem("entity2Datatype"));
	}

	@Test
	public void testGetClassLoadAssociationsFirstLevelWihtNaturalKey() {

		SelectQuery<Entity6VO> query = selectFrom(Entity6VO.class).loadNaturalKeyReferences(true);

		Map<Class<? extends IVOEntity>, Set<String>> classLoadAssociations = DBUtil.getLoadAssociations(query);

		assertThat(classLoadAssociations.get(Entity6VO.class), hasItem("entity5"));
		assertThat(classLoadAssociations.get(Entity5VO.class), hasItem("entity4"));
	}

}
