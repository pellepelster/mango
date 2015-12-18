package packagename.projectname.server;

import io.pelle.mango.server.MangoServerApplicationContext;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import io.pelle.mango.server.MangoLoggerApplicationContext;
import io.pelle.mango.MangoSpringServicesGen;
import packagename.projectname.ProjectNameRestRemoteServicesGen;
import packagename.projectname.ProjectNameBaseApplicationContextGen;
import packagename.projectname.ProjectNameSpringServicesGen;

@Configuration
@Import({ MangoLoggerApplicationContext.class, ProjectNameRestRemoteServicesGen.class, ProjectNameBaseApplicationContextGen.class, MangoSpringServicesGen.class, ProjectNameSpringServicesGen.class  } )
public class ProjectNameApplicationContext extends MangoServerApplicationContext {

}
