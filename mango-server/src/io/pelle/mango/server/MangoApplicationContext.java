package io.pelle.mango.server;

import io.pelle.mango.client.property.IPropertyService;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.graphite.GraphiteReporter.Builder;

public class MangoApplicationContext implements InitializingBean {

	@Autowired
	private MetricRegistry metricRegistry;

	@Autowired
	private IPropertyService propertyService;

	@Override
	public void afterPropertiesSet() throws Exception {

		JmxReporter reporter = JmxReporter.forRegistry(metricRegistry).build();
		reporter.start();

		if (propertyService.getProperty(ConfigurationParameters.GRAPHITE_METRICS_ENABLED)) {

			Graphite graphite = new Graphite(new InetSocketAddress(propertyService.getProperty(ConfigurationParameters.GRAPHITE_METRICS_HOST), propertyService.getProperty(ConfigurationParameters.GRAPHITE_METRICS_PORT)));

			Builder builder = GraphiteReporter.forRegistry(metricRegistry).convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).filter(MetricFilter.ALL);

			if (propertyService.getProperty(ConfigurationParameters.GRAPHITE_METRICS_PREFIX) != null) {
				builder.prefixedWith(propertyService.getProperty(ConfigurationParameters.GRAPHITE_METRICS_PREFIX));
			}

			GraphiteReporter graphiteReporter = builder.build(graphite);

			graphiteReporter.start(1, TimeUnit.MINUTES);
		}

	}

	public void setMetricRegistry(MetricRegistry metricRegistry) {
		this.metricRegistry = metricRegistry;
	}

}