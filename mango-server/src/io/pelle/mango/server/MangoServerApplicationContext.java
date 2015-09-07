package io.pelle.mango.server;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.graphite.GraphiteReporter.Builder;

import io.pelle.mango.client.property.IPropertyService;
import io.pelle.mango.db.MangoDBApplicationContext;
import io.pelle.mango.server.api.entity.EntityApiController;
import io.pelle.mango.server.api.entity.EntityApiIndexController;
import io.pelle.mango.server.api.webhook.EntityWebhookRegistry;
import io.pelle.mango.server.api.webhook.WebhookApiController;
import io.pelle.mango.server.file.EntityFileUUIDCallback;
import io.pelle.mango.server.file.FileController;
import io.pelle.mango.server.file.FileEntityCallback;
import io.pelle.mango.server.file.FileStorage;
import io.pelle.mango.server.hierarchy.HierarchicalVODecorator;
import io.pelle.mango.server.hierarchy.HierarchyParentValidator;
import io.pelle.mango.server.log.LogReferenceKeyMapperRegistry;
import io.pelle.mango.server.log.VOEntityLogReferenceKeyMapper;
import io.pelle.mango.server.security.MangoGroupPermissionMerger;
import io.pelle.mango.server.state.StateEntityCallback;
import io.pelle.mango.server.util.ConfigurationLogger;
import io.pelle.mango.server.validator.LengthValidator;
import io.pelle.mango.server.validator.MandatoryValidator;
import io.pelle.mango.server.validator.NaturalKeyValidator;
import io.pelle.mango.server.vo.VOMetaDataService;
import io.pelle.mango.server.xml.XmlVOExporter;
import io.pelle.mango.server.xml.XmlVOImporter;
import io.pelle.mango.server.xml.XmlVOMapper;

@Configuration
@ImportResource({ "classpath:/MangoServerApplicationContext.xml" })
@Import({ MangoSecurityConfig.class })
@PropertySources({ @PropertySource(value = "classpath:/mango.properties", ignoreResourceNotFound = true), @PropertySource("classpath:/mango_defaults.properties") })
public class MangoServerApplicationContext extends MangoDBApplicationContext {

	@Autowired
	private IPropertyService propertyService;

	@Bean
	public ConfigurationLogger ConfigurationLogger() {
		return new ConfigurationLogger();
	}

	@Bean(name = "xmlVOImporter")
	public XmlVOImporter XmlVOImporter() {
		return new XmlVOImporter();
	}

	@Bean(name = "xmlVOExporter")
	public XmlVOExporter XmlVOExporter() {
		return new XmlVOExporter();
	}

	@Bean
	public XmlVOMapper XmlVOMapper() {
		return new XmlVOMapper();
	}

	@Bean
	public LogReferenceKeyMapperRegistry logReferenceKeyMapperRegistry() {
		return new LogReferenceKeyMapperRegistry();
	}

	@Bean
	public VOEntityLogReferenceKeyMapper VOEntityLogReferenceKeyMapper() {
		return new VOEntityLogReferenceKeyMapper();
	}

	@Bean
	public VOMetaDataService VOMetaDataService() {
		return new VOMetaDataService();
	}

	@Bean
	public MetricRegistry metricRegistry() {
		MetricRegistry bean = new MetricRegistry();
		return bean;
	}

	@Bean
	public EntityApiController entityApiController() {
		return new EntityApiController();
	}

	@Bean
	public EntityApiIndexController entityApiIndexController() {
		return new EntityApiIndexController();
	}

	@Bean
	public WebhookApiController webhookApiController() {
		return new WebhookApiController();
	}

	@Bean
	public EntityWebhookRegistry entityWebhookRegistry() {
		return new EntityWebhookRegistry();
	}

	@Bean
	public FileEntityCallback fileEntityCallback() {
		return new FileEntityCallback();
	}

	@Bean
	public StateEntityCallback stateEntityCallback() {
		return new StateEntityCallback();
	}

	@Bean
	public FileStorage fileStorage() {
		return new FileStorage();
	}

	@Bean
	public MangoGroupPermissionMerger mangoGroupPermissionMerger() {
		return new MangoGroupPermissionMerger();
	}

	@Bean
	public EntityFileUUIDCallback entityFileUUIDCallback() {
		return new EntityFileUUIDCallback();
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
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("/i18/usermsg");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
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
