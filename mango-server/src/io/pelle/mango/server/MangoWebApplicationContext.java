package io.pelle.mango.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.pelle.mango.MangoGWTRemoteServicesGen;

@Configuration
@Import({ MangoWebMvcApplicationContext.class, MangoGWTRemoteServicesGen.class })
public class MangoWebApplicationContext {
}