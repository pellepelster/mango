package io.pelle.mango.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({ "classpath:/MangoGWTRemoteServices-gen.xml" })
@Import( {MangoWebMvcApplicationContext.class })
public class MangoWebApplicationContext  {
}