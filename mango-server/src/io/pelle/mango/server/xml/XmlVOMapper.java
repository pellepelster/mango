package io.pelle.mango.server.xml;

import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.server.base.xml.IXmlVOMapper;
import io.pelle.mango.server.base.xml.XmlElementDescriptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class XmlVOMapper implements IXmlVOMapper {
	
	private Map<String, XmlElementDescriptor> elements = new HashMap<String, XmlElementDescriptor>();
	
	private Map<Class<?>, String> referenceListElementName = new HashMap<Class<?>, String>();
	
	private Map<Class<?>, String> referenceElementName = new HashMap<Class<?>, String>();
	
	private Map<Class<?>, String> listElementName = new HashMap<Class<?>, String>();
	
	private Map<Class<?>, String> elementName = new HashMap<Class<?>, String>();

	@Autowired
	public void setXmlVOMappers(List<IXmlVOMapper> xmlVOMappers) {
		for (IXmlVOMapper xmlVOMapper : xmlVOMappers) {
			
			this.elements.putAll(xmlVOMapper.getElements());
			
			for (Map.Entry<String, XmlElementDescriptor> elementEntry : xmlVOMapper.getElements().entrySet()) {
				
				XmlElementDescriptor xmlElementDescriptor = elementEntry.getValue();
				
				if (xmlElementDescriptor.isList() & xmlElementDescriptor.isReference()) {
					this.referenceListElementName.put(xmlElementDescriptor.getVoClass(), elementEntry.getKey());
					continue;
				}
				if (!xmlElementDescriptor.isList() & xmlElementDescriptor.isReference()) {
					this.referenceElementName.put(xmlElementDescriptor.getVoClass(), elementEntry.getKey());
					continue;
				}
				if (!xmlElementDescriptor.isList() & !xmlElementDescriptor.isReference()) {
					this.elementName.put(xmlElementDescriptor.getVoClass(), elementEntry.getKey());
					continue;
				}
				if (xmlElementDescriptor.isList() & !xmlElementDescriptor.isReference()) {
					this.listElementName.put(xmlElementDescriptor.getVoClass(), elementEntry.getKey());
					continue;
				}
			}
		}
	}

	public XmlElementDescriptor getElementDescriptor(String elementName) {
		
		XmlElementDescriptor xmlElementDescriptor = this.elements.get(elementName);
		
		if (xmlElementDescriptor == null) {
			throw new RuntimeException(String.format("no XmlElementDescriptor found for '%s'", elementName));
		}
		
		return xmlElementDescriptor;
	}

	public String getReferenceListElementName(Class<?> voClass) {
		return this.referenceListElementName.get(voClass);
	}

	public String getReferenceElementName(Class<?> voClass) {
		return this.referenceElementName.get(voClass);
	}

	public String getListElementName(Class<?> voClass) {
		return this.listElementName.get(voClass);
	}

	public String getElementName(Class<?> voClass) {
		return this.elementName.get(voClass);
	}

	@Override
	public Map<String, XmlElementDescriptor> getElements() {
		return this.elements;
	}

	public Class<? extends IBaseVO> getVOClassForElementName(String elementName) {
		return this.elements.get(elementName).getVoClass();
	}
}