package io.pelle.mango.demo.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.client.log.ILogService;
import io.pelle.mango.client.log.LOGLEVEL;
import io.pelle.mango.client.log.LogEntryVO;
import io.pelle.mango.demo.client.test.Entity1VO;
import io.pelle.mango.server.log.MangoLogger;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoLogServiceTest extends BaseDemoTest {

	@Autowired
	private ILogService logService;

	@Autowired
	private MangoLogger mangoLogger;

	@Autowired
	private IBaseEntityService baseEntityService;

	@Test
	public void testLogEntriesFromPingTask() throws InterruptedException {

		Thread.sleep(5000);
		List<LogEntryVO> logEntries1 = logService.getLog(50, "ping");
		Thread.sleep(5000);
		List<LogEntryVO> logEntries2 = logService.getLog(50, "ping");

		assertTrue(logEntries2.size() > logEntries1.size());
	}

	@Test
	public void testLogValueObjectReference() throws InterruptedException {

		baseEntityService.deleteAll(LogEntryVO.class.getName());

		Entity1VO entity1VO = new Entity1VO();
		entity1VO.setStringDatatype1("xxx");
		entity1VO = baseEntityService.create(entity1VO);

		mangoLogger.info("test1", entity1VO);

		Thread.sleep(5000);

		List<LogEntryVO> logEntries = logService.getLog(50, entity1VO);
		assertEquals(1, logEntries.size());
		assertEquals("io.pelle.mango.demo.client.test.Entity1#" + entity1VO.getId(), logEntries.get(0).getReference());
	}

	@Test
	public void testLogStringReference() throws InterruptedException {

		baseEntityService.deleteAll(LogEntryVO.class.getName());

		mangoLogger.info("test1", "reference1");

		Thread.sleep(1000);

		List<LogEntryVO> logEntries = logService.getLog(50, "reference1");
		assertEquals(1, logEntries.size());
		assertEquals("reference1", logEntries.get(0).getReference());
	}

	private LogEntryVO assertRangeAndorder(List<LogEntryVO> logEntries, int from, int to) {

		LogEntryVO logEntryVO = null;

		for (int i = from; i > to; i--) {
			int realIndex = from - i;
			logEntryVO = logEntries.get(realIndex);
			assertEquals("reference1", logEntryVO.getReference());
			assertEquals((long) i, (long) logEntryVO.getTimestamp());
		}

		return logEntryVO;
	}

	private void print(List<LogEntryVO> logEntries) {
		for (LogEntryVO logEntryVO : logEntries) {
			System.out.println(String.format("%d: %s", logEntryVO.getTimestamp(), logEntryVO.getReference()));
		}
	}

	@Test
	public void testGetLogPaging() throws InterruptedException {

		baseEntityService.deleteAll(LogEntryVO.class.getName());

		for (int i = 1; i <= 200; i++) {
			mangoLogger.log("test1", LOGLEVEL.INFO, "reference1", i);
		}

		Thread.sleep(4000);

		List<LogEntryVO> logEntries1 = logService.getLog(50, "reference1");
		assertEquals(50, logEntries1.size());

		LogEntryVO lastLogEntryVO1 = assertRangeAndorder(logEntries1, 200, 150);

		List<LogEntryVO> logEntries2 = logService.getLogBefore(lastLogEntryVO1.getTimestamp(), 50, "reference1");
		assertEquals(50, logEntries2.size());
		LogEntryVO lastLogEntryVO2 = assertRangeAndorder(logEntries2, 150, 100);

		List<LogEntryVO> logEntries3 = logService.getLogBefore(lastLogEntryVO2.getTimestamp(), 50, "reference1");
		assertEquals(50, logEntries3.size());
		LogEntryVO lastLogEntryVO3 = assertRangeAndorder(logEntries3, 100, 50);

		List<LogEntryVO> logEntries4 = logService.getLogBefore(lastLogEntryVO3.getTimestamp(), 50, "reference1");
		assertEquals(50, logEntries4.size());
		LogEntryVO lastLogEntryVO4 = assertRangeAndorder(logEntries4, 50, 1);

		List<LogEntryVO> logEntries5 = logService.getLogAfter(logEntries4.get(0).getTimestamp(), 50, "reference1");
		assertEquals(50, logEntries5.size());
		print(logEntries5);
		LogEntryVO lastLogEntryVO5 = assertRangeAndorder(logEntries5, 100, 50);

		List<LogEntryVO> logEntries6 = logService.getLogAfter(logEntries5.get(0).getTimestamp(), 50, "reference1");
		assertEquals(50, logEntries6.size());
		print(logEntries6);
		LogEntryVO lastLogEntryVO6 = assertRangeAndorder(logEntries6, 150, 100);

	}

}
