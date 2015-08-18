package io.pelle.mango.demo.server.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.pelle.mango.client.FileVO;
import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.db.dao.IBaseVODAO;
import io.pelle.mango.demo.server.BaseDemoTest;

public class DemoFileStorageServiceTest extends BaseDemoTest {

	@Autowired
	private IBaseEntityService baseEntityService;

	@Autowired
	private IBaseVODAO baseVODAO;

	@Before
	public void initTestData() {
		baseVODAO.deleteAll(FileVO.class);
	}

	@Test
	public void testFileVOUUIDSave() {

		FileVO file = new FileVO();
		file.setFileContent(new byte[] { 0xa, 0xb, 0xc });

		FileVO savedFile = baseEntityService.save(file);
		assertEquals(36, savedFile.getFileUUID().length());
	}

	@Test
	public void testFileVOUUIDCreate() {

		FileVO file = new FileVO();
		file.setFileContent(new byte[] { 0xa, 0xb, 0xc });

		FileVO savedFile = baseEntityService.create(file);
		assertEquals(36, savedFile.getFileUUID().length());
	}

}
