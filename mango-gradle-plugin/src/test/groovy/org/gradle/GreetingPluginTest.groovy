package org.gradle

import org.junit.Test

import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Project
import static org.junit.Assert.*

class MangoModelProjectPluginTest {

	@Test
    public void greeterPluginAddsGreetingTaskToProject() {
		
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'java'
        project.apply plugin: 'eclipse'
        project.apply plugin: 'MangoModelProject'
    }
}
