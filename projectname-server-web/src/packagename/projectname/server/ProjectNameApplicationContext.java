package packagename.projectname.server;

import io.pelle.mango.server.MangoServerApplicationContext;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import io.pelle.mango.server.MangoLoggerApplicationContext;
import packagename.projectname.ProjectNameRestRemoteServicesGen;
import packagename.projectname.ProjectNameBaseApplicationContextGen;

@Configuration
@ImportResource({ "classpath:/ProjectNameSpringServices-gen.xml", "classpath:/MangoSpringServices-gen.xml" })
@Import({ MangoLoggerApplicationContext.class, ProjectNameRestRemoteServicesGen.class, ProjectNameBaseApplicationContextGen.class  } )
public class ProjectNameApplicationContext extends MangoServerApplicationContext {

}
