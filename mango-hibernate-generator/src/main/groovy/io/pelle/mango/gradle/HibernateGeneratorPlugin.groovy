package groovy.io.pelle.mango.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

class HibernateGeneratorPlugin implements Plugin<Project> {
	
	void apply(Project project) {

		project.extensions.create('ddlgenerator', DDLGeneratorExtension)
		project.task('generateDDL', type: GenerateDDLTask)
		
	}
}

class DDLGeneratorExtension {
	
	String outputDir

}
