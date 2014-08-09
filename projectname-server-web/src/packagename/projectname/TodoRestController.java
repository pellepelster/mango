package packagename.projectname;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import packagename.client.TodoVO;

@RestController
@RequestMapping("/todos")
@Transactional
public class TodoRestController {

	@Autowired
	private TodoService todoService;

	@RequestMapping(value = "")
	public List<TodoVO> getTodos() {
		return todoService.getTodos();
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public TodoVO addTodo(@RequestBody TodoVO todo) {

		if (todo.isNew()) {
			return todoService.addTodo(todo);
		} else {
			TodoVO oldTodo = todoService.getTodo(todo.getId());
			oldTodo.setTitle(todo.getTitle());
			oldTodo.setCompleted(todo.getCompleted());
			return todoService.saveTodo(todo);
		}

	}

	@RequestMapping(value = "{todoId}", method = RequestMethod.DELETE)
	public void deleteTodo(@PathVariable long todoId) {
		todoService.deleteTodo(todoId);
	}

	@RequestMapping(value = "deleteAll")
	public void deleteAll() {
		todoService.deleteAll();
	}

	public void setTodoService(TodoService todoService) {
		this.todoService = todoService;
	}

}
