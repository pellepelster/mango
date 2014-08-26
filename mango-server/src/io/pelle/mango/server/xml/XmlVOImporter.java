package io.pelle.mango.server.xml;

import io.pelle.mango.client.IBaseEntityService;
import io.pelle.mango.client.base.util.SimpleCallback;
import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.base.vo.query.expressions.ExpressionFactory;
import io.pelle.mango.db.util.BeanUtils;
import io.pelle.mango.server.base.xml.IXmlVOImporter;
import io.pelle.mango.server.base.xml.XmlElementDescriptor;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.dom.DOMSource;

import org.apache.commons.beanutils.ConstructorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;

import com.google.common.base.Optional;

@Component
public class XmlVOImporter extends BaseXmlVOHandler implements IXmlVOImporter {

	public interface BinaryFileReadCallback {
		byte[] readBinaryFile(String fileId);
	}

	private class VOAttributeSetteCallback implements SimpleCallback<IBaseVO> {

		private IBaseVO vo;

		private String attributeName;

		public VOAttributeSetteCallback(IBaseVO vo, String attributeName) {
			super();
			this.vo = vo;
			this.attributeName = attributeName;
		}

		@Override
		public void onCallback(IBaseVO t) {
			this.vo.set(this.attributeName, t);
		}
	}

	private class ListAdderCallback implements SimpleCallback<IBaseVO> {

		private List<IBaseVO> list;

		private String attributeName;

		public ListAdderCallback(List<IBaseVO> list) {
			super();
			this.list = list;
		}

		@Override
		public void onCallback(IBaseVO t) {
			this.list.add(t);
		}
	}

	@Autowired
	private XmlVOMapper voXmlMapper;

	@Autowired
	private IBaseEntityService baseEntityService;

	private XMLInputFactory inputFactory = XMLInputFactory.newInstance();

	public void importVOs(Element element, BinaryFileReadCallback binaryFileReadCallback) {
		try {
			DOMSource domSource = new DOMSource(element);
			XMLEventReader eventReader = inputFactory.createXMLEventReader(domSource);
			importVOsInternal(eventReader, binaryFileReadCallback);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public void importVOs(InputStream inputStream, BinaryFileReadCallback binaryFileReadCallback) {
		try {
			XMLEventReader eventReader = inputFactory.createXMLEventReader(inputStream);
			importVOsInternal(eventReader, binaryFileReadCallback);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private void importVOsInternal(XMLEventReader eventReader, BinaryFileReadCallback binaryFileReadCallback) throws Exception {

		while (eventReader.hasNext()) {

			XMLEvent event = eventReader.nextEvent();

			if (event.isStartElement()) {

				StartElement voStartElement = event.asStartElement();
				XmlElementDescriptor voStartElementDescriptor = this.voXmlMapper.getElementDescriptor(voStartElement.getName().getLocalPart());

				if (!voStartElementDescriptor.isList() && !voStartElementDescriptor.isReference()) {

					final IBaseVO vo = (IBaseVO) ConstructorUtils.invokeConstructor(voStartElementDescriptor.getVoClass(), new Object[0]);

					StartElement voAttributeStartElement = null;
					IAttributeDescriptor<?> attributeDescriptor = null;

					// loop value object attributes
					while (eventReader.hasNext() && !isEndElementFor(event, voStartElement)) {

						event = eventReader.nextEvent();

						if (attributeDescriptor != null) {

							if (isEndElementFor(event, voAttributeStartElement)) {
								voAttributeStartElement = null;
								attributeDescriptor = null;
								continue;
							}

							if (IBaseVO.class.isAssignableFrom(attributeDescriptor.getAttributeType())) {

								resolveVOReference(eventReader, event, (Class<IBaseVO>) attributeDescriptor.getAttributeType(), new VOAttributeSetteCallback(vo, attributeDescriptor.getAttributeName()));
							} else if (List.class.isAssignableFrom(attributeDescriptor.getAttributeType())) {
								if (event.isStartElement()) {
									StartElement listReferenceStartElement = event.asStartElement();

//									XmlElementDescriptor listReferenceXmlElementDescriptor = this.voXmlMapper.getElementDescriptor(listReferenceStartElement.getName().getLocalPart());

									resolveVOReference(eventReader, event, (Class<IBaseVO>) attributeDescriptor.getListAttributeType(), new ListAdderCallback((List<IBaseVO>) vo.get(attributeDescriptor.getAttributeName())));
								}
							} else if (attributeDescriptor.getAttributeType().isArray()) {
								if (binaryFileReadCallback != null) {
									String fileId = event.asCharacters().getData();
									byte[] content = binaryFileReadCallback.readBinaryFile(fileId);
									vo.set(attributeDescriptor.getAttributeName(), content);
								}
								continue;
							} else {
								Object convertedValue = fromXml(event.asCharacters().getData(), attributeDescriptor.getAttributeType());
								vo.set(attributeDescriptor.getAttributeName(), convertedValue);
								continue;
							}
						}
						if (attributeDescriptor == null && event.isStartElement()) {
							voAttributeStartElement = event.asStartElement();
							attributeDescriptor = BeanUtils.getAttributeDescriptor(vo.getClass(), voAttributeStartElement.getName().getLocalPart());
							continue;
						}
					}
					this.baseEntityService.create(vo);
				}
			}
		}
	}

	private void resolveVOReference(XMLEventReader eventReader, XMLEvent currentEvent, Class<IBaseVO> voClass, SimpleCallback<IBaseVO> callback) throws XMLStreamException {

		if (currentEvent.isStartElement()) {
			StartElement referenceStartElement = currentEvent.asStartElement();
			XmlElementDescriptor referenceXmlElementDescriptor = this.voXmlMapper.getElementDescriptor(referenceStartElement.getName().getLocalPart());
			if (referenceXmlElementDescriptor.isReference() && !referenceXmlElementDescriptor.isList()) {
				Map<String, Object> referenceAttibutes = new HashMap<String, Object>();
				XMLEvent event = currentEvent;
				while (eventReader.hasNext() && !isEndElementFor(event, referenceStartElement)) {
					event = eventReader.nextEvent();
					if (event.isStartElement()) {
						StartElement referenceAttributeStartElement = event.asStartElement();
						IAttributeDescriptor<?> referenceAttributeDescriptor = BeanUtils.getAttributeDescriptor(voClass, referenceAttributeStartElement.getName().getLocalPart());
						event = eventReader.nextEvent();
						Object convertedValue = fromXml(event.asCharacters().getData(), referenceAttributeDescriptor.getAttributeType());
						referenceAttibutes.put(referenceAttributeStartElement.getName().getLocalPart(), convertedValue);
					}
				}
				IBaseVO referencedVO = getWithCriteriaMap(voClass, referenceAttibutes);
				callback.onCallback(referencedVO);
			}
		}
	}

	private boolean isEndElementFor(XMLEvent currentEvent, StartElement startElement) {
		return startElement != null && currentEvent.isEndElement() && currentEvent.asEndElement().getName().getLocalPart().equals(startElement.getName().getLocalPart());
	}

	public void setBaseEntityService(IBaseEntityService baseEntityService) {
		this.baseEntityService = baseEntityService;
	}

	public void setVoXmlMapper(XmlVOMapper voXmlMapper) {
		this.voXmlMapper = voXmlMapper;
	}

	public <T extends IBaseVO> T getWithCriteriaMap(Class<T> voClass, Map<String, Object> criteriaMap) {

		SelectQuery<T> selectQuery = SelectQuery.selectFrom(voClass);

		Optional<IBooleanExpression> expression = Optional.absent();

		for (Map.Entry<String, Object> criteriaEntry : criteriaMap.entrySet()) {

			Optional<IBooleanExpression> compareExpression = ExpressionFactory.createEqualsExpression(voClass, criteriaEntry.getKey(), criteriaEntry.getValue());

			if (compareExpression.isPresent()) {
				if (expression.isPresent()) {
					expression = Optional.of(expression.get().and(compareExpression.get()));
				} else {
					expression = compareExpression;
				}
			}

		}

		if (expression.isPresent()) {
			selectQuery.where(expression.get());
		}

		List<T> vos = this.baseEntityService.filter(selectQuery);

		if (vos.size() == 1) {
			return vos.get(0);
		} else if (vos.size() == 0) {
			throw new RuntimeException(String.format("reference matches no value object (criterias: %s", criteriaMap.toString()));
		}
		{
			throw new RuntimeException(String.format("reference matches  more than than one (%d) value objects (criterias: %s", vos.size(), criteriaMap.toString()));
		}

	}

	@Override
	public void importVOs(Element element) {
		importVOs(element, null);
	}
}