package io.pelle.mango.demo.server;

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
		List<LogEntryVO> logEntries1 = logService.getLog(0, 50);
		Thread.sleep(5000);
		List<LogEntryVO> logEntries2 = logService.getLog(0, 50);

		assertTrue(logEntries2.size() > logEntries1.size());
	}

}
