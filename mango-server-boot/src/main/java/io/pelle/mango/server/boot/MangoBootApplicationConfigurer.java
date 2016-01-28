package io.pelle.mango.server.boot;

import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.pelle.mango.server.MangoApplicationContextConfigurer;

@Configuration
@Import({ EmbeddedServletContainerAutoConfiguration.class, WebMvcAutoConfiguration.class })
public class MangoBootApplicationConfigurer extends MangoApplicationContextConfigurer {
}