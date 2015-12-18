package io.pelle.mango.server;

import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.codahale.metrics.MetricRegistry;

import io.pelle.mango.MangoBaseApplicationContextGen;
import io.pelle.mango.db.MangoDBApplicationContext;
import io.pelle.mango.server.api.entity.EntityApiController;
import io.pelle.mango.server.api.entity.EntityApiIndexController;
import io.pelle.mango.server.api.webhook.EntityWebhookRegistry;
import io.pelle.mango.server.api.webhook.WebhookApiController;
import io.pelle.mango.server.file.EntityFileUUIDCallback;
import io.pelle.mango.server.file.FileController;
import io.pelle.mango.server.file.FileEntityCallback;
import io.pelle.mango.server.file.FileStorage;
import io.pelle.mango.server.gwt.MangoRPCServiceExporterFactory;
import io.pelle.mango.server.hierarchy.HierarchicalVODecorator;
import io.pelle.mango.server.hierarchy.HierarchyParentValidator;
import io.pelle.mango.server.log.LogReferenceKeyMapperRegistry;
import io.pelle.mango.server.log.VOEntityLogReferenceKeyMapper;
import io.pelle.mango.server.security.MangoGroupPermissionMerger;
import io.pelle.mango.server.state.StateEntityCallback;
import io.pelle.mango.server.util.ConfigurationLogger;
import io.pelle.mango.server.util.DocumentationBean;
import io.pelle.mango.server.validator.LengthValidator;
import io.pelle.mango.server.validator.MandatoryValidator;
import io.pelle.mango.server.validator.NaturalKeyValidator;
import io.pelle.mango.server.vo.VOMetaDataService;
import io.pelle.mango.server.xml.XmlVOExporter;
import io.pelle.mango.server.xml.XmlVOImporter;
import io.pelle.mango.server.xml.XmlVOMapper;

@Configuration
@PropertySources({ @PropertySource(value = "classpath:/mango.properties", ignoreResourceNotFound = true), @PropertySource("classpath:/mango_defaults.properties") })
@Import({ MangoBaseApplicationContextGen.class, MangoDBApplicationContext.class, MangoMetricsApplicationContext.class })
@ImportResource({ "classpath:/MangoBaseApplicationContext.xml" })
public class MangoServerApplicationContext {

	@Bean
	public FreeMarkerConfigurer freemarkerConfig() {

		FreeMarkerConfigurer result = new FreeMarkerConfigurer();
		result.setTemplateLoaderPath("classpath:templates");

		return result;
	}

	@Bean
	public FreeMarkerViewResolver viewResolver() {

		FreeMarkerViewResolver result = new FreeMarkerViewResolver();
		result.setCache(true);
		result.setSuffix(".ftl");

		return result;
	}
	
	@Bean
	public StateEntityCallback stateEntityCallback() {
		return new StateEntityCallback();
	}

	@Bean
	public ConfigurationLogger configurationLogger() {
		return new ConfigurationLogger();
	}
	
	@Bean
	public DocumentationBean documentationBean() {
		return new DocumentationBean();
	}

	@Bean(name = "xmlVOImporter")
	public XmlVOImporter xmlVOImporter() {
		return new XmlVOImporter();
	}

	@Bean
	public MangoRPCServiceExporterFactory rpcServiceExporterFactory() {
		return new MangoRPCServiceExporterFactory();
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

}
