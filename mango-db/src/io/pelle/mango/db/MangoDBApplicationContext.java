package io.pelle.mango.db;

import io.pelle.mango.db.util.EntityVOMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({ "classpath:/DBBaseApplicationContext.xml" })
public class MangoDBApplicationContext {

	@Bean
	public EntityVOMapper entityVOMapper() {
		return EntityVOMapper.getInstance();
	}
}
