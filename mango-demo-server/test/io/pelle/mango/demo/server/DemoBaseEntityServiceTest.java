package io.pelle.mango.demo.server;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import io.pelle.mango.client.base.db.vos.Result;
import io.pelle.mango.client.base.messages.IValidationMessage;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.baseentityservice.IBaseEntityService;
import io.pelle.mango.db.dao.IBaseVODAO;
import io.pelle.mango.demo.client.test.ENUMERATION1;
import io.pelle.mango.demo.client.test.Entity1VO;
import io.pelle.mango.demo.client.test.Entity2VO;
import io.pelle.mango.demo.client.test.Entity3VO;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoBaseEntityServiceTest extends BaseDemoTest {

	@Autowired
	private IBaseEntityService baseEntityService;

	@Autowired
	private IBaseVODAO baseVODAO;

	public void setBaseEntityService(IBaseEntityService baseEntityService) {
		this.baseEntityService = baseEntityService;
	}

	@Test
	public void testGetNewVO() {
		assertNotNull(baseEntityService.getNewVO(Entity1VO.class.getName(), null));
		assertTrue(baseEntityService.getNewVO(Entity2VO.class.getName(), null) instanceof Entity2VO);
	}

	@Test
	public void testCreateInfoVOEntity() {

		Date start = new Date();
		baseEntityService.deleteAll(Entity1VO.class.getName());

		Entity1VO entity1VO = new Entity1VO();
		entity1VO.setStringDatatype1("aaa");

		Result<Entity1VO> result1 = this.baseEntityService.validateAndCreate(entity1VO);
		assertEquals(0, result1.getValidationMessages().size());
		Date end = new Date();

		assertNull(result1.getVO().getCreateUser());
		assertNull(result1.getVO().getUpdateUser());
		assertTrue(result1.getVO().getCreateDate().after(start) && result1.getVO().getCreateDate().before(end));
		assertTrue(result1.getVO().getUpdateDate().after(start) && result1.getVO().getUpdateDate().before(end));
	}

	@Test
	public void testFilter() {

		baseVODAO.deleteAll(Entity1VO.class);

		Entity1VO entity1VO = new Entity1VO();
		entity1VO.setStringDatatype1("aaa");

		Result<Entity1VO> result = baseEntityService.validateAndSave(entity1VO);

		assertEquals("aaa", result.getVO().getStringDatatype1());
		assertEquals(0, result.getValidationMessages().size());

		List<Entity1VO> filterResult = baseEntityService.filter(SelectQuery.selectFrom(Entity1VO.class));
		assertEquals(1, filterResult.size());
	}

	@Test
	public void testEntity1Enumeration1List() {

		baseVODAO.deleteAll(Entity1VO.class);

		Entity1VO entity1VO = new Entity1VO();
		entity1VO.setStringDatatype1("aaa");
		entity1VO.getEnumeration1Datatypes().add(ENUMERATION1.ENUMERATIONVALUE1);

		Result<Entity1VO> result = baseEntityService.validateAndSave(entity1VO);

		Entity1VO result1 = baseEntityService.read(result.getVO().getId(), Entity1VO.class.getName());
		assertEquals(ENUMERATION1.ENUMERATIONVALUE1, result.getVO().getEnumeration1Datatypes().get(0));
		assertEquals(ENUMERATION1.ENUMERATIONVALUE1, result1.getEnumeration1Datatypes().get(0));
	}

	@Test
	public void testFilterStringIgnoreCase() {

		baseVODAO.deleteAll(Entity1VO.class);

		Entity1VO entity1VO = new Entity1VO();
		entity1VO.setStringDatatype1("aaa");
		baseEntityService.validateAndSave(entity1VO);

		Entity1VO entity2VO = new Entity1VO();
		entity2VO.setStringDatatype1("AAA");
		baseEntityService.validateAndSave(entity2VO);

		List<Entity1VO> filterResult = baseEntityService.filter(SelectQuery.selectFrom(Entity1VO.class).where(Entity1VO.STRINGDATATYPE1.eq("aaa")));
		assertEquals(1, filterResult.size());
		assertEquals("aaa", filterResult.get(0).getStringDatatype1());

		filterResult = baseEntityService.filter(SelectQuery.selectFrom(Entity1VO.class).where(Entity1VO.STRINGDATATYPE1.eqIgnoreCase("aaa")));
		assertEquals(2, filterResult.size());
		assertTrue("aaa".equals(filterResult.get(0).getStringDatatype1()) || "aaa".equals(filterResult.get(1).getStringDatatype1()));
		assertTrue("AAA".equals(filterResult.get(0).getStringDatatype1()) || "AAA".equals(filterResult.get(1).getStringDatatype1()));

	}

	@Test
	public void testFilterByEnumeration() {

		baseVODAO.deleteAll(Entity1VO.class);

		Entity1VO entity1VO = new Entity1VO();
		entity1VO.setStringDatatype1("zzz");
		entity1VO.setEnumeration1Datatype(ENUMERATION1.ENUMERATIONVALUE1);
		baseEntityService.validateAndSave(entity1VO);

		Entity1VO entity2VO = new Entity1VO();
		entity2VO.setStringDatatype1("uuu");
		entity2VO.setEnumeration1Datatype(ENUMERATION1.ENUMERATIONVALUE2);
		baseEntityService.validateAndSave(entity2VO);

		List<Entity1VO> filterResult = baseEntityService.filter(SelectQuery.selectFrom(Entity1VO.class).where(Entity1VO.ENUMERATION1DATATYPE.eq(ENUMERATION1.ENUMERATIONVALUE1)));
		assertEquals(1, filterResult.size());
		assertEquals(ENUMERATION1.ENUMERATIONVALUE1, filterResult.get(0).getEnumeration1Datatype());

		filterResult = baseEntityService.filter(SelectQuery.selectFrom(Entity1VO.class).where(Entity1VO.ENUMERATION1DATATYPE.neq(ENUMERATION1.ENUMERATIONVALUE1)));
		assertEquals(1, filterResult.size());
		assertEquals(ENUMERATION1.ENUMERATIONVALUE2, filterResult.get(0).getEnumeration1Datatype());

	}

	@Test
	public void testValidateAndSave() {

		Entity1VO entity1VO = new Entity1VO();
		entity1VO.setStringDatatype1("aaa");

		Result<Entity1VO> result = baseEntityService.validateAndSave(entity1VO);

		assertEquals("aaa", result.getVO().getStringDatatype1());
		assertEquals(0, result.getValidationMessages().size());
	}

	@Test
	public void testValidateAndCreate() {

		baseEntityService.deleteAll(Entity1VO.class.getName());

		Entity1VO entity1VO = new Entity1VO();
		entity1VO.setStringDatatype1("aaa");

		Result<Entity1VO> result = baseEntityService.validateAndCreate(entity1VO);

		assertEquals("aaa", result.getVO().getStringDatatype1());
		assertEquals(0, result.getValidationMessages().size());
	}

	@Test
	public void testValidateAndCreateEmptyNaturalKey() {

		baseEntityService.deleteAll(Entity1VO.class.getName());

		Entity1VO entity1VO = new Entity1VO();
		Result<Entity1VO> result1 = this.baseEntityService.validateAndCreate(entity1VO);
		assertEquals(1, result1.getValidationMessages().size());

		assertEquals("Natural key attribute 'stringDatatype1' can not be empty for entity 'io.pelle.mango.demo.client.test.Entity1VO'", result1.getValidationMessages().get(0).getMessage());
		assertEquals("stringDatatype1", result1.getValidationMessages().get(0).getContext().get(IValidationMessage.ATTRIBUTE_CONTEXT_KEY));
	}

	@Test
	public void testValidateAndCreateDuplicateNaturalKey() {
		baseEntityService.deleteAll(Entity1VO.class.getName());

		Entity1VO entity1VO = new Entity1VO();
		entity1VO.setStringDatatype1("aaa");
		Result<Entity1VO> result1 = this.baseEntityService.validateAndCreate(entity1VO);
		assertEquals(0, result1.getValidationMessages().size());

		entity1VO = new Entity1VO();
		entity1VO.setStringDatatype1("aaa");

		Result<Entity1VO> result2 = this.baseEntityService.validateAndCreate(entity1VO);
		assertEquals(1, result2.getValidationMessages().size());
		assertEquals("An entity with the natural key 'aaa' already exists", result2.getValidationMessages().get(0).getMessage());
		assertEquals("stringDatatype1", result2.getValidationMessages().get(0).getContext().get(IValidationMessage.ATTRIBUTE_CONTEXT_KEY));
	}

	@Test
	public void testCreateAndSaveNaturalKey() {

		baseEntityService.deleteAll(Entity1VO.class.getName());

		Entity1VO entity1VO = new Entity1VO();
		entity1VO.setStringDatatype1("aaa");
		Result<Entity1VO> result1 = this.baseEntityService.validateAndCreate(entity1VO);
		assertEquals(0, result1.getValidationMessages().size());

		entity1VO = result1.getVO();
		assertEquals("aaa", entity1VO.getStringDatatype1());
		entity1VO.setBooleanDatatype1(true);

		Result<Entity1VO> result2 = this.baseEntityService.validateAndSave(entity1VO);
		assertEquals(0, result2.getValidationMessages().size());
	}

	@Test
	public void testValidateAndCreateValidateMaxLength() {

		baseEntityService.deleteAll(Entity1VO.class.getName());

		Entity1VO entity1VO = new Entity1VO();
		entity1VO.setStringDatatype1("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

		Result<Entity1VO> result = baseEntityService.validateAndCreate(entity1VO);

		assertEquals(1, result.getValidationMessages().size());
		assertEquals("Attribute 'stringDatatype1' is longer than 42", result.getValidationMessages().get(0).getMessage());
	}

	@Test
	public void testValidateAndCreateValidateMinLength() {

		baseEntityService.deleteAll(Entity1VO.class.getName());

		Entity1VO entity1VO = new Entity1VO();
		entity1VO.setStringDatatype1("a");

		Result<Entity1VO> result = baseEntityService.validateAndCreate(entity1VO);

		assertEquals(1, result.getValidationMessages().size());
		assertEquals("Attribute 'stringDatatype1' is shorter than 2", result.getValidationMessages().get(0).getMessage());
	}

	@Test
	public void testEntity3VOBinaryDatatype1() {

		byte[] data = new byte[] { 0xa, 0xb, 0xc };

		baseEntityService.deleteAll(Entity3VO.class.getName());

		Entity3VO entity3VO = new Entity3VO();
		entity3VO.setBinaryDatatype1(data);

		Result<Entity3VO> result1 = this.baseEntityService.validateAndCreate(entity3VO);
		entity3VO = this.baseEntityService.read(result1.getVO().getId(), Entity3VO.class.getName());
		assertArrayEquals(data, entity3VO.getBinaryDatatype1());
	}

	@Test
	public void testEntity3VOBinaryDatatype1Large() {

		int size = 1024 * 1204;
		byte[] data = new byte[size];

		for (int i = 0; i < size; i++) {
			data[i] = 0xa;
		}

		baseEntityService.deleteAll(Entity3VO.class.getName());

		Entity3VO entity3VO = new Entity3VO();
		entity3VO.setBinaryDatatype1(data);

		Result<Entity3VO> result1 = this.baseEntityService.validateAndCreate(entity3VO);
		entity3VO = this.baseEntityService.read(result1.getVO().getId(), Entity3VO.class.getName());
		assertArrayEquals(data, entity3VO.getBinaryDatatype1());
	}

	@Test
	public void testValidateAndCreate1() {

		baseEntityService.deleteAll(Entity1VO.class.getName());

		Entity1VO entity1VO = new Entity1VO();
		entity1VO.setStringDatatype1("aaa");

		Result<Entity1VO> result = baseEntityService.validateAndCreate(entity1VO);

		assertEquals("aaa", result.getVO().getStringDatatype1());
		assertEquals(0, result.getValidationMessages().size());
	}

	public void setBaseVODAO(IBaseVODAO baseVODAO) {
		this.baseVODAO = baseVODAO;
	}
}
