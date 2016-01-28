package io.pelle.mango.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.pelle.mango.MangoSpringInvokerServicesGen;
import io.pelle.mango.MangoSpringServicesGen;
import io.pelle.mango.db.MangoDBApplicationContext;

@Configuration
public class MangoApplicationContextConfigurer {

	@Configuration
	@Import({ MangoDBApplicationContext.class, MangoServerApplicationContext.class, MangoMailApplicationContext.class, MangoSecurityConfig.class, MangoMetricsApplicationContext.class, MangoSpringServicesGen.class,
			MangoSpringInvokerServicesGen.class, MangoLoggerApplicationContext.class })
	public static class Configurations {
	}

}
