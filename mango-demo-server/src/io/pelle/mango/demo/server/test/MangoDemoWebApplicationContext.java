package io.pelle.mango.demo.server.test;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({ "classpath:/MangoGWTRemoteServices-gen.xml" })
public class MangoDemoWebApplicationContext extends MangoDemoApplicationContext {
}
