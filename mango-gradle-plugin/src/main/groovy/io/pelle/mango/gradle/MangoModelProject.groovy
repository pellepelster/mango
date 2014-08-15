package io.pelle.mango.gradle

import org.apache.ivy.core.module.descriptor.ExtendsDescriptor;
import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.tasks.bundling.Jar;

class MangoModelProject extends MangoBaseProject {
	
	void apply(Project project) {

		super.apply(project);
		
		project.apply(plugin: 'xtext')
		
		project.xtext {
			languages{
				mango {
					setup = 'io.pelle.mango.dsl.MangoStandaloneSetup'
					consumesJava = true
				}
			}
		}

		project.dependencies {
			xtextTooling group: 'io.pelle.mango', name: 'mango-dsl', version: '+'
			xtextTooling group: 'io.pelle.mango', name: 'mango-server', version: '+'
			compile group: 'io.pelle.mango', name: 'mango-server', version: '+'
			testCompile group: 'io.pelle.mango', name: 'mango-db', version: '+', configuration: 'testArtifacts'
		}
				
		project.configurations {
			generatedEntities
			generatedEntitiesSources
			generatedVOs
			generatedVOsSources
			generatedXml
			generatedXmlSources
		}
		
		project.sourceSets {
			
			generatedVOs {
				java {
					srcDirs = [ 'src-gen-vos' ]
				}
				resources {
					srcDirs = [ 'src-gen-vos' ]
				}
				
				compileClasspath += project.sourceSets.main.compileClasspath
			}
		
			generatedEntities {
				java {
					srcDirs = [ 'src-gen-entities' ]
				}
				resources {
					srcDirs = [ 'src-gen-entities' ]
				}
				
				compileClasspath += project.sourceSets.main.compileClasspath
				compileClasspath += project.sourceSets.generatedVOs.output
			}
			
			generatedXml {
				
				java {
					srcDirs = [ 'src-gen-xml' ]
				}
				resources {
					srcDirs = [ 'src-gen-xml' ]
				}
				
				compileClasspath += project.sourceSets.main.compileClasspath
				compileClasspath += project.sourceSets.generatedVOs.output
			}
		
			test {
				
				java {
					srcDirs = [ 'test' ]
				}
				resources {
					srcDirs = [ 'test' ]
				}
				
				compileClasspath += project.sourceSets.generatedEntities.output
				runtimeClasspath += project.sourceSets.generatedEntities.output
		
				compileClasspath += project.sourceSets.generatedVOs.output
				runtimeClasspath += project.sourceSets.generatedVOs.output
		
			}
		
		}

		project.task('generatedEntitiesJar', type: Jar, dependsOn: project.jar) {
			classifier 'entities-generated'
			from project.sourceSets.generatedEntities.output
		}
		
		project.task('generatedEntitiesSourceJar', type: Jar, dependsOn: project.jar) {
			classifier 'entities-generated-sources'
			from project.sourceSets.generatedEntities.allSource
			exclude('**/persistence.xml')
		}
		
		project.task('generatedVOsJar', type: Jar, dependsOn: project.jar) {
			classifier 'vos-generated'
			from project.sourceSets.generatedVOs.output
		}
		
		project.task('generatedVOsSourceJar', type: Jar, dependsOn: project.jar) {
			classifier 'vos-generated-sources'
			from project.sourceSets.generatedVOs.allSource
		}

		project.task('generatedXmlJar', type: Jar, dependsOn: project.jar) {
			classifier 'xml-generated'
			from project.sourceSets.generatedXml.output
		}
		
		project.task('generatedXmlSourceJar', type: Jar, dependsOn: project.jar) {
			classifier 'xml-generated-sources'
			from project.sourceSets.generatedXml.allSource
		}

		project.compileGeneratedEntitiesJava.dependsOn project.compileJava
		
		project.compileGeneratedVOsJava.dependsOn project.compileJava

		project.compileGeneratedXmlJava.dependsOn project.compileJava
		
		project.artifacts {
			archives project.generatedEntitiesJar, project.generatedEntitiesSourceJar
			archives project.generatedVOsJar, project.generatedVOsSourceJar
			archives project.generatedXmlJar, project.generatedXmlSourceJar
			
			generatedEntities project.generatedEntitiesJar, project.generatedEntitiesSourceJar
			generatedVOs project.generatedVOsJar, project.generatedVOsSourceJar
			generatedXml project.generatedXmlJar, project.generatedXmlSourceJar
		 }
	}
}