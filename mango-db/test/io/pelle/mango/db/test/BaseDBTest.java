package io.pelle.mango.db.test;

import java.util.UUID;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/DBBaseApplicationContext.xml" })
@TransactionConfiguration(defaultRollback = false)
@Transactional
public abstract class BaseDBTest extends AbstractTransactionalJUnit4SpringContextTests {

	public static final String TESTCLIENT_NAME = "testclient";

	public static final String TESTUSER_NAME = "testuser";

	public BaseDBTest(String jndiName) {
		super();

		SimpleNamingContextBuilder builder;
		try {
			builder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		String tempDir = System.getProperty("java.io.tmpdir");

		DataSource ds = new SingleConnectionDataSource(String.format("jdbc:derby:%s/%s_%s;create=true", tempDir, jndiName, UUID.randomUUID().toString()), jndiName, "", true);
		builder.bind(String.format("java:comp/env/jdbc/%s", jndiName), ds);

	}

	@Autowired
	private EntityManagerFactory entityManagerFactory;

}