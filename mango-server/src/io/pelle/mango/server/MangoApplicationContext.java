package io.pelle.mango.server;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;

public class MangoApplicationContext implements InitializingBean {

	@Autowired
	private MetricRegistry metricRegistry;

	@Override
	public void afterPropertiesSet() throws Exception {
		final JmxReporter reporter = JmxReporter.forRegistry(metricRegistry).build();
		reporter.start();
	}

	public void setMetricRegistry(MetricRegistry metricRegistry) {
		this.metricRegistry = metricRegistry;
	}

}