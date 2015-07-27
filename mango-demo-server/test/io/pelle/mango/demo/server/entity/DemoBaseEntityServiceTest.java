package io.pelle.mango.demo.server.entity;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gwt.editor.client.Editor.Ignore;

import io.pelle.mango.client.api.webhook.WebhookVO;
import io.pelle.mango.client.base.db.vos.Result;
import io.pelle.mango.client.base.messages.IValidationMessage;
import io.pelle.mango.client.base.vo.query.DeleteQuery;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.db.dao.IBaseVODAO;
import io.pelle.mango.demo.client.test.ENUMERATION1;
import io.pelle.mango.demo.client.test.Entity1VO;
import io.pelle.mango.demo.client.test.Entity2VO;
import io.pelle.mango.demo.client.test.Entity3VO;
import io.pelle.mango.demo.client.test.Entity4VO;
import io.pelle.mango.demo.client.test.Entity5VO;
import io.pelle.mango.demo.client.test.Entity6VO;
import io.pelle.mango.demo.client.test.Entity7VO;
import io.pelle.mango.demo.server.BaseDemoTest;
import io.pelle.mango.server.api.webhook.Webhook;

public class DemoBaseEntityServiceTest extends BaseDemoTest {

	@Autowired
	private IBaseEntityService baseEntityService;

	@Autowired
	private IBaseVODAO baseVODAO;

	@Test
	public void testGetNewVO() {
		assertNotNull(baseEntityService.getNewVO(Entity1VO.class.getName(), null));
		assertTrue(baseEntityService.getNewVO(Entity2VO.class.getName(), null) instanceof Entity2VO);
	}

	@Before
	public void initTestData() {
		baseVODAO.deleteAll(Entity1VO.class);
		baseVODAO.deleteAll(Entity2VO.class);
		baseVODAO.deleteAll(Entity6VO.class);
		baseVODAO.deleteAll(Entity5VO.class);
		baseVODAO.deleteAll(Entity4VO.class);
	}

	@Test
	public void testCreateInfoVOEntity() {

		Date start = new Date();

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1("aaa");

		Result<Entity1VO> result1 = this.baseEntityService.validateAndCreate(entity1);
		assertEquals(0, result1.getValidationMessages().size());
		Date end = new Date();

		assertNull(result1.getValue().getCreateUser());
		assertNull(result1.getValue().getUpdateUser());
		assertTrue(result1.getValue().getCreateDate().after(start) && result1.getValue().getCreateDate().before(end));
		assertTrue(result1.getValue().getUpdateDate().after(start) && result1.getValue().getUpdateDate().before(end));
	}

	@Test
	public void testFilter() {

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1("aaa");

		Result<Entity1VO> result = baseEntityService.validateAndSave(entity1);

		assertEquals("aaa", result.getValue().getStringDatatype1());
		assertEquals(0, result.getValidationMessages().size());

		List<Entity1VO> filterResult = baseEntityService.filter(SelectQuery.selectFrom(Entity1VO.class));
		assertEquals(1, filterResult.size());
	}

	@Test
	public void testReadyByQuery() {

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1("aaa");
		assertTrue(baseEntityService.validateAndSave(entity1).isOk());

		entity1 = new Entity1VO();
		entity1.setStringDatatype1("bbb");
		assertTrue(baseEntityService.validateAndSave(entity1).isOk());

		entity1 = new Entity1VO();
		entity1.setStringDatatype1("ccc");
		assertTrue(baseEntityService.validateAndSave(entity1).isOk());

		assertNull(baseEntityService.read(SelectQuery.selectFrom(Entity1VO.class).where(Entity1VO.STRINGDATATYPE1.eq("zzz"))));
		assertNull(baseEntityService.read(SelectQuery.selectFrom(Entity1VO.class)));
		assertNotNull(baseEntityService.read(SelectQuery.selectFrom(Entity1VO.class).where(Entity1VO.STRINGDATATYPE1.eq("aaa"))));

	}

	@Test
	public void testEntity1Enumeration1List() {

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1("aaa");
		entity1.getEnumeration1Datatypes().add(ENUMERATION1.ENUMERATIONVALUE1);

		Result<Entity1VO> result = baseEntityService.validateAndSave(entity1);

		Entity1VO result1 = baseEntityService.read(result.getValue().getId(), Entity1VO.class.getName());
		assertEquals(ENUMERATION1.ENUMERATIONVALUE1, result.getValue().getEnumeration1Datatypes().get(0));
		assertEquals(ENUMERATION1.ENUMERATIONVALUE1, result1.getEnumeration1Datatypes().get(0));
	}

	@Test
	public void testFilterLongGreaterEquals() {

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1("aaa");
		entity1.setIntegerDatatype1(3);
		baseEntityService.validateAndSave(entity1);

		entity1 = new Entity1VO();
		entity1.setStringDatatype1("AAA");
		entity1.setIntegerDatatype1(6);
		baseEntityService.validateAndSave(entity1);

		List<Entity1VO> filterResult = baseEntityService.filter(SelectQuery.selectFrom(Entity1VO.class).where(Entity1VO.INTEGERDATATYPE1.greaterThan(3)));
		assertEquals(1, filterResult.size());

		filterResult = baseEntityService.filter(SelectQuery.selectFrom(Entity1VO.class).where(Entity1VO.INTEGERDATATYPE1.greaterThanEquals(3)));
		assertEquals(2, filterResult.size());
	}

	@Test
	public void testFilterLongLessEquals() {

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1("aaa");
		entity1.setIntegerDatatype1(3);
		baseEntityService.validateAndSave(entity1);

		entity1 = new Entity1VO();
		entity1.setStringDatatype1("AAA");
		entity1.setIntegerDatatype1(6);
		baseEntityService.validateAndSave(entity1);

		List<Entity1VO> filterResult = baseEntityService.filter(SelectQuery.selectFrom(Entity1VO.class).where(Entity1VO.INTEGERDATATYPE1.lessThan(6)));
		assertEquals(1, filterResult.size());

		filterResult = baseEntityService.filter(SelectQuery.selectFrom(Entity1VO.class).where(Entity1VO.INTEGERDATATYPE1.lessThanEquals(6)));
		assertEquals(2, filterResult.size());
	}

	@Test
	public void testFilterBooleanEquals() {

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1("aaa");
		entity1.setBooleanDatatype1(true);
		baseEntityService.validateAndSave(entity1);

		entity1 = new Entity1VO();
		entity1.setStringDatatype1("bbb");
		entity1.setBooleanDatatype1(false);
		baseEntityService.validateAndSave(entity1);

		List<Entity1VO> filterResult = baseEntityService.filter(SelectQuery.selectFrom(Entity1VO.class).where(Entity1VO.BOOLEANDATATYPE1.eq(true)));
		assertEquals(1, filterResult.size());
		assertEquals("aaa", filterResult.get(0).getStringDatatype1());

		filterResult = baseEntityService.filter(SelectQuery.selectFrom(Entity1VO.class).where(Entity1VO.BOOLEANDATATYPE1.eq(false)));
		assertEquals(1, filterResult.size());
		assertEquals("bbb", filterResult.get(0).getStringDatatype1());
	}

	@Test
	public void testFilterLongGreaterLess() {

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1("aaa");
		entity1.setIntegerDatatype1(3);
		baseEntityService.validateAndSave(entity1);

		entity1 = new Entity1VO();
		entity1.setStringDatatype1("AAA");
		entity1.setIntegerDatatype1(6);
		baseEntityService.validateAndSave(entity1);

		List<Entity1VO> filterResult = baseEntityService.filter(SelectQuery.selectFrom(Entity1VO.class).where(Entity1VO.INTEGERDATATYPE1.lessThan(5)));
		assertEquals(1, filterResult.size());
		assertEquals((Integer) 3, filterResult.get(0).getIntegerDatatype1());

		filterResult = baseEntityService.filter(SelectQuery.selectFrom(Entity1VO.class).where(Entity1VO.INTEGERDATATYPE1.greaterThan(5)));
		assertEquals(1, filterResult.size());
		assertEquals((Integer) 6, filterResult.get(0).getIntegerDatatype1());
	}

	@Test
	public void test() {

		Entity2VO entity2VO = new Entity2VO();
		entity2VO.setStringDatatype2("bbb");
		assertTrue(baseEntityService.validateAndCreate(entity2VO).isOk());

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1("aaa");
		entity1.setEntity2Datatype(entity2VO);

		assertTrue(baseEntityService.validateAndCreate(entity1).isOk());

	}

	@Test
	public void test1() {

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1("aaa");

		assertTrue(baseEntityService.validateAndCreate(entity1).isOk());

		assertTrue(baseEntityService.read(SelectQuery.selectFrom(Entity1VO.class)).getMetadata().isLoaded(Entity1VO.ENTITY2DATATYPE.getAttributeName()));
	}

	@Test
	public void testDeleteAll() {

		List<Entity1VO> entites = baseEntityService.filter(SelectQuery.selectFrom(Entity1VO.class));
		assertEquals(0, entites.size());

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1("aaa");
		assertTrue(baseEntityService.validateAndCreate(entity1).isOk());

		entites = baseEntityService.filter(SelectQuery.selectFrom(Entity1VO.class));
		assertEquals(1, entites.size());

		baseEntityService.deleteAll(Entity1VO.class.getName());

		entites = baseEntityService.filter(SelectQuery.selectFrom(Entity1VO.class));
		assertEquals(0, entites.size());

	}

	@Test
	public void testFilterStringIgnoreCase() {

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1("aaa");
		baseEntityService.validateAndSave(entity1);

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
	public void testFilterNested() {

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1("zzz1");

		Entity2VO entity2VO = new Entity2VO();
		entity2VO.setStringDatatype2("xxx1");
		entity1.setEntity2Datatype(entity2VO);

		Result<Entity1VO> result = baseEntityService.validateAndSave(entity1);
		assertTrue(result.getValidationMessages().isEmpty());

		entity1 = new Entity1VO();
		entity1.setStringDatatype1("zzz2");

		entity2VO = new Entity2VO();
		entity2VO.setStringDatatype2("xxx2");
		entity1.setEntity2Datatype(entity2VO);

		result = baseEntityService.validateAndSave(entity1);
		assertTrue(result.getValidationMessages().isEmpty());
		assertNotNull(result.getValue().getEntity2Datatype());

		List<Entity1VO> filterResult = baseEntityService.filter(SelectQuery.selectFrom(Entity1VO.class).where(Entity1VO.ENTITY2DATATYPE.path(Entity2VO.STRINGDATATYPE2).eq("uuu")));
		assertEquals(0, filterResult.size());

		filterResult = baseEntityService.filter(SelectQuery.selectFrom(Entity1VO.class));
		assertEquals(2, filterResult.size());

		filterResult = baseEntityService.filter(SelectQuery.selectFrom(Entity1VO.class).where(Entity1VO.ENTITY2DATATYPE.path(Entity2VO.STRINGDATATYPE2).eq("xxx1")));
		assertEquals(1, filterResult.size());
		assertEquals("xxx1", filterResult.get(0).getEntity2Datatype().getStringDatatype2());

	}

	@Test
	@Ignore
	public void testFilterByEnumeration() {

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1("zzz");
		entity1.setEnumeration1Datatype(ENUMERATION1.ENUMERATIONVALUE1);
		baseEntityService.validateAndSave(entity1);

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

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1("aaa");

		Result<Entity1VO> result = baseEntityService.validateAndSave(entity1);

		assertEquals("aaa", result.getValue().getStringDatatype1());
		assertEquals(0, result.getValidationMessages().size());
	}

	@Test
	public void testValidateAndCreate() {

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1("aaa");

		Result<Entity1VO> result = baseEntityService.validateAndCreate(entity1);

		assertEquals("aaa", result.getValue().getStringDatatype1());
		assertEquals(0, result.getValidationMessages().size());
	}

	@Test
	public void testValidateAndCreateEmptyNaturalKey() {

		Entity1VO entity1VO = new Entity1VO();
		Result<Entity1VO> result1 = this.baseEntityService.validateAndCreate(entity1VO);
		assertEquals(1, result1.getValidationMessages().size());

		assertEquals("Natural key attribute 'stringDatatype1' can not be empty for entity 'io.pelle.mango.demo.client.test.Entity1VO'", result1.getValidationMessages().get(0).getMessage());
		assertEquals("stringDatatype1", result1.getValidationMessages().get(0).getContext().get(IValidationMessage.ATTRIBUTE_CONTEXT_KEY));
	}

	@Test
	@Ignore
	public void testAutoLoadNaturalKeyReferences() {

		Entity4VO entity4 = new Entity4VO();
		entity4.setStringDatatype4("xxx");

		Entity5VO entity5 = new Entity5VO();
		entity5.setEntity4(entity4);
		entity5.setString1("yyy");

		assertEquals("yyy, xxx", entity5.getNaturalKey());

		Result<Entity5VO> entity5SaveResult = this.baseEntityService.validateAndCreate(entity5);
		assertEquals(0, entity5SaveResult.getValidationMessages().size());

		SelectQuery<Entity5VO> entity5Query = SelectQuery.selectFrom(Entity5VO.class);
		List<Entity5VO> entity5Result = baseEntityService.filter(entity5Query);
		assertEquals("yyy, xxx", entity5Result.get(0).getNaturalKey());

		Entity6VO entity6 = new Entity6VO();
		entity6.setString1("xxx");
		entity6.setEntity5(entity5SaveResult.getValue());

		Result<Entity6VO> entity6SaveResult = this.baseEntityService.validateAndCreate(entity6);
		assertEquals(0, entity6SaveResult.getValidationMessages().size());
		assertNotNull(entity6SaveResult.getValue().getEntity5());

		SelectQuery<Entity6VO> entity6Query = SelectQuery.selectFrom(Entity6VO.class).loadNaturalKeyReferences(true);
		List<Entity6VO> entity6Result = baseEntityService.filter(entity6Query);
		assertEquals("yyy, xxx", entity6Result.get(0).getEntity5().getNaturalKey());

	}

	@Test
	public void testAutoLoadFirstLevelInherited() {

		Entity4VO entity4 = new Entity4VO();
		entity4.setStringDatatype4("xxx");

		Entity5VO entity5 = new Entity5VO();
		entity5.setString1("xxx");
		entity5.setEntity4(entity4);

		Entity7VO entity7 = new Entity7VO();
		entity7.setEntity5(entity5);
		entity7.setString1("yyy");

		Result<Entity7VO> entity7SaveResult = this.baseEntityService.validateAndCreate(entity7);
		assertEquals(0, entity7SaveResult.getValidationMessages().size());

		SelectQuery<Entity7VO> entity7Query = SelectQuery.selectFrom(Entity7VO.class);
		List<Entity7VO> entity7Result = baseEntityService.filter(entity7Query);
		assertNotNull(entity7Result.get(0).getEntity5());

	}

	@Test
	public void testAutoLoadFirstLevel() {

		Entity4VO entity4 = new Entity4VO();
		entity4.setStringDatatype4("xxx");

		Entity5VO entity5VO = new Entity5VO();
		entity5VO.setEntity4(entity4);
		entity5VO.setString1("yyy");

		Result<Entity5VO> entity5VOSaveResult = this.baseEntityService.validateAndCreate(entity5VO);
		assertEquals(0, entity5VOSaveResult.getValidationMessages().size());

		SelectQuery<Entity5VO> entity5Query = SelectQuery.selectFrom(Entity5VO.class);
		List<Entity5VO> entity5Result = baseEntityService.filter(entity5Query);
		assertNotNull(entity5Result.get(0).getEntity4());

	}

	@Test
	public void testValidateAndCreateComposedNaturalKey1Level() {

		Entity5VO entity5 = new Entity5VO();
		entity5.setString1("aaa");

		Entity4VO entity4 = new Entity4VO();
		entity4.setStringDatatype4("bbb");
		entity5.setEntity4(entity4);

		assertTrue(this.baseEntityService.validateAndCreate(entity5).isOk());

		entity5 = new Entity5VO();
		entity5.setString1("aaa");
		entity4 = new Entity4VO();
		entity4.setStringDatatype4("bbb");
		entity5.setEntity4(entity4);

		Result<Entity5VO> result2 = this.baseEntityService.validateAndCreate(entity5);
		assertEquals(1, result2.getValidationMessages().size());
		assertEquals("An entity with the natural key 'aaa, bbb' already exists", result2.getValidationMessages().get(0).getMessage());

		// assertEquals("stringDatatype1",
		// result2.getValidationMessages().get(0).getContext().get(IValidationMessage.ATTRIBUTE_CONTEXT_KEY));
	}

	@Test
	public void testValidateAndCreateComposedNaturalKey2Levels() {

		Entity4VO entity4 = new Entity4VO();
		entity4.setStringDatatype4("xxx");

		Entity5VO entity5 = new Entity5VO();
		entity5.setEntity4(entity4);
		entity5.setString1("yyy");

		assertTrue(this.baseEntityService.validateAndCreate(entity5).isOk());

		entity5 = baseEntityService.read(SelectQuery.selectFrom(Entity5VO.class));

		assertNotNull(entity5);

		Entity6VO entity6 = new Entity6VO();
		entity6.setString1("xxx");
		entity6.setEntity5(entity5);

		assertTrue(this.baseEntityService.validateAndCreate(entity6).isOk());

	}

	@Test
	public void testValidateAndCreateDuplicateNaturalKey() {

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1("aaa");
		Result<Entity1VO> result1 = this.baseEntityService.validateAndCreate(entity1);
		assertEquals(0, result1.getValidationMessages().size());

		entity1 = new Entity1VO();
		entity1.setStringDatatype1("aaa");

		Result<Entity1VO> result2 = this.baseEntityService.validateAndCreate(entity1);
		assertEquals(1, result2.getValidationMessages().size());
		assertEquals("An entity with the natural key 'aaa' already exists", result2.getValidationMessages().get(0).getMessage());
		assertEquals("stringDatatype1", result2.getValidationMessages().get(0).getContext().get(IValidationMessage.ATTRIBUTE_CONTEXT_KEY));
	}

	@Test
	public void testValidateAndCreateEntityWihtoutNaturalKey() {

		Entity2VO entity2 = new Entity2VO();
		entity2.setStringDatatype2("aaa");
		assertTrue(this.baseEntityService.validateAndCreate(entity2).isOk());

		entity2 = new Entity2VO();
		entity2.setStringDatatype2("aaa");
		assertTrue(this.baseEntityService.validateAndCreate(entity2).isOk());
	}

	@Test
	public void testCreateAndSaveNaturalKey() {

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1("aaa");
		Result<Entity1VO> result1 = this.baseEntityService.validateAndCreate(entity1);
		assertEquals(0, result1.getValidationMessages().size());

		entity1 = result1.getValue();
		assertEquals("aaa", entity1.getStringDatatype1());
		entity1.setBooleanDatatype1(true);

		Result<Entity1VO> result2 = this.baseEntityService.validateAndSave(entity1);
		assertEquals(0, result2.getValidationMessages().size());
	}

	@Test
	public void testValidateAndCreateValidateMaxLength() {

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

		Result<Entity1VO> result = baseEntityService.validateAndCreate(entity1);

		assertEquals(1, result.getValidationMessages().size());
		assertEquals("Attribute 'stringDatatype1' is longer than 42", result.getValidationMessages().get(0).getMessage());
	}

	@Test
	public void testValidateAndCreateValidateMinLength() {

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1("a");

		Result<Entity1VO> result = baseEntityService.validateAndCreate(entity1);

		assertEquals(1, result.getValidationMessages().size());
		assertEquals("Attribute 'stringDatatype1' is shorter than 2", result.getValidationMessages().get(0).getMessage());
	}

	@Test
	public void testEntity1Sort() {

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1("aaa");
		baseEntityService.create(entity1);

		entity1 = new Entity1VO();
		entity1.setStringDatatype1("bbb");
		baseEntityService.create(entity1);

		entity1 = new Entity1VO();
		entity1.setStringDatatype1("ccc");
		baseEntityService.create(entity1);

		SelectQuery<Entity1VO> selectQuery = SelectQuery.selectFrom(Entity1VO.class).orderBy(Entity1VO.STRINGDATATYPE1);

		List<Entity1VO> result1 = this.baseEntityService.filter(selectQuery);
		assertEquals("aaa", result1.get(0).getStringDatatype1());
		assertEquals("bbb", result1.get(1).getStringDatatype1());
		assertEquals("ccc", result1.get(2).getStringDatatype1());

		selectQuery = SelectQuery.selectFrom(Entity1VO.class).orderBy(Entity1VO.STRINGDATATYPE1).descending();
		result1 = this.baseEntityService.filter(selectQuery);
		assertEquals("ccc", result1.get(0).getStringDatatype1());
		assertEquals("bbb", result1.get(1).getStringDatatype1());
		assertEquals("aaa", result1.get(2).getStringDatatype1());
	}

	@Test
	public void testEntity3VOBinaryDatatype1() {

		byte[] data = new byte[] { 0xa, 0xb, 0xc };

		Entity3VO entity3 = new Entity3VO();
		entity3.setBinaryDatatype1(data);

		Result<Entity3VO> result1 = this.baseEntityService.validateAndCreate(entity3);
		entity3 = this.baseEntityService.read(result1.getValue().getId(), Entity3VO.class.getName());
		assertArrayEquals(data, entity3.getBinaryDatatype1());
	}

	@Test
	public void testEntity3VOBinaryDatatype1Large() {

		int size = 1024 * 1204;
		byte[] data = new byte[size];

		for (int i = 0; i < size; i++) {
			data[i] = 0xa;
		}

		Entity3VO entity3 = new Entity3VO();
		entity3.setBinaryDatatype1(data);

		Result<Entity3VO> result1 = this.baseEntityService.validateAndCreate(entity3);
		entity3 = this.baseEntityService.read(result1.getValue().getId(), Entity3VO.class.getName());
		assertArrayEquals(data, entity3.getBinaryDatatype1());
	}

	@Test
	public void testDeleteQuery() {

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1("aaa");
		baseEntityService.validateAndCreate(entity1);

		entity1 = new Entity1VO();
		entity1.setStringDatatype1("bbb");
		baseEntityService.validateAndCreate(entity1);

		assertEquals(2, baseEntityService.filter(SelectQuery.selectFrom(Entity1VO.class)).size());

		baseEntityService.deleteQuery(DeleteQuery.deleteFrom(Entity1VO.class).where(Entity1VO.STRINGDATATYPE1.eq("aaa")));
		assertEquals(1, baseEntityService.filter(SelectQuery.selectFrom(Entity1VO.class)).size());

		baseEntityService.deleteQuery(DeleteQuery.deleteFrom(Entity1VO.class));
		assertEquals(0, baseEntityService.filter(SelectQuery.selectFrom(Entity1VO.class)).size());
	}

	@Test
	public void testCreateAndReadMap() {

		baseEntityService.deleteAll(Webhook.class.getName());

		WebhookVO webHook1 = new WebhookVO();
		webHook1.getConfig().put("aaa", "bbb");

		webHook1 = baseEntityService.create(webHook1);

		WebhookVO result = baseEntityService.read(webHook1.getId(), Webhook.class.getName());

		assertTrue(result.getConfig() instanceof HashMap);
		assertEquals(1, result.getConfig().size());
		assertEquals("bbb", result.getConfig().get("aaa"));
	}

	@Test
	public void testValidateAndCreate1() {

		Entity1VO entity1 = new Entity1VO();
		entity1.setStringDatatype1("aaa");

		Result<Entity1VO> result = baseEntityService.validateAndCreate(entity1);

		assertEquals("aaa", result.getValue().getStringDatatype1());
		assertEquals(0, result.getValidationMessages().size());
	}

}
