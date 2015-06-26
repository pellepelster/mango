package io.pelle.mango.client;

import io.pelle.mango.cli.BaseMangoCliApplicationContext;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({ "MangoSpringInvokerClientServices-gen.xml" })
public class TestApplicationContext extends BaseMangoCliApplicationContext {
}
