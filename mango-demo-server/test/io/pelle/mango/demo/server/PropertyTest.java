package io.pelle.mango.demo.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import io.pelle.mango.client.base.property.IProperty;
import io.pelle.mango.client.core.property.PropertiesBuilder;
import io.pelle.mango.client.core.property.PropertyBuilder;
import io.pelle.mango.client.core.property.SystemProperties;
import io.pelle.mango.client.property.IPropertyService;
import io.pelle.mango.client.property.PropertyCategory;
import io.pelle.mango.db.dao.BaseEntityDAO;
import io.pelle.mango.server.property.PropertyValue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gwt.editor.client.Editor.Ignore;

public class PropertyTest extends BaseDemoTest {

	public static IProperty<String> DB_PROPERTY = PropertyBuilder.createStringDatabaseProperty("dbproperty", "test property");

	public static IProperty<String> DB_PROPERTY_WITH_DEFAULT = PropertyBuilder.createStringDatabaseProperty("dbproperty.with.default", "test property").defaultValue("aaa");

	public static IProperty<String> SYSTEM_PROPERTY_FALLBACK = PropertyBuilder.createStringDatabaseProperty("system.property.fallback", "test property");

	public static IProperty<String> DB_PROPERTY_WITH_FALLBACK = PropertyBuilder.createStringDatabaseProperty("db.property.with.fallback", "test property").fallback(SYSTEM_PROPERTY_FALLBACK);

	public static IProperty<String> SYSTEM_PROPERTY = PropertyBuilder.createStringDatabaseProperty("systemproperty", "test property");

	public static IProperty<String> SYSTEM_PROPERTY_WITH_DEFAULT = PropertyBuilder.createStringDatabaseProperty("systemproperty.with.default", "test property").defaultValue("ddd");

	@Autowired
	private BaseEntityDAO baseEntityDAO;

	@Autowired
	private IPropertyService propertyService;

	@Test
	@org.junit.Ignore
	public void testCreateCategory() {

		PropertiesBuilder.getInstance().createCategory("zzz");
		List<PropertyCategory> propertyCategories = PropertiesBuilder.getInstance().getRootCategories();
		assertEquals(1, propertyCategories.size());
		assertEquals("zzz", propertyCategories.get(0).getName());

		PropertiesBuilder.getInstance().createCategory("zzz");
		propertyCategories = PropertiesBuilder.getInstance().getRootCategories();
		assertEquals(1, propertyCategories.size());
		assertEquals("zzz", propertyCategories.get(0).getName());
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

		propertyService.setProperty(SYSTEM_PROPERTY_FALLBACK, "zzz");
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

	@Test
	@Ignore
	public void testCreateProperty() {

		// PropertiesBuilder.getInstance().createCategory("zzz").createSystemProperty("uuu");
		// List<PropertyCategory> propertyCategories =
		// PropertiesBuilder.getInstance().getRootCategories();
		// assertEquals("zzz", propertyCategories.get(0).getName());
	}

}
