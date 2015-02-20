package packagename.projectname.server;

import static io.pelle.mango.client.base.vo.query.SelectQuery.selectFrom;
import io.pelle.mango.db.dao.IBaseVODAO;

import java.util.List;

import packagename.projectname.client.IProjectNameService;
import packagename.projectname.client.TodoRequest;
import packagename.projectname.client.TodoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class ProjectNameServiceImpl implements IProjectNameService {

	@Autowired
	private IBaseVODAO baseVODAO;

	@Override
	public List<TodoVO> getAll() {
		return baseVODAO.filter(selectFrom(TodoVO.class));
	}

	@Override
	public void delete(Long todoId) {
		baseVODAO.delete(getTodo(todoId));
		
	}

	@Override
	public void deleteAll() {
		baseVODAO.deleteAll(TodoVO.class);
	}

	public TodoVO convert(TodoRequest todoRequest, TodoVO todoVO) {

		todoVO.setTitle(todoRequest.getTitle());
		todoVO.setCompleted(todoRequest.getCompleted());
		
		return todoVO;
	}

	@Override
	public TodoVO save(TodoRequest todoRequest) {
		
		TodoVO todo = null;
		
		if (todoRequest.getId() != null) {
			todo = baseVODAO.read(todoRequest.getId(), TodoVO.class);
		}
		
		if (todo != null) {
			return baseVODAO.save(convert(todoRequest, todo));
		} else {
			return baseVODAO.create(convert(todoRequest, new TodoVO()));
		}
		
	}

	
	public TodoVO getTodo(long todoId) {
		TodoVO todo = baseVODAO.read(todoId, TodoVO.class);
		return todo;
	}
	
}
