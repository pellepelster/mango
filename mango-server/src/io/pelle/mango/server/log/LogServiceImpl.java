package io.pelle.mango.server.log;

import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.client.log.ILogService;
import io.pelle.mango.client.log.LogEntryVO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;


public class LogServiceImpl implements ILogService {

	@Autowired
	private IBaseEntityService baseEntityService;

	public SelectQuery<LogEntryVO> getBaseQuery(int count, String reference) {
		SelectQuery<LogEntryVO> baseQuery = SelectQuery.selectFrom(LogEntryVO.class).orderBy(LogEntryVO.TIMESTAMP).setMaxResults(count);
		
		if (reference != null) {
			baseQuery.where(LogEntryVO.REFERENCE.eq(reference));
		}
		
		return baseQuery;
	}

	@Override
	public List<LogEntryVO> getLog(int count, String reference) {
		SelectQuery<LogEntryVO> logQuery = getBaseQuery(count, reference).descending();
		return baseEntityService.filter(logQuery);
	}

	@Override
	public List<LogEntryVO> getLogBefore(Long timestamp, int count, String reference) {
		SelectQuery<LogEntryVO> logQuery = getBaseQuery(count, reference).descending();
		logQuery.where(LogEntryVO.TIMESTAMP.lessThan(timestamp));
		return baseEntityService.filter(logQuery);
	}

	@Override
	public List<LogEntryVO> getLogAfter(Long timestamp, int count, String reference) {
		SelectQuery<LogEntryVO> logQuery = getBaseQuery(count, reference).ascending();
		logQuery.where(LogEntryVO.TIMESTAMP.greaterThan(timestamp));
		return new ArrayList<LogEntryVO>(Lists.reverse(baseEntityService.filter(logQuery)));
	}
}
