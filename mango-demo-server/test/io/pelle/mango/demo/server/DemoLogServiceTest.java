package io.pelle.mango.demo.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.pelle.mango.client.log.ILogService;
import io.pelle.mango.client.log.LogEntryVO;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoLogServiceTest extends BaseDemoTest {

	@Autowired
	private ILogService logService;

	@Test
	public void testLogEntryFromPingTask() throws InterruptedException {

		Thread.sleep(5000);
		List<LogEntryVO> logEntries1 = logService.getLog(50);
		Thread.sleep(5000);
		List<LogEntryVO> logEntries2 = logService.getLog(50);

		assertTrue(logEntries2.size() > logEntries1.size());

		LogEntryVO logEntryVO = logEntries1.get(0);
		List<LogEntryVO> logEntries3 = logService.getLogBefore(logEntryVO.getTimestamp(), 50);
		assertEquals(logEntries1.size() - 1, logEntries3.size());

	}
}
