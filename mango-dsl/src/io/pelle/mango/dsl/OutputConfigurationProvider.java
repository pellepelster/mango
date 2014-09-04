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

		OutputConfiguration voOutputConfiguration = new OutputConfiguration(GeneratorConstants.CLIENT_GWT_GEN_OUTPUT);
		voOutputConfiguration.setDescription(GeneratorConstants.CLIENT_GWT_GEN_OUTPUT_DESCRIPTION);
		voOutputConfiguration.setOutputDirectory(GeneratorConstants.CLIENT_GWT_GEN_OUTPUT_DEFAULT_DIR);
		voOutputConfiguration.setOverrideExistingResources(true);
		voOutputConfiguration.setCreateOutputDirectory(true);
		voOutputConfiguration.setCleanUpDerivedResources(true);
		voOutputConfiguration.setSetDerivedProperty(true);
		voOutputConfiguration.setKeepLocalHistory(true);

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
		outputConfigurations.add(voOutputConfiguration);
		outputConfigurations.add(xmlOutputConfiguration);

		outputConfigurations.addAll(new org.eclipse.xtext.generator.OutputConfigurationProvider().getOutputConfigurations());

		return outputConfigurations;
	}
}