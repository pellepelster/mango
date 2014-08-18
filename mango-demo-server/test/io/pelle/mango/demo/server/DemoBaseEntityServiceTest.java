package io.pelle.mango.demo.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import io.pelle.mango.client.IBaseEntityService;
import io.pelle.mango.client.base.db.vos.Result;
import io.pelle.mango.client.base.messages.IValidationMessage;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.db.dao.IBaseVODAO;
import io.pelle.mango.test.client.Entity1VO;
import io.pelle.mango.test.client.Entity2VO;

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

		assertEquals("Natural key attribute 'stringDatatype1' can not be empty for entity 'io.pelle.mango.test.client.Entity1VO'", result1.getValidationMessages().get(0).getMessage());
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

	public void setBaseVODAO(IBaseVODAO baseVODAO) {
		this.baseVODAO = baseVODAO;
	}
}
