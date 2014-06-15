package io.pelle.mango.gradle

import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.tasks.bundling.Jar;

class MangoBaseProject implements Plugin<Project> {
	
	void apply(Project project) {

		project.apply(plugin: 'eclipse')
		project.apply(plugin: 'java')

		project.sourceSets {
			
			main {
				java { 
					srcDirs = ["src"]
				}
				
				resources {
					srcDirs = ["src"]
				}
			}
			
			test {
				java {
					srcDirs = [ 'test' ]
				}
				resources {
					srcDirs = [ 'test' ]
				}
			}
		}
		
		project.configurations {
			testArtifacts.extendsFrom(testRuntime)
		}
	
		project.task('testJar', type: Jar, dependsOn: project.testClasses) {
			appendix = 'test'
			from project.sourceSets.test.output
		}
	
		project.task('testSourceJar', type: Jar, dependsOn: project.testClasses) {
			appendix = 'test-sources'
			from project.sourceSets.test.allSource
			exclude('**/persistence.xml')
		}
	
		project.artifacts {
			testArtifacts project.testJar, project.testSourceJar
		}
		
		project.repositories {
			ivy {
				name "localDevelopmentRepository"
				url System.properties['user.home'] + "/.gradle/mango_repository"
				layout "maven"
			}
	
			mavenCentral()
		}
	}
}