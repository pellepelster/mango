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
	public List<LogEntryVO> getLog(int count) {
		SelectQuery<LogEntryVO> logQuery = SelectQuery.selectFrom(LogEntryVO.class).orderBy(LogEntryVO.TIMESTAMP).setMaxResults(count).descending();
		return baseEntityService.filter(logQuery);
	}

	@Override
	public List<LogEntryVO> getLogBefore(Long timestamp, int count) {
		SelectQuery<LogEntryVO> logQuery = SelectQuery.selectFrom(LogEntryVO.class).orderBy(LogEntryVO.TIMESTAMP).setMaxResults(count).descending();
		logQuery.where(LogEntryVO.TIMESTAMP.lessThan(timestamp));
		return baseEntityService.filter(logQuery);
	}

	@Override
	public List<LogEntryVO> getLogAfter(Long timestamp, int count) {
		SelectQuery<LogEntryVO> logQuery = SelectQuery.selectFrom(LogEntryVO.class).orderBy(LogEntryVO.TIMESTAMP).setMaxResults(count).descending();
		logQuery.where(LogEntryVO.TIMESTAMP.greaterThan(timestamp));
		return baseEntityService.filter(logQuery);
	}
}
