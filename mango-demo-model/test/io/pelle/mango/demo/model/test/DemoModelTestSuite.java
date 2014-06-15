package io.pelle.mango.demo.model.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DBUtilTest.class, DemoBaseEntityDAOTest.class,
		DemoBaseVODAOTest.class, DictionaryModelGeneratorTest.class,
		EntityModelGeneratorTest.class, EntityVOMapperTest.class,
		ValueObjectModelGeneratorTest.class })
public class DemoModelTestSuite {

}