package io.pelle.mango.db.test;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Profile;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = false)
@Transactional
public abstract class BaseDBTest extends AbstractTransactionalJUnit4SpringContextTests {

	public static final String TESTCLIENT_NAME = "testclient";

	public static final String TESTUSER_NAME = "testuser";

	public BaseDBTest(String jndiName) {
		super();

		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		dataSource.setUrl("jdbc:hsqldb:mem:" + jndiName);

		SimpleNamingContextBuilder builder;
		try {
			builder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		builder.bind(String.format("java:comp/env/jdbc/%s", jndiName), dataSource);

	}

}