package io.pelle.mango.demo.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import io.pelle.mango.client.base.property.IProperty;
import io.pelle.mango.client.base.property.IPropertyCategory;
import io.pelle.mango.client.base.property.IPropertyGroup;
import io.pelle.mango.client.core.property.PropertyBuilder;
import io.pelle.mango.client.core.property.PropertyProvider;
import io.pelle.mango.client.core.property.SystemProperties;
import io.pelle.mango.client.property.IPropertyService;
import io.pelle.mango.db.dao.BaseEntityDAO;
import io.pelle.mango.server.property.PropertyValue;

import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gwt.thirdparty.guava.common.collect.Iterables;

public class PropertyServiceTest extends BaseDemoTest {

	public static IProperty<String> DB_PROPERTY = PropertyBuilder.createStringProperty("dbproperty").database().name("test property");

	public static IProperty<String> DB_PROPERTY_WITH_DEFAULT = PropertyBuilder.createStringProperty("dbproperty.with.default").database().name("test property").defaultValue("aaa");

	public static IProperty<String> DB_SYSTEM_PROPERTY_FALLBACK = PropertyBuilder.createStringProperty("system.property.fallback").database().name("test property");

	public static IProperty<String> DB_PROPERTY_WITH_FALLBACK = PropertyBuilder.createStringProperty("db.property.with.fallback").database().name("test property").fallback(DB_SYSTEM_PROPERTY_FALLBACK);

	public static IProperty<String> SYSTEM_PROPERTY = PropertyBuilder.createStringProperty("systemproperty").system().name("test property");

	public static IProperty<String> SYSTEM_PROPERTY_WITH_DEFAULT = PropertyBuilder.createStringProperty("systemproperty.with.default").system().name("test property").defaultValue("ddd");

	@Autowired
	private BaseEntityDAO baseEntityDAO;

	@Autowired
	private IPropertyService propertyService;

	@Test
	public void testDefaultNameIsId() {
		IPropertyCategory category1 = PropertyProvider.getInstance().createCategory("zzz");
		assertEquals("zzz", category1.getName());
	}

	@Test
	public void testCreateSameCategory() {
		IPropertyCategory category1 = PropertyProvider.getInstance().createCategory("zzz");
		IPropertyCategory category2 = PropertyProvider.getInstance().createCategory("zzz");
		assertEquals(category1, category2);
	}

	@Test
	public void testCreateSameGroup() {
		IPropertyGroup group1 = PropertyProvider.getInstance().createGroup("ddd");
		IPropertyGroup group2 = PropertyProvider.getInstance().createGroup("ddd");
		assertEquals(group1, group2);
	}

	@Test
	public void testGetPropertyValues() {

		IProperty<String> property1 = PropertyBuilder.createStringProperty("property1").database().name("test property");
		IProperty<String> property2 = PropertyBuilder.createStringProperty("property2").database().name("test property");
		IProperty<String> property3 = PropertyBuilder.createStringProperty("property3").database().name("test property");
		IProperty<String> property4 = PropertyBuilder.createStringProperty("property4").database().name("test property");

		baseEntityDAO.deleteAll(PropertyValue.class);
		propertyService.setProperty(property1, "abc");
		propertyService.setProperty(property2, "def");
		propertyService.setProperty(property3, "ghi");
		propertyService.setProperty(property4, "klm");

		PropertyProvider.getInstance().addProperty(property1);

		IPropertyCategory category1 = PropertyProvider.getInstance().createCategory("category1");
		category1.addProperty(property2);
		PropertyProvider.getInstance().createGroup("group2").addProperty(property4);

		IPropertyGroup group1 = category1.createGroup("group1");
		group1.addProperty(property3);

		Map<IProperty<?>, Object> values = propertyService.getPropertyValues(PropertyProvider.getInstance().getRootCategory());
		assertEquals(2, values.size());
		assertTrue(Iterables.get(values.values(), 0).equals("abc") || Iterables.get(values.values(), 1).equals("abc"));
		assertTrue(Iterables.get(values.values(), 0).equals("klm") || Iterables.get(values.values(), 1).equals("klm"));

		values = propertyService.getPropertyValues(category1);
		assertEquals(2, values.size());
		assertTrue(Iterables.get(values.values(), 0).equals("def") || Iterables.get(values.values(), 1).equals("def"));
		assertTrue(Iterables.get(values.values(), 0).equals("ghi") || Iterables.get(values.values(), 1).equals("ghi"));
	}

	@Test
	public void testGetJavaVMInfoProperty() {
		String value = propertyService.getProperty(SystemProperties.JAVA_VM_INFO);
		System.out.println(value);
		assertNotNull(value);
	}

	@Test
	public void testGetDatabaseProperty() {

		baseEntityDAO.deleteAll(PropertyValue.class);
		assertNull(propertyService.getProperty(DB_PROPERTY));

		propertyService.setProperty(DB_PROPERTY, "zzz");
		assertEquals("zzz", propertyService.getProperty(DB_PROPERTY));
	}

	@Test
	public void testGetDatabasePropertyWithFallback() {

		baseEntityDAO.deleteAll(PropertyValue.class);

		assertNull(propertyService.getProperty(DB_PROPERTY_WITH_FALLBACK));

		propertyService.setProperty(DB_SYSTEM_PROPERTY_FALLBACK, "zzz");
		assertEquals("zzz", propertyService.getProperty(DB_PROPERTY_WITH_FALLBACK));
	}

	@Test
	public void testGetSystemProperty() {

		assertNull(propertyService.getProperty(SYSTEM_PROPERTY));

		propertyService.setProperty(SYSTEM_PROPERTY, "zzz");
		assertEquals("zzz", propertyService.getProperty(SYSTEM_PROPERTY));
	}

	@Test
	public void testGetSystemPropertyWithDefault() {
		assertEquals("ddd", propertyService.getProperty(SYSTEM_PROPERTY_WITH_DEFAULT));
	}

	@Test
	public void testGetDatabasePropertyWithDefault() {
		assertEquals("aaa", propertyService.getProperty(DB_PROPERTY_WITH_DEFAULT));
	}

}
