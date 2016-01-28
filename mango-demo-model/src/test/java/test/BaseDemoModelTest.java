package io.pelle.mango.demo.model.test;

import org.junit.runner.RunWith;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.pelle.mango.db.MangoDBApplicationContext;
import io.pelle.mango.db.test.BaseDBTest;
import io.pelle.mango.demo.MangoDemoBaseApplicationContextGen;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/MangoApplicationContext.xml" })
@Import(value = { MangoDemoBaseApplicationContextGen.class, MangoDBApplicationContext.class })
public abstract class BaseDemoModelTest extends BaseDBTest {

	public BaseDemoModelTest() {
	}

}