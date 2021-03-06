package io.pelle.mango.server;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.graphite.GraphiteReporter.Builder;

import io.pelle.mango.MangoBaseApplicationContextGen;
import io.pelle.mango.client.property.IPropertyService;
import io.pelle.mango.db.MangoDBApplicationContext;

@Configuration
@PropertySources({ @PropertySource(value = "classpath:/mango.properties", ignoreResourceNotFound = true), @PropertySource("classpath:/mango_defaults.properties") })
@Import({ MangoBaseApplicationContextGen.class, MangoDBApplicationContext.class })
@ImportResource({ "classpath:/MangoBaseApplicationContext.xml" })
public class MangoMetricsApplicationContext {

	@Autowired
	private IPropertyService propertyService;

	@Bean
	public MetricRegistry metricRegistry() {
		MetricRegistry bean = new MetricRegistry();
		return bean;
	}

	@Bean
	@Autowired
	public JmxReporter jmxReporter(MetricRegistry metricRegistry) {

		JmxReporter reporter = JmxReporter.forRegistry(metricRegistry).build();
		reporter.start();

		if (propertyService.getProperty(ConfigurationParameters.GRAPHITE_METRICS_ENABLED)) {

			Graphite graphite = new Graphite(new InetSocketAddress(propertyService.getProperty(ConfigurationParameters.GRAPHITE_CARBON_HOST), propertyService.getProperty(ConfigurationParameters.GRAPHITE_CARBON_PORT)));

			Builder builder = GraphiteReporter.forRegistry(metricRegistry).convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).filter(MetricFilter.ALL);

			if (propertyService.getProperty(ConfigurationParameters.GRAPHITE_METRICS_PREFIX) != null) {
				builder.prefixedWith(propertyService.getProperty(ConfigurationParameters.GRAPHITE_METRICS_PREFIX));
			}

			GraphiteReporter graphiteReporter = builder.build(graphite);
			graphiteReporter.start(1, TimeUnit.MINUTES);
		}

		return reporter;
	}

}
