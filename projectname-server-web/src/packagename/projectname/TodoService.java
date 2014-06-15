package packagename.projectname;

import static io.pelle.mango.db.jpql.SelectQuery.selectFrom;
import io.pelle.mango.db.dao.IBaseVODAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import packagename.TodoVO;

@Component
public class TodoService {

	@Autowired
	private IBaseVODAO baseVODAO;

	public List<TodoVO> getTodos() {
		return baseVODAO.filter(selectFrom(TodoVO.class));
	}

	public TodoVO addTodo(TodoVO todo) {
		return baseVODAO.create(todo);
	}

	public void deleteTodo(long todoId) {
		baseVODAO.delete(getTodo(todoId));
	}

	public TodoVO getTodo(long todoId) {
		TodoVO todo = baseVODAO.read(todoId, TodoVO.class);
		return todo;
	}

	public TodoVO saveTodo(TodoVO todo) {
		return baseVODAO.save(todo);
	}

	public void deleteAll() {
		baseVODAO.deleteAll(TodoVO.class);
	}

	public void setBaseVODAO(IBaseVODAO baseVODAO) {
		this.baseVODAO = baseVODAO;
	}

}
