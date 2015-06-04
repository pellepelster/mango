package io.pelle.mango.demo.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import io.pelle.mango.client.base.vo.BigDecimalAttributeDescriptor;
import io.pelle.mango.client.base.vo.BooleanAttributeDescriptor;
import io.pelle.mango.client.base.vo.IEntityDescriptor;
import io.pelle.mango.client.base.vo.StringAttributeDescriptor;
import io.pelle.mango.demo.client.MangoDemoDictionaryModel;
import io.pelle.mango.demo.client.test.ENUMERATION1;
import io.pelle.mango.demo.client.test.Entity1VO;
import io.pelle.mango.demo.client.test.Entity2VO;
import io.pelle.mango.demo.client.test.Entity3VO;
import io.pelle.mango.demo.client.test.Entity4VO;
import io.pelle.mango.demo.client.test.Entity5VO;
import io.pelle.mango.demo.client.test.ValueObject1;
import io.pelle.mango.demo.client.test.ValueObject2;
import io.pelle.mango.demo.client.test.ValueObject3;
import io.pelle.mango.demo.client.test.ValueObject4;
import io.pelle.mango.demo.server.showcase.Company;
import io.pelle.mango.demo.server.showcase.Country;
import io.pelle.mango.demo.server.test.Entity1;
import io.pelle.mango.demo.server.test.Entity2;
import io.pelle.mango.demo.server.test.Entity3;
import io.pelle.mango.demo.server.test.Entity4;
import io.pelle.mango.demo.server.test.Entity5;
import io.pelle.mango.demo.server.test.Entity6;
import io.pelle.mango.demo.server.test.Entity7;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;

import org.junit.Ignore;
import org.junit.Test;

public class EntityModelGeneratorTest {

	@Test
	public void testEntity1() {
		assertEquals("io.pelle.mango.demo.server.test.Entity1", Entity1.class.getName());
	}

	@Test
	public void testStringAttributeDescriptor() {
		assertEquals(StringAttributeDescriptor.class, Entity1.STRINGDATATYPE1.getClass());
	}

	@Test
	public void testCountryExchangeRateReadOnly() {
		assertTrue(MangoDemoDictionaryModel.COUNTRY.COUNTRY_EDITOR.COUNTRY_EXCHANGE_RATE.isReadonly());
	}

	@Test
	public void testGetCountryNaturalKey() {

		Country country = new Country();
		country.setCountryIsoCode2("DE");

		assertTrue(country.hasNaturalKey());
		assertEquals("DE", country.getNaturalKey());
	}

	@Test
	public void testGetEntity5VOComposedNaturalKey() {

		Entity4 entity4 = new Entity4();
		entity4.setStringDatatype4("def");

		Entity5 entity5 = new Entity5();
		entity5.setString1("abc");
		entity5.setEntity4(entity4);

		assertTrue(entity5.hasNaturalKey());
		assertEquals("abc, def", entity5.getNaturalKey());
	}

	@Test
	public void testEntity2HasNoNaturalKey() {
		Entity2 entity2 = new Entity2();

		assertEquals(entity2.getNaturalKey(), entity2.toString());
		assertFalse(entity2.hasNaturalKey());
	}

	@Test
	public void testCountryNameWidth() {
		assertEquals(32, MangoDemoDictionaryModel.COUNTRY.COUNTRY_EDITOR.COUNTRY_NAME.getWidth());
	}

	@Test
	public void testEntity1VO() {
		assertEquals("io.pelle.mango.demo.client.test.Entity1VO", Entity1VO.class.getName());
	}

	@Test
	public void testENUMERATION1() {
		assertEquals("io.pelle.mango.demo.client.test.ENUMERATION1", ENUMERATION1.class.getName());
	}

	// -------------------------------------------------------------------------
	// StringDatatype1
	// -------------------------------------------------------------------------
	@Test
	public void testEntity1StringDatatype1() {
		assertEquals(String.class, Entity1.STRINGDATATYPE1.getAttributeType());
		assertEquals(String.class, Entity1.STRINGDATATYPE1.getListAttributeType());
		assertEquals("stringDatatype1", Entity1.STRINGDATATYPE1.getAttributeName());
		assertTrue(Entity1.STRINGDATATYPE1 instanceof StringAttributeDescriptor);
	}

	@Test
	public void testEntity1StringDatatype1JPAColumLength() throws NoSuchFieldException, SecurityException {
		Field field = Entity1.class.getDeclaredField("stringDatatype1");
		Column column = field.getAnnotation(Column.class);
		assertEquals(42, column.length());
	}

	@Test
	public void testEntity1VOStringDatatype1() {
		assertEquals(String.class, Entity1VO.STRINGDATATYPE1.getAttributeType());
		assertEquals(String.class, Entity1VO.STRINGDATATYPE1.getListAttributeType());
		assertEquals("stringDatatype1", Entity1VO.STRINGDATATYPE1.getAttributeName());
		assertTrue(Entity1VO.STRINGDATATYPE1 instanceof StringAttributeDescriptor);
	}

	// -------------------------------------------------------------------------
	// BooleanDatatype1
	// -------------------------------------------------------------------------
	@Test
	public void testEntity1BooleanDatatype1() {
		assertEquals(Boolean.class, Entity1.BOOLEANDATATYPE1.getAttributeType());
		assertEquals(Boolean.class, Entity1.BOOLEANDATATYPE1.getListAttributeType());
		assertEquals("booleanDatatype1", Entity1.BOOLEANDATATYPE1.getAttributeName());
		assertTrue(Entity1.BOOLEANDATATYPE1 instanceof BooleanAttributeDescriptor);
	}

	@Test
	public void testEntity1VOBooleanDatatype1() {
		assertEquals(Boolean.class, Entity1VO.BOOLEANDATATYPE1.getAttributeType());
		assertEquals(Boolean.class, Entity1VO.BOOLEANDATATYPE1.getListAttributeType());
		assertEquals("booleanDatatype1", Entity1VO.BOOLEANDATATYPE1.getAttributeName());
		assertTrue(Entity1VO.BOOLEANDATATYPE1 instanceof BooleanAttributeDescriptor);
	}

	// -------------------------------------------------------------------------
	// DecimalDatatype1
	// -------------------------------------------------------------------------
	@Test
	public void testEntity1DecimalDatatype1() {
		assertEquals(BigDecimal.class, Entity1.DECIMALDATATYPE1.getAttributeType());
		assertEquals(BigDecimal.class, Entity1.DECIMALDATATYPE1.getListAttributeType());
		assertEquals("decimalDatatype1", Entity1.DECIMALDATATYPE1.getAttributeName());
		assertTrue(Entity1.DECIMALDATATYPE1 instanceof BigDecimalAttributeDescriptor);
	}

	@Test
	public void testEntity1VODecimalDatatype1() {
		assertEquals(BigDecimal.class, Entity1VO.DECIMALDATATYPE1.getAttributeType());
		assertEquals(BigDecimal.class, Entity1VO.DECIMALDATATYPE1.getListAttributeType());
		assertEquals("decimalDatatype1", Entity1VO.DECIMALDATATYPE1.getAttributeName());
		assertTrue(Entity1VO.DECIMALDATATYPE1 instanceof BigDecimalAttributeDescriptor);
	}

	// -------------------------------------------------------------------------
	// DoubleDatatype1
	// -------------------------------------------------------------------------
	@Test
	public void testEntity1DoubleDatatype1() {
		assertEquals(Double.class, Entity1.DOUBLEDATATYPE1.getAttributeType());
		assertEquals(Double.class, Entity1.DOUBLEDATATYPE1.getListAttributeType());
		assertEquals("doubleDatatype1", Entity1.DOUBLEDATATYPE1.getAttributeName());
	}

	@Test
	public void testEntity1VODoubleDatatype1() {
		assertEquals(Double.class, Entity1VO.DOUBLEDATATYPE1.getAttributeType());
		assertEquals(Double.class, Entity1VO.DOUBLEDATATYPE1.getListAttributeType());
		assertEquals("doubleDatatype1", Entity1VO.DOUBLEDATATYPE1.getAttributeName());
	}

	// -------------------------------------------------------------------------
	// FloatDatatype1
	// -------------------------------------------------------------------------
	@Test
	public void testEntity1FloatDatatype1() {
		assertEquals(Float.class, Entity1.FLOATDATATYPE1.getAttributeType());
		assertEquals(Float.class, Entity1.FLOATDATATYPE1.getListAttributeType());
		assertEquals("floatDatatype1", Entity1.FLOATDATATYPE1.getAttributeName());
	}

	@Test
	public void testEntity1VOFloatDatatype1() {
		assertEquals(Float.class, Entity1VO.FLOATDATATYPE1.getAttributeType());
		assertEquals(Float.class, Entity1VO.FLOATDATATYPE1.getListAttributeType());
		assertEquals("floatDatatype1", Entity1VO.FLOATDATATYPE1.getAttributeName());
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
		assertEquals("enumeration1Datatype", Entity1VO.ENUMERATION1DATATYPE.getAttributeName());
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
		assertEquals("entity2Datatype", Entity1VO.ENTITY2DATATYPE.getAttributeName());
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

	@SuppressWarnings("rawtypes")
	@Test
	@Ignore
	public void testExtendsAttributeDescriptors() {
		assertTrue(Entity6.STRING1.getParent() instanceof IEntityDescriptor);
		assertTrue(Entity7.STRING1.getParent() instanceof IEntityDescriptor);
		assertEquals(Entity6.class, ((IEntityDescriptor) Entity6.STRING1.getParent()).getVOEntityClass());
		assertEquals(Entity7.class, ((IEntityDescriptor) Entity7.STRING1.getParent()).getVOEntityClass());
	}

	@Test
	public void testNaturalKey() {
		Entity1VO entity1VO = new Entity1VO();
		entity1VO.setStringDatatype1("abc");
		assertEquals("abc", entity1VO.getNaturalKey());
	}

	@Test
	public void testValueObject4ConstructorSetter() {

		ValueObject4 valueObject4 = new ValueObject4("sss");
		assertEquals("sss", valueObject4.getString4());
	}

	@Test
	public void testValueObject3ExtendsValueObject2CopyConstructor() {

		ValueObject2 valueObject2 = new ValueObject2();
		valueObject2.setString2("zzz");

		ValueObject3 valueObject3 = new ValueObject3(valueObject2);
		assertEquals("zzz", valueObject3.getString2());
	}

	@Test
	public void testCompanyHierarchicalAttributeDescriptors() {
		assertEquals(Company.PARENTCLASSNAME.getAttributeName(), "parentClassName");
		assertEquals(Company.PARENTID.getAttributeName(), "parentId");
	}

	@Test
	public void testValueObject4ExtendsValueObject3ExtendsValueObject2CopyConstructor() {

		ValueObject2 valueObject2 = new ValueObject2();
		valueObject2.setString2("zzz");

		ValueObject3 valueObject3 = new ValueObject3(valueObject2);
		valueObject3.setString3("xxx");

		ValueObject4 valueObject41 = new ValueObject4(valueObject3);
		assertEquals("zzz", valueObject41.getString2());
		assertEquals("xxx", valueObject41.getString3());

		ValueObject4 valueObject42 = new ValueObject4(valueObject2);
		assertEquals("zzz", valueObject42.getString2());
	}

}
