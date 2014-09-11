package io.pelle.mango.demo.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.pelle.mango.client.base.vo.StringAttributeDescriptor;
import io.pelle.mango.test.Entity1;
import io.pelle.mango.test.Entity2;
import io.pelle.mango.test.Entity3;
import io.pelle.mango.test.Entity4;
import io.pelle.mango.test.Entity5;
import io.pelle.mango.test.Entity6;
import io.pelle.mango.test.Entity7;
import io.pelle.mango.test.client.ENUMERATION1;
import io.pelle.mango.test.client.Entity1VO;
import io.pelle.mango.test.client.Entity2VO;
import io.pelle.mango.test.client.Entity3VO;
import io.pelle.mango.test.client.Entity4VO;
import io.pelle.mango.test.client.Entity5VO;
import io.pelle.mango.test.client.ValueObject1;
import io.pelle.mango.test.client.ValueObject2;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class EntityModelGeneratorTest {

	@Test
	public void testEntity1() {
		assertEquals("io.pelle.mango.test.Entity1", Entity1.class.getName());
	}

	@Test
	public void testStringAttributeDescriptor() {
		assertEquals(StringAttributeDescriptor.class, Entity1.STRINGDATATYPE1.getClass());
	}

	@Test
	public void testEntity1VO() {
		assertEquals("io.pelle.mango.test.client.Entity1VO", Entity1VO.class.getName());
	}

	@Test
	public void testENUMERATION1() {
		assertEquals("io.pelle.mango.test.client.ENUMERATION1", ENUMERATION1.class.getName());
	}

	// -------------------------------------------------------------------------
	// StringDatatype1
	// -------------------------------------------------------------------------
	@Test
	public void testEntity1StringDatatype1() {
		assertEquals(String.class, Entity1.STRINGDATATYPE1.getAttributeType());
		assertEquals(String.class, Entity1.STRINGDATATYPE1.getListAttributeType());
		assertEquals("stringDatatype1", Entity1.STRINGDATATYPE1.getAttributeName());
	}

	@Test
	public void testEntity1VOStringDatatype1() {
		assertEquals(String.class, Entity1VO.STRINGDATATYPE1.getAttributeType());
		assertEquals(String.class, Entity1VO.STRINGDATATYPE1.getListAttributeType());
		assertEquals("stringDatatype1", Entity1.STRINGDATATYPE1.getAttributeName());
	}

	// -------------------------------------------------------------------------
	// BooleanDatatype1
	// -------------------------------------------------------------------------
	@Test
	public void testEntity1BooleanDatatype1() {
		assertEquals(Boolean.class, Entity1.BOOLEANDATATYPE1.getAttributeType());
		assertEquals(Boolean.class, Entity1.BOOLEANDATATYPE1.getListAttributeType());
		assertEquals("booleanDatatype1", Entity1.BOOLEANDATATYPE1.getAttributeName());
	}

	@Test
	public void testEntity1VOBooleanDatatype1() {
		assertEquals(Boolean.class, Entity1VO.BOOLEANDATATYPE1.getAttributeType());
		assertEquals(Boolean.class, Entity1VO.BOOLEANDATATYPE1.getListAttributeType());
		assertEquals("booleanDatatype1", Entity1.BOOLEANDATATYPE1.getAttributeName());
	}

	// -------------------------------------------------------------------------
	// StringDatatype1 0..n
	// -------------------------------------------------------------------------
	@Test
	public void testEntity1StringDatatype1List() {
		assertEquals(List.class, Entity1.STRINGDATATYPE1LIST.getAttributeType());
		assertEquals(String.class, Entity1.STRINGDATATYPE1LIST.getListAttributeType());
		assertEquals("stringDatatype1List", Entity1.STRINGDATATYPE1LIST.getAttributeName());
	}

	@Test
	public void testEntity1VOStringDatatype1List() {
		assertEquals(List.class, Entity1.STRINGDATATYPE1LIST.getAttributeType());
		assertEquals(String.class, Entity1.STRINGDATATYPE1LIST.getListAttributeType());
		assertEquals("stringDatatype1List", Entity1.STRINGDATATYPE1LIST.getAttributeName());
	}

	// -------------------------------------------------------------------------
	// Entity2Datatype
	// -------------------------------------------------------------------------
	@Test
	public void testEntity1EnumerationDatatype() {
		assertEquals(ENUMERATION1.class, Entity1.ENUMERATION1DATATYPE.getAttributeType());
		assertEquals(ENUMERATION1.class, Entity1.ENUMERATION1DATATYPE.getListAttributeType());
		assertEquals("enumeration1Datatype", Entity1.ENUMERATION1DATATYPE.getAttributeName());
	}

	@Test
	public void testEntity1VOEnumerationDatatype() {
		assertEquals(ENUMERATION1.class, Entity1VO.ENUMERATION1DATATYPE.getAttributeType());
		assertEquals(ENUMERATION1.class, Entity1VO.ENUMERATION1DATATYPE.getListAttributeType());
		assertEquals("enumeration1Datatype", Entity1.ENUMERATION1DATATYPE.getAttributeName());
	}

	// -------------------------------------------------------------------------
	// Entity2Datatype
	// -------------------------------------------------------------------------
	@Test
	public void testEntity1Entity2Datatype() {
		assertEquals(Entity2.class, Entity1.ENTITY2DATATYPE.getAttributeType());
		assertEquals(Entity2.class, Entity1.ENTITY2DATATYPE.getListAttributeType());
		assertEquals("entity2Datatype", Entity1.ENTITY2DATATYPE.getAttributeName());
	}

	@Test
	public void testEntity1VOEntity2VODatatype() {
		assertEquals(Entity2VO.class, Entity1VO.ENTITY2DATATYPE.getAttributeType());
		assertEquals(Entity2VO.class, Entity1VO.ENTITY2DATATYPE.getListAttributeType());
		assertEquals("entity2Datatype", Entity1.ENTITY2DATATYPE.getAttributeName());
	}

	// -------------------------------------------------------------------------
	// Entity3Datatype 0..n
	// -------------------------------------------------------------------------
	@Test
	public void testEntity2Entity3DatatypeList() {
		assertEquals(List.class, Entity2.ENTITY3DATATYPES.getAttributeType());
		assertEquals(Entity3.class, Entity2.ENTITY3DATATYPES.getListAttributeType());
		assertEquals("entity3Datatypes", Entity2.ENTITY3DATATYPES.getAttributeName());
	}

	@Test
	public void testEntity2VOEntity3DatatypeVOList() {
		assertEquals(List.class, Entity2VO.ENTITY3DATATYPES.getAttributeType());
		assertEquals(Entity3VO.class, Entity2VO.ENTITY3DATATYPES.getListAttributeType());
		assertEquals("entity3Datatypes", Entity2VO.ENTITY3DATATYPES.getAttributeName());
	}

	// -------------------------------------------------------------------------
	// Entity4
	// -------------------------------------------------------------------------
	@Test
	public void testEntity5Entity4() {
		assertEquals(Entity4.class, Entity5.ENTITY4.getAttributeType());
		assertEquals(Entity4.class, Entity5.ENTITY4.getListAttributeType());
		assertEquals("entity4", Entity5.ENTITY4.getAttributeName());
	}

	@Test
	public void testEntity5VOEntity4VO() {
		assertEquals(Entity4VO.class, Entity5VO.ENTITY4.getAttributeType());
		assertEquals(Entity4VO.class, Entity5VO.ENTITY4.getListAttributeType());
		assertEquals("entity4", Entity5.ENTITY4.getAttributeName());
	}

	// -------------------------------------------------------------------------
	// Entity4 0..n
	// -------------------------------------------------------------------------
	@Test
	public void testEntity5Entity4List() {
		assertEquals(List.class, Entity5.ENTITY4S.getAttributeType());
		assertEquals(Entity4.class, Entity5.ENTITY4S.getListAttributeType());
		assertEquals("entity4s", Entity5.ENTITY4S.getAttributeName());
	}

	@Test
	public void testEntity5VOEntity4VOList() {
		assertEquals(List.class, Entity5VO.ENTITY4S.getAttributeType());
		assertEquals(Entity4VO.class, Entity5VO.ENTITY4S.getListAttributeType());
		assertEquals("entity4s", Entity5.ENTITY4S.getAttributeName());
	}

	// -------------------------------------------------------------------------
	// string
	// -------------------------------------------------------------------------
	@Test
	public void testEntity5String() {
		assertEquals(String.class, Entity5.STRING1.getAttributeType());
		assertEquals(String.class, Entity5.STRING1.getListAttributeType());
		assertEquals("string1", Entity5.STRING1.getAttributeName());
	}

	@Test
	public void testEntity5VOString() {
		assertEquals(String.class, Entity5VO.STRING1.getAttributeType());
		assertEquals(String.class, Entity5VO.STRING1.getListAttributeType());
		assertEquals("string1", Entity5VO.STRING1.getAttributeName());
	}

	// -------------------------------------------------------------------------
	// boolean
	// -------------------------------------------------------------------------
	@Test
	public void testEntity5Boolean() {
		assertEquals(Boolean.class, Entity5.BOOLEAN1.getAttributeType());
		assertEquals(Boolean.class, Entity5.BOOLEAN1.getListAttributeType());
		assertEquals("boolean1", Entity5.BOOLEAN1.getAttributeName());
	}

	@Test
	public void testEntity5VOBoolean() {
		assertEquals(Boolean.class, Entity5VO.BOOLEAN1.getAttributeType());
		assertEquals(Boolean.class, Entity5VO.BOOLEAN1.getListAttributeType());
		assertEquals("boolean1", Entity5VO.BOOLEAN1.getAttributeName());
	}

	// -------------------------------------------------------------------------
	// BinaryDatatype1
	// -------------------------------------------------------------------------
	@Test
	public void testEntity3BinaryDatatype1() {
		assertEquals(byte[].class, Entity3.BINARYDATATYPE1.getAttributeType());
		assertEquals(byte[].class, Entity3.BINARYDATATYPE1.getListAttributeType());
		assertEquals("binaryDatatype1", Entity3.BINARYDATATYPE1.getAttributeName());
	}

	@Test
	public void testEntity3VOBinaryDatatype1() {
		assertEquals(byte[].class, Entity4VO.BINARYDATATYPE1.getAttributeType());
		assertEquals(byte[].class, Entity4VO.BINARYDATATYPE1.getListAttributeType());
		assertEquals("binaryDatatype1", Entity4VO.BINARYDATATYPE1.getAttributeName());
	}

	// -------------------------------------------------------------------------
	// binary1
	// -------------------------------------------------------------------------
	@Test
	public void testEntity5Binary1() {
		assertEquals(byte[].class, Entity5.BINARY1.getAttributeType());
		assertEquals(byte[].class, Entity5.BINARY1.getListAttributeType());
		assertEquals("binary1", Entity5.BINARY1.getAttributeName());
	}

	@Test
	public void testEntity5VOBinary1() {
		assertEquals(byte[].class, Entity5VO.BINARY1.getAttributeType());
		assertEquals(byte[].class, Entity5VO.BINARY1.getListAttributeType());
		assertEquals("binary1", Entity5VO.BINARY1.getAttributeName());
	}

	// -------------------------------------------------------------------------
	// enumeration1
	// -------------------------------------------------------------------------
	@Test
	public void testEntity5Enumeration1() {
		assertEquals(ENUMERATION1.class, Entity5.ENUMERATION1.getAttributeType());
		assertEquals(ENUMERATION1.class, Entity5.ENUMERATION1.getListAttributeType());
		assertEquals("enumeration1", Entity5.ENUMERATION1.getAttributeName());
	}

	@Test
	public void testEntity5VOEnumeration1() {
		assertEquals(ENUMERATION1.class, Entity5VO.ENUMERATION1.getAttributeType());
		assertEquals(ENUMERATION1.class, Entity5VO.ENUMERATION1.getListAttributeType());
		assertEquals("enumeration1", Entity5VO.ENUMERATION1.getAttributeName());
	}

	@Test
	public void testGenericGetterSetterStringDatatype1() {
		Entity1VO entity1VO = new Entity1VO();
		assertEquals(null, entity1VO.get("stringDatatype1"));
		entity1VO.set("stringDatatype1", "aaa");
		assertEquals("aaa", entity1VO.get("stringDatatype1"));
	}

	@Test
	public void testGenericGetterSetterEnumeration1Datatype() {
		Entity1VO entity1VO = new Entity1VO();
		assertEquals(null, entity1VO.get("enumeration1Datatype"));
		entity1VO.set("enumeration1Datatype", ENUMERATION1.ENUMERATIONVALUE1);
		assertEquals(ENUMERATION1.ENUMERATIONVALUE1, entity1VO.get("enumeration1Datatype"));
	}

	@Test
	public void testValueObject1ValueObject2() {
		ValueObject1 valueObject1 = new ValueObject1();
		valueObject1.setValueObject2(new ValueObject2());
	}

	@Test
	public void testValueObject1ValueObject2List() {
		ValueObject1 valueObject1 = new ValueObject1();
		assertTrue(valueObject1.getValueObjects2().isEmpty());
	}

	@Test
	@Ignore
	public void testExtendsAttributeDescriptors() {
		assertEquals(Entity6.class, Entity6.STRING1.getParent().getVOEntityClass());
		assertEquals(Entity7.class, Entity7.STRING1.getParent().getVOEntityClass());
	}

	@Test
	public void testNaturalKey() {
		Entity1VO entity1VO = new Entity1VO();
		entity1VO.setStringDatatype1("abc");
		assertEquals("abc", entity1VO.getNaturalKey());
	}

}
