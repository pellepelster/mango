package io.pelle.mango.db.test;

import java.util.UUID;

import javax.sql.DataSource;

import org.junit.runner.RunWith;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
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

//		BasicDataSource dataSource = new BasicDataSource();
//
//		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
//		dataSource.setUrl("jdbc:hsqldb:mem:example");

		SimpleNamingContextBuilder builder;
		try {
			builder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		String tempDir = System.getProperty("java.io.tmpdir");

		DataSource ds = new SingleConnectionDataSource(String.format("jdbc:hsqldb:mem:example"), jndiName, "", true);
		builder.bind(String.format("java:comp/env/jdbc/%s", jndiName), ds);

	}

}