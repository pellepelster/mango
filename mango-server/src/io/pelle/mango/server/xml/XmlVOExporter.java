package io.pelle.mango.server.xml;

import io.pelle.mango.client.base.db.vos.UUID;
import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.db.util.BeanUtils;
import io.pelle.mango.db.voquery.VOClassQuery;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class XmlVOExporter extends BaseXmlVOHandler {

	public interface BinaryFileWriteCallback {
		void writeBinaryFile(String fileId, byte[] content);
	}

	private static final String KEY_ATTRIBUTE_NAME = "key";

	private static final String VALUE_ATTRIBUTE_NAME = "value";

	private static final String KEY_VALUE_ELEMENT_NAME = "KeyValue";

	@Autowired
	private XmlVOMapper voXmlMapper;

	@Autowired
	private IBaseEntityService baseEntityService;

	private XMLEventFactory eventFactory = XMLEventFactory.newFactory();

	private XMLEvent END = this.eventFactory.createCharacters("\n");

	private XMLEvent TAB = this.eventFactory.createCharacters("\t");

	public <VOType extends IBaseVO> void exportVOs(OutputStream outputStream, List<VOType> vosToExport, BinaryFileWriteCallback binaryFileCallback) {
		try {

			int indentation = 0;

			VOType vo = vosToExport.get(0);

			String elementName = this.voXmlMapper.getElementName(vo.getClass());
			String listElementName = this.voXmlMapper.getListElementName(vo.getClass());

			IAttributeDescriptor<?>[] attributeDescriptors = BeanUtils.getAttributeDescriptors(vo.getClass());

			XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
			XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(outputStream);

			StartDocument startDocument = this.eventFactory.createStartDocument();
			eventWriter.add(startDocument);
			eventWriter.add(this.END);
			addIndentation(eventWriter, indentation);
			eventWriter.add(this.eventFactory.createStartElement("", "", listElementName));
			eventWriter.add(this.END);
			indentation++;

			for (VOType voToExport : vosToExport) {

				addIndentation(eventWriter, indentation);
				eventWriter.add(this.eventFactory.createStartElement("", "", elementName));
				eventWriter.add(this.END);
				indentation++;

				for (IAttributeDescriptor<?> attributeDescriptor : attributeDescriptors) {

					String attributeName = attributeDescriptor.getAttributeName();
					Object attributeValue = voToExport.get(attributeName);

					if (attributeValue != null) {

						Class<?> attributeClass = attributeDescriptor.getAttributeType();

						if (IBaseVO.class.isAssignableFrom(attributeClass)) {
							createAttributeStartElement(eventWriter, attributeName, indentation, true);
							createReferenceAttributeNode(eventWriter, (IBaseVO) attributeValue, indentation);
							createAttributeEndElement(eventWriter, attributeName, indentation, true);
						} else if (List.class.isAssignableFrom(attributeClass)) {
							@SuppressWarnings("unchecked")
							List<IBaseVO> voList = (List<IBaseVO>) attributeValue;
							if (!voList.isEmpty()) {
								createAttributeStartElement(eventWriter, attributeName, indentation, true);
								String referenceListElementName = this.voXmlMapper.getReferenceListElementName(attributeDescriptor.getListAttributeType());
								addIndentation(eventWriter, indentation + 1);
								eventWriter.add(this.eventFactory.createStartElement("", "", referenceListElementName));
								eventWriter.add(this.END);
								for (IBaseVO voItem : voList) {
									createReferenceAttributeNode(eventWriter, voItem, indentation + 2);
								}
								addIndentation(eventWriter, indentation + 1);
								eventWriter.add(this.eventFactory.createEndElement("", "", referenceListElementName));
								eventWriter.add(this.END);
								createAttributeEndElement(eventWriter, attributeName, indentation, true);
							}
						} else if (Map.class.isAssignableFrom(attributeClass)) {
							createAttributeStartElement(eventWriter, attributeName, indentation, true);
							@SuppressWarnings("unchecked")
							Map<Object, Object> map = (Map<Object, Object>) attributeValue;
							eventWriter.add(this.eventFactory.createStartElement("", "", attributeName));
							eventWriter.add(this.END);
							for (Map.Entry<Object, Object> mapEntry : map.entrySet()) {
								Attribute keyAttribute = this.eventFactory.createAttribute(KEY_ATTRIBUTE_NAME, mapEntry.getKey().toString());
								Attribute valueAttribute = this.eventFactory.createAttribute(VALUE_ATTRIBUTE_NAME, mapEntry.getValue().toString());
								List<Attribute> attributeList = Arrays.asList(keyAttribute, valueAttribute);
								List<String> namespaceList = Arrays.asList();
								StartElement startElement = this.eventFactory.createStartElement("", "", KEY_VALUE_ELEMENT_NAME, attributeList.iterator(), namespaceList.iterator());
								eventWriter.add(startElement);
								eventWriter.add(this.eventFactory.createEndElement("", "", KEY_VALUE_ELEMENT_NAME));
								eventWriter.add(this.END);
							}
							addIndentation(eventWriter, indentation);
							eventWriter.add(this.eventFactory.createEndElement("", "", attributeName));
							eventWriter.add(this.END);
							createAttributeEndElement(eventWriter, attributeName, indentation, true);
						} else if (attributeValue instanceof byte[]) {
							String fileId = UUID.uuid();
							createAttributeNode(eventWriter, attributeName, fileId, indentation);
							binaryFileCallback.writeBinaryFile(fileId, (byte[]) attributeValue);
						} else {
							createAttributeNode(eventWriter, attributeName, attributeValue, indentation);
						}
					}
				}
				indentation--;
				addIndentation(eventWriter, indentation);
				eventWriter.add(this.eventFactory.createEndElement("", "", elementName));
				eventWriter.add(this.END);
			}
			indentation--;
			addIndentation(eventWriter, indentation);
			eventWriter.add(this.eventFactory.createEndElement("", "", listElementName));
			eventWriter.add(this.eventFactory.createEndDocument());
			eventWriter.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void addIndentation(XMLEventWriter eventWriter, int indentation) throws XMLStreamException {
		for (int i = 0; i < indentation; i++) {
			eventWriter.add(this.TAB);
		}
	}

	private void createReferenceAttributeNode(XMLEventWriter eventWriter, IBaseVO vo, int indentation) throws XMLStreamException {

		String referenceElementName = this.voXmlMapper.getReferenceElementName(vo.getClass());
		addIndentation(eventWriter, indentation);
		eventWriter.add(this.eventFactory.createStartElement("", "", referenceElementName));
		eventWriter.add(this.END);

		Iterator<? extends IAttributeDescriptor<?>> naturalKeyAttributeDescriptors = VOClassQuery.createQuery(vo.getClass()).attributesDescriptors().naturalKeys().iterator();

		if (naturalKeyAttributeDescriptors.hasNext()) {
			createAttributeNode(eventWriter, IBaseVO.FIELD_ID.getAttributeName(), vo.getOid(), indentation + 1);
		} else {
			while (naturalKeyAttributeDescriptors.hasNext()) {
				IAttributeDescriptor<?> attributeDescriptor = naturalKeyAttributeDescriptors.next();
				createAttributeNode(eventWriter, attributeDescriptor.getAttributeName(), vo.get(attributeDescriptor.getAttributeName()), indentation + 1);
			}
		}

		addIndentation(eventWriter, indentation);
		eventWriter.add(this.eventFactory.createEndElement("", "", referenceElementName));
		eventWriter.add(this.END);
	}

	private void createAttributeStartElement(XMLEventWriter eventWriter, String name, int indentation, boolean indent) throws XMLStreamException {
		addIndentation(eventWriter, indentation);
		eventWriter.add(this.eventFactory.createStartElement("", "", name));
		if (indent) {
			eventWriter.add(this.END);
		}
	}

	private void createAttributeEndElement(XMLEventWriter eventWriter, String name, int indentation, boolean indent) throws XMLStreamException {
		if (indent) {
			addIndentation(eventWriter, indentation);
		}
		eventWriter.add(this.eventFactory.createEndElement("", "", name));
		eventWriter.add(this.END);
	}

	private void createAttributeNode(XMLEventWriter eventWriter, String name, Object value, int indentation) throws XMLStreamException {
		createAttributeStartElement(eventWriter, name, indentation, false);
		Characters characters = this.eventFactory.createCharacters(toXML(value));
		eventWriter.add(characters);
		createAttributeEndElement(eventWriter, name, indentation, false);
	}

	public void setVoXmlMapper(XmlVOMapper voXmlMapper) {
		this.voXmlMapper = voXmlMapper;
	}
}