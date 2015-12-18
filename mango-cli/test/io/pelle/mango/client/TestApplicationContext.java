package io.pelle.mango.client;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.pelle.mango.MangoSpringInvokerClientServicesGen;
import io.pelle.mango.cli.BaseMangoCliApplicationContext;

@Configuration
@Import({ MangoSpringInvokerClientServicesGen.class })
public class TestApplicationContext extends BaseMangoCliApplicationContext {
}
