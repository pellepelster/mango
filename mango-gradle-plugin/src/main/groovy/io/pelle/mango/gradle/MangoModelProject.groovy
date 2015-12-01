package io.pelle.mango.gradle

import org.apache.ivy.core.module.descriptor.ExtendsDescriptor;
import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.tasks.bundling.Jar;

class MangoModelProject extends MangoBaseProject {
	
	void apply(Project project) {

		super.apply(project);
		
		project.apply(plugin: 'org.xtext.xtext')
		
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
			compile group: 'io.pelle.mango', name: 'mango-model', version: '+'
			testCompile group: 'io.pelle.mango', name: 'mango-db', version: '+', configuration: 'testArtifacts'
		}
				
		project.configurations {
			generatedServer
			generatedServerSources
			generatedClientGWT
			generatedClientGWTSources
			generatedXml
			generatedXmlSources
		}
		
		project.sourceSets {
			
			generatedClientGWT {
				java {
					srcDirs = [ 'src-gen-client-gwt' ]
				}
				resources {
					srcDirs = [ 'src-gen-client-gwt' ]
				}
				
				compileClasspath += project.sourceSets.main.compileClasspath
			}
		
			generatedServer {
				java {
					srcDirs = [ 'src-gen-server' ]
				}
				resources {
					srcDirs = [ 'src-gen-server' ]
				}
				
				compileClasspath += project.sourceSets.main.compileClasspath
				compileClasspath += project.sourceSets.generatedClientGWT.output
			}
			
			generatedXml {
				
				java {
					srcDirs = [ 'src-gen-xml' ]
				}
				resources {
					srcDirs = [ 'src-gen-xml' ]
				}
				
				compileClasspath += project.sourceSets.main.compileClasspath
				compileClasspath += project.sourceSets.generatedClientGWT.output
			}
		
			test {
				
				java {
					srcDirs = [ 'test' ]
				}
				resources {
					srcDirs = [ 'test' ]
				}
				
				compileClasspath += project.sourceSets.generatedServer.output
				runtimeClasspath += project.sourceSets.generatedServer.output
		
				compileClasspath += project.sourceSets.generatedClientGWT.output
				runtimeClasspath += project.sourceSets.generatedClientGWT.output
		
			}
		
		}

		project.task('generatedServerJar', type: Jar, dependsOn: project.jar) {
			classifier 'entities-generated'
			from project.sourceSets.generatedServer.output
		}
		
		project.task('generatedServerSourceJar', type: Jar, dependsOn: project.jar) {
			classifier 'entities-generated-sources'
			from project.sourceSets.generatedServer.allSource
			exclude('**/persistence.xml')
		}
		
		project.task('generatedClientGWTJar', type: Jar, dependsOn: project.jar) {
			classifier 'vos-generated'
			from project.sourceSets.generatedClientGWT.output
		}
		
		project.task('generatedClientGWTSourceJar', type: Jar, dependsOn: project.jar) {
			classifier 'vos-generated-sources'
			from project.sourceSets.generatedClientGWT.allSource
		}

		project.task('generatedXmlJar', type: Jar, dependsOn: project.jar) {
			classifier 'xml-generated'
			from project.sourceSets.generatedXml.output
		}
		
		project.task('generatedXmlSourceJar', type: Jar, dependsOn: project.jar) {
			classifier 'xml-generated-sources'
			from project.sourceSets.generatedXml.allSource
		}
		
		
		project.clean.doLast { 
		    ant.delete(includeEmptyDirs: 'true', failOnError: 'false') {
		        fileset(dir: "src-gen-client-gwt", includes: "**/*")
		        fileset(dir: "src-gen-server", includes: "**/*")
		        fileset(dir: "src-gen-xml", includes: "**/*")
		    }
		}

		project.compileGeneratedServerJava.dependsOn project.compileJava
		
		project.compileGeneratedClientGWTJava.dependsOn project.compileJava

		project.compileGeneratedXmlJava.dependsOn project.compileJava
		
		project.artifacts {
			archives project.generatedServerJar, project.generatedServerSourceJar
			archives project.generatedClientGWTJar, project.generatedClientGWTSourceJar
			archives project.generatedXmlJar, project.generatedXmlSourceJar
			
			generatedServer project.generatedServerJar, project.generatedServerSourceJar
			generatedClientGWT project.generatedClientGWTJar, project.generatedClientGWTSourceJar
			generatedXml project.generatedXmlJar, project.generatedXmlSourceJar
		 }
	}
}