package io.pelle.mango.demo.model.test;

import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.db.dao.IBaseEntityDAO;
import io.pelle.mango.demo.server.test.Entity1;

import org.junit.Ignore;
import org.junit.Test;

public class DocumentationCode {

	@Test
	@Ignore
	public void testDictionaryName() {

		IBaseEntityDAO dao = null;

		SelectQuery<Entity1> query = SelectQuery.selectFrom(Entity1.class).where(Entity1.STRINGDATATYPE1.eq("abc"));

		for (Entity1 entity1 : dao.filter(query)) {
			// do something
		}

	}

}
