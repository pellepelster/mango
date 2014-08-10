package packagename.projectname;

import static org.junit.Assert.assertEquals;
import io.pelle.mango.db.test.BaseDBTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import packagename.client.TodoVO;

@ContextConfiguration(locations = { "classpath:/ProjectNameApplicationContext.xml", "classpath:/ProjectNameDB-gen.xml", "classpath:/ProjectNameBaseApplicationContext-gen.xml" })
public class TodoServiceTest extends BaseDBTest {

	public TodoServiceTest() {
		super("projectname");
	}

	@Autowired
	private TodoService todoService;

	@Test
	public void testAddTodo() {
		assertEquals(0, todoService.getTodos().size());
		todoService.addTodo(new TodoVO());
		assertEquals(1, todoService.getTodos().size());
	}

	public void setTodoService(TodoService todoService) {
		this.todoService = todoService;
	}

}
