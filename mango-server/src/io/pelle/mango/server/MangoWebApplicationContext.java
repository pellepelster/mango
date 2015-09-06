package io.pelle.mango.server;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import io.pelle.mango.server.xml.MangoObjectMapper;

@Configuration
@ImportResource({ "classpath:/MangoGWTRemoteServices-gen.xml" })
@Import(MangoServerApplicationContext.class)
@EnableWebMvc
public class MangoWebApplicationContext extends WebMvcConfigurerAdapter {

	@Bean
	public MangoObjectMapper mangoObjectMapper() {
		return new MangoObjectMapper();
	}

	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter result = new MappingJackson2HttpMessageConverter();
		result.setObjectMapper(mangoObjectMapper());
		return result;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(mappingJackson2HttpMessageConverter());
	}

	@Bean
	public LocaleResolver localeResolver() {
		AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
		return resolver;
	}
}