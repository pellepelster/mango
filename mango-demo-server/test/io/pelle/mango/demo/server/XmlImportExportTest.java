package io.pelle.mango.demo.server;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.demo.client.test.ENUMERATION1;
import io.pelle.mango.demo.client.test.Entity1VO;
import io.pelle.mango.server.xml.XmlVOExporter;
import io.pelle.mango.server.xml.XmlVOImporter;

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

	@Test
	public void testDomImportEntity() throws Exception {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		Document doc = builder.newDocument();

		Element entity1Element = doc.createElement("Entity1");

		doc.appendChild(entity1Element);

		Element stringDatatype1Element = doc.createElement("stringDatatype1");
		stringDatatype1Element.setTextContent("xxx");
		entity1Element.appendChild(stringDatatype1Element);

		baseEntityService.deleteAll(Entity1VO.class.getName());

		xmlVOImporter.importVOs(entity1Element, null);

		List<Entity1VO> entity1VOs = this.baseEntityService.filter(SelectQuery.selectFrom(Entity1VO.class));
		Assert.assertEquals(1, entity1VOs.size());

		assertEquals("xxx", entity1VOs.get(0).getStringDatatype1());

	}

	@Test
	public void testDomImportEntityList() throws Exception {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		Document doc = builder.newDocument();

		Element entityElementList = doc.createElement("Entity1List");
		doc.appendChild(entityElementList);

		// entity 1
		Element entity1Element = doc.createElement("Entity1");
		entityElementList.appendChild(entity1Element);
		Element stringDatatype1Element = doc.createElement("stringDatatype1");
		stringDatatype1Element.setTextContent("xxx");
		entity1Element.appendChild(stringDatatype1Element);

		// entity 2
		Element entity2Element = doc.createElement("Entity1");
		entityElementList.appendChild(entity2Element);
		Element stringDatatype2Element = doc.createElement("stringDatatype1");
		stringDatatype2Element.setTextContent("yyy");
		entity2Element.appendChild(stringDatatype2Element);

		baseEntityService.deleteAll(Entity1VO.class.getName());

		xmlVOImporter.importVOs(entityElementList, null);

		List<Entity1VO> entity1VOs = this.baseEntityService.filter(SelectQuery.selectFrom(Entity1VO.class));
		Assert.assertEquals(2, entity1VOs.size());

		assertEquals("xxx", entity1VOs.get(0).getStringDatatype1());
		assertEquals("yyy", entity1VOs.get(1).getStringDatatype1());

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
