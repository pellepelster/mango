package io.pelle.mango.demo.server.util;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import io.pelle.mango.demo.server.MangoDemoTestApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MangoDemoTestApplicationContext.class })
@TransactionConfiguration(defaultRollback = false)
@Transactional()
@ActiveProfiles("test")
public abstract class BaseDemoTest extends AbstractTransactionalJUnit4SpringContextTests {
}