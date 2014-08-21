package io.pelle.mango.demo.model.test;

import io.pelle.mango.db.test.BaseDBTest;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/MangoDemoDB-gen.xml", "classpath:/MangoDemoBaseApplicationContext-gen.xml", "classpath:/MangoBaseApplicationContext.xml" })
public abstract class BaseDemoModelTest extends BaseDBTest {

	public BaseDemoModelTest() {
		super("mangodemo");
	}

}
