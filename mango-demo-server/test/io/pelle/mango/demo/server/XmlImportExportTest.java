package io.pelle.mango.demo.server;

import static org.junit.Assert.assertEquals;
import io.pelle.mango.client.IBaseEntityService;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.server.xml.XmlVOExporter;
import io.pelle.mango.server.xml.XmlVOImporter;
import io.pelle.mango.test.client.ENUMERATION1;
import io.pelle.mango.test.client.Entity1VO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class XmlImportExportTest extends BaseDemoTest {

	@Autowired
	private XmlVOExporter xmlVOExporter;

	@Autowired
	private XmlVOImporter xmlVOImporter;

	@Autowired
	private IBaseEntityService baseEntityService;

	@Test
	public void testSimpleExportImport() {

		baseEntityService.deleteAll(Entity1VO.class.getName());

		// create Entity1VO
		Entity1VO entity1VO = new Entity1VO();
		entity1VO.setStringDatatype1("string1");
		entity1VO.setBooleanDatatype1(true);
		entity1VO.setEnumeration1Datatype(ENUMERATION1.ENUMERATIONVALUE2);

		baseEntityService.create(entity1VO);
		List<Entity1VO> entity1VOs = new ArrayList<Entity1VO>();
		entity1VOs.add(entity1VO);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		this.xmlVOExporter.exportVOs(outputStream, entity1VOs, null);

		baseEntityService.deleteAll(Entity1VO.class.getName());

		System.out.println(outputStream.toString());
		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

		this.xmlVOImporter.importVOs(inputStream, null);

		entity1VOs = this.baseEntityService.filter(SelectQuery.selectFrom(Entity1VO.class));
		Assert.assertEquals(1, entity1VOs.size());

		assertEquals("string1", entity1VOs.get(0).getStringDatatype1());
		assertEquals(true, entity1VOs.get(0).getBooleanDatatype1());
		assertEquals(ENUMERATION1.ENUMERATIONVALUE2, entity1VOs.get(0).getEnumeration1Datatype());

	}

	public void setXmlVOExporter(XmlVOExporter xmlVOExporter) {
		this.xmlVOExporter = xmlVOExporter;
	}

	public void setBaseEntityService(IBaseEntityService baseEntityService) {
		this.baseEntityService = baseEntityService;
	}

	public void setXmlVOImporter(XmlVOImporter xmlVOImporter) {
		this.xmlVOImporter = xmlVOImporter;
	}

}
