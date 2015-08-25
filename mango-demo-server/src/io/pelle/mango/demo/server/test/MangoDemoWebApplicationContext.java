package io.pelle.mango.demo.server.test;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.pelle.mango.server.MangoWebApplicationContext;

@Configuration
@Import(MangoWebApplicationContext.class)
public class MangoDemoWebApplicationContext extends MangoDemoApplicationContext {
}
