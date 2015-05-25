package packagename.projectname.server;

import io.pelle.mango.server.MangoServerApplicationContext;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.ComponentScan;

@Configuration
@ImportResource({ "classpath:/ProjectNameDB-gen.xml", "classpath:/ProjectNameBaseApplicationContext-gen.xml",
		"classpath:/ProjectNameSpringServices-gen.xml", "classpath:/MangoSpringServices-gen.xml", "classpath:/ProjectNameRestRemoteServices-gen.xml", "classpath:/MangoLoggerApplicationContext.xml" })
@ComponentScan("packagename.projectname")
public class ProjectNameApplicationContext extends MangoServerApplicationContext {

}
