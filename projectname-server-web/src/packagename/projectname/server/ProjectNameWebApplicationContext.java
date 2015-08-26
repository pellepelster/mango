package packagename.projectname.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.pelle.mango.server.MangoWebApplicationContext;

@Configuration
@Import(MangoWebApplicationContext.class)
public class ProjectNameWebApplicationContext extends ProjectNameApplicationContext {

}
