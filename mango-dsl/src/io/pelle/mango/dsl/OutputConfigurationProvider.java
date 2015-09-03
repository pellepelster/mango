package io.pelle.mango.dsl;

import io.pelle.mango.dsl.generator.GeneratorConstants;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.xtext.generator.IOutputConfigurationProvider;
import org.eclipse.xtext.generator.OutputConfiguration;

public final class OutputConfigurationProvider implements IOutputConfigurationProvider {

	@Override
	public final Set<OutputConfiguration> getOutputConfigurations() {

		OutputConfiguration entityOutputConfiguration = new OutputConfiguration(GeneratorConstants.SERVER_GEN_OUTPUT);
		entityOutputConfiguration.setDescription(GeneratorConstants.SERVER_GEN_OUTPUT_DESCRIPTION);
		entityOutputConfiguration.setOutputDirectory(GeneratorConstants.SERVER_GEN_OUTPUT_DEFAULT_DIR);
		entityOutputConfiguration.setOverrideExistingResources(true);
		entityOutputConfiguration.setCreateOutputDirectory(true);
		entityOutputConfiguration.setCleanUpDerivedResources(true);
		entityOutputConfiguration.setSetDerivedProperty(true);
		entityOutputConfiguration.setKeepLocalHistory(true);

		OutputConfiguration clientGWTOutputConfiguration = new OutputConfiguration(GeneratorConstants.CLIENT_GWT_GEN_OUTPUT);
		clientGWTOutputConfiguration.setDescription(GeneratorConstants.CLIENT_GWT_GEN_OUTPUT_DESCRIPTION);
		clientGWTOutputConfiguration.setOutputDirectory(GeneratorConstants.CLIENT_GWT_GEN_OUTPUT_DEFAULT_DIR);
		clientGWTOutputConfiguration.setOverrideExistingResources(true);
		clientGWTOutputConfiguration.setCreateOutputDirectory(true);
		clientGWTOutputConfiguration.setCleanUpDerivedResources(true);
		clientGWTOutputConfiguration.setSetDerivedProperty(true);
		clientGWTOutputConfiguration.setKeepLocalHistory(true);

		OutputConfiguration clientGWTStubsOutputConfiguration = new OutputConfiguration(GeneratorConstants.CLIENT_GWT_GEN_STUBS_OUTPUT);
		clientGWTStubsOutputConfiguration.setDescription(GeneratorConstants.CLIENT_GWT_GEN_STUBS_OUTPUT_DESCRIPTION);
		clientGWTStubsOutputConfiguration.setOutputDirectory(GeneratorConstants.CLIENT_GWT_GEN_STUBS_OUTPUT_DEFAULT_DIR);
		clientGWTStubsOutputConfiguration.setOverrideExistingResources(true);
		clientGWTStubsOutputConfiguration.setCreateOutputDirectory(true);
		clientGWTStubsOutputConfiguration.setCleanUpDerivedResources(true);
		clientGWTStubsOutputConfiguration.setSetDerivedProperty(true);
		clientGWTStubsOutputConfiguration.setKeepLocalHistory(true);

		OutputConfiguration xmlOutputConfiguration = new OutputConfiguration(GeneratorConstants.XML_GEN_OUTPUT);
		xmlOutputConfiguration.setDescription(GeneratorConstants.XML_GEN_OUTPUT_DESCRIPTION);
		xmlOutputConfiguration.setOutputDirectory(GeneratorConstants.XML_GEN_OUTPUT_DEFAULT_DIR);
		xmlOutputConfiguration.setOverrideExistingResources(true);
		xmlOutputConfiguration.setCreateOutputDirectory(true);
		xmlOutputConfiguration.setCleanUpDerivedResources(true);
		xmlOutputConfiguration.setSetDerivedProperty(true);
		xmlOutputConfiguration.setKeepLocalHistory(true);

		Set<OutputConfiguration> outputConfigurations = new HashSet<OutputConfiguration>();
		outputConfigurations.add(entityOutputConfiguration);
		outputConfigurations.add(clientGWTOutputConfiguration);
		outputConfigurations.add(clientGWTStubsOutputConfiguration);
		outputConfigurations.add(xmlOutputConfiguration);

		outputConfigurations.addAll(new org.eclipse.xtext.generator.OutputConfigurationProvider().getOutputConfigurations());

		return outputConfigurations;
	}
}