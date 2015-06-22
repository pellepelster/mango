package groovy.io.pelle.mango.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.logging.LoggingManagerInternal;


class GenerateDDLTask extends DefaultTask {
	
		@TaskAction
		void generate() {
			println '----------showMessage-------------'
			println project.ddlgenerator.outputDir
		}
		
	
}
	