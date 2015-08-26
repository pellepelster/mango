package packagename.projectname.server;

import io.pelle.mango.server.MangoServerApplicationContext;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import io.pelle.mango.server.MangoLoggerApplicationContext;

@Configuration
@ImportResource({ "classpath:/ProjectNameDB-gen.xml", "classpath:/ProjectNameBaseApplicationContext-gen.xml",
		"classpath:/ProjectNameSpringServices-gen.xml", "classpath:/MangoSpringServices-gen.xml", "classpath:/ProjectNameRestRemoteServices-gen.xml" })
@Import(MangoLoggerApplicationContext.class)
public class ProjectNameApplicationContext extends MangoServerApplicationContext {

}
