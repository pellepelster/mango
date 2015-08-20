package io.pelle.mango.server;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.graphite.GraphiteReporter.Builder;

import io.pelle.mango.client.property.IPropertyService;
import io.pelle.mango.db.MangoDBApplicationContext;
import io.pelle.mango.server.hierarchy.HierarchicalVODecorator;
import io.pelle.mango.server.hierarchy.HierarchyParentValidator;
import io.pelle.mango.server.system.FileController;
import io.pelle.mango.server.system.FileVOEntityCallback;
import io.pelle.mango.server.validator.LengthValidator;
import io.pelle.mango.server.validator.MandatoryValidator;
import io.pelle.mango.server.validator.NaturalKeyValidator;

@Configuration
@ImportResource({ "classpath:/MangoServerApplicationContext.xml" })
public class MangoServerApplicationContext extends MangoDBApplicationContext {

	@Autowired
	private IPropertyService propertyService;

	@Bean
	public MetricRegistry metricRegistry() {
		MetricRegistry bean = new MetricRegistry();
		return bean;
	}

	@Bean
	public FileVOEntityCallback fileVOEntityCallback() {
		return new FileVOEntityCallback();
	}

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setMaxUploadSize(5242880);
		return resolver;
	}

	@Bean
	public FileController fileUploadController() {
		FileController bean = new FileController();
		return bean;
	}

	@Bean
	public EhCacheManagerFactoryBean cacheManagerFactory() {
		EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
		bean.setConfigLocation(new ClassPathResource("ehcache.xml"));
		bean.setShared(true);
		return bean;
	}

	@Bean
	public HierarchicalVODecorator hierarchicalVODecorator() {
		return new HierarchicalVODecorator();
	}

	@Bean(name = "cacheManager")
	public CacheManager cacheManager() {
		EhCacheCacheManager cacheManager = new EhCacheCacheManager();
		cacheManager.setCacheManager(cacheManagerFactory().getObject());

		return cacheManager;
	}

	@Bean
	public NaturalKeyValidator naturalKeyValidator() {
		return new NaturalKeyValidator();
	}

	@Bean
	public LengthValidator lengthValidator() {
		return new LengthValidator();
	}

	@Bean
	public MandatoryValidator mandatoryValidator() {
		return new MandatoryValidator();
	}

	@Bean
	public HierarchyParentValidator hierarchyParentValidator() {
		return new HierarchyParentValidator();
	}

	@Bean
	public JmxReporter jmxReporter() {

		JmxReporter reporter = JmxReporter.forRegistry(metricRegistry()).build();
		reporter.start();

		if (propertyService.getProperty(ConfigurationParameters.GRAPHITE_METRICS_ENABLED)) {

			Graphite graphite = new Graphite(new InetSocketAddress(propertyService.getProperty(ConfigurationParameters.GRAPHITE_CARBON_HOST), propertyService.getProperty(ConfigurationParameters.GRAPHITE_CARBON_PORT)));

			Builder builder = GraphiteReporter.forRegistry(metricRegistry()).convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).filter(MetricFilter.ALL);

			if (propertyService.getProperty(ConfigurationParameters.GRAPHITE_METRICS_PREFIX) != null) {
				builder.prefixedWith(propertyService.getProperty(ConfigurationParameters.GRAPHITE_METRICS_PREFIX));
			}

			GraphiteReporter graphiteReporter = builder.build(graphite);
			graphiteReporter.start(1, TimeUnit.MINUTES);
		}

		return reporter;
	}

}
