package io.pelle.mango.demo.server;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.junit.runner.RunWith;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/MangoDemoApplicationContext.xml", "classpath:/MangoApplicationContext.xml", "classpath:/MangoTestApplicationContext.xml", "classpath:/MangoDemoApplicationContext.xml",
		"classpath:/DBBaseApplicationContext.xml", "classpath:/MangoDemoDB-gen.xml", "classpath:/MangoDemoSpringServices-gen.xml", "classpath:/MangoLoggerApplicationContext.xml", "classpath:/MangoDemoBaseApplicationContext-gen.xml",
		"classpath:/MangoSpringServices-gen.xml" })
@TransactionConfiguration(defaultRollback = false)
@Transactional
public abstract class BaseDemoTest extends AbstractTransactionalJUnit4SpringContextTests {

	public BaseDemoTest() {

		BasicDataSource dataSource = new BasicDataSource();

		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		dataSource.setUrl("jdbc:hsqldb:mem:example");

		SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
		builder.bind("java:comp/env/jdbc/mangodemo", dataSource);
		try {
			builder.activate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}