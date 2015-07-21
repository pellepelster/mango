package io.pelle.mango.demo.server;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import io.pelle.mango.db.test.BaseDBTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { io.pelle.mango.demo.server.MangoDemoTestApplicationContext.class })
@Transactional
public abstract class BaseDemoTest extends BaseDBTest {

	public BaseDemoTest() {
		super("mangodemo");
	}
}