package io.pelle.mango.server.log;

import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.client.log.ILogService;
import io.pelle.mango.client.log.LogEntryVO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class LogServiceImpl implements ILogService {

	@Autowired
	private IBaseEntityService baseEntityService;

	@Override
	public List<LogEntryVO> getLog(int firstResult, int maxResults) {
		SelectQuery<LogEntryVO> logQuery = SelectQuery
				.selectFrom(LogEntryVO.class).setMaxResults(maxResults)
				.setFirstResult(firstResult);
		return baseEntityService.filter(logQuery);
	}

}
