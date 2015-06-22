package io.pelle.mango.gradle

import static org.junit.Assert.*

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

class MangoModelProjectPluginTest {

	@Test
    public void testGenerateDefaultOutputDir() {
		
        Project project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'HibernateGenerator'

		assert project.tasks.findByName('generateDDL')
		
    }
	
	@Test
	public void testGenerateCustomOutputDir() {
		
		Project project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'HibernateGenerator'
		assert project.tasks.findByName('generateDDL')
		
		project.ddlgenerator {
				outputDir = 'xxx'
		}

		assert project.ddlgenerator.outputDir == 'xxx'
						
	}

}
