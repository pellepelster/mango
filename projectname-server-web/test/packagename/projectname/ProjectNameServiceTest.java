package packagename.projectname;

import static org.junit.Assert.assertEquals;
import io.pelle.mango.db.test.BaseDBTest;

import packagename.projectname.client.IProjectNameService;
import packagename.projectname.client.TodoRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ActiveProfiles;

@ContextConfiguration( classes = { packagename.projectname.server.ProjectNameApplicationContext.class  })
@ActiveProfiles("test")
public class ProjectNameServiceTest extends BaseDBTest {

	public ProjectNameServiceTest() {
	}

	@Autowired
	private IProjectNameService projectnameService;

	@Test
	public void testAddTodo() {
		assertEquals(0, projectnameService.getAll().size());
		projectnameService.save(new TodoRequest());
		assertEquals(1, projectnameService.getAll().size());
	}

}
