package io.pelle.mango.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({ "classpath:/MangoGWTRemoteServices-gen.xml" })
public class MangoWebApplicationContext extends MangoServerApplicationContext {
}