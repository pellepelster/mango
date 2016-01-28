package io.pelle.mango.db.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
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

	public BaseDBTest() {
		super();
	}

}