package io.pelle.mango.test;

import java.util.HashMap;
import java.util.Map;
import io.pelle.mango.server.base.xml.XmlElementDescriptor;

@org.springframework.stereotype.Component
public class MangoDemoXmlVOMapper implements io.pelle.mango.server.base.xml.IXmlVOMapper
{
	
	@java.lang.SuppressWarnings("serial")
	private Map<String, XmlElementDescriptor> elements = new HashMap<String, XmlElementDescriptor>()
	{
		private final long serialVersionUID = 1L;
		{
			put("Entity1List", new XmlElementDescriptor(io.pelle.mango.test.client.Entity1VO.class, true, false));
			put("Entity1", new XmlElementDescriptor(io.pelle.mango.test.client.Entity1VO.class, false, false));
			put("Entity1ReferenceList", new XmlElementDescriptor(io.pelle.mango.test.client.Entity1VO.class, true, true));
			put("Entity1Reference", new XmlElementDescriptor(io.pelle.mango.test.client.Entity1VO.class, false, true));
			put("Entity2List", new XmlElementDescriptor(io.pelle.mango.test.client.Entity2VO.class, true, false));
			put("Entity2", new XmlElementDescriptor(io.pelle.mango.test.client.Entity2VO.class, false, false));
			put("Entity2ReferenceList", new XmlElementDescriptor(io.pelle.mango.test.client.Entity2VO.class, true, true));
			put("Entity2Reference", new XmlElementDescriptor(io.pelle.mango.test.client.Entity2VO.class, false, true));
			put("Entity3List", new XmlElementDescriptor(io.pelle.mango.test.client.Entity3VO.class, true, false));
			put("Entity3", new XmlElementDescriptor(io.pelle.mango.test.client.Entity3VO.class, false, false));
			put("Entity3ReferenceList", new XmlElementDescriptor(io.pelle.mango.test.client.Entity3VO.class, true, true));
			put("Entity3Reference", new XmlElementDescriptor(io.pelle.mango.test.client.Entity3VO.class, false, true));
			put("Entity4List", new XmlElementDescriptor(io.pelle.mango.test.client.Entity4VO.class, true, false));
			put("Entity4", new XmlElementDescriptor(io.pelle.mango.test.client.Entity4VO.class, false, false));
			put("Entity4ReferenceList", new XmlElementDescriptor(io.pelle.mango.test.client.Entity4VO.class, true, true));
			put("Entity4Reference", new XmlElementDescriptor(io.pelle.mango.test.client.Entity4VO.class, false, true));
			put("Entity5List", new XmlElementDescriptor(io.pelle.mango.test.client.Entity5VO.class, true, false));
			put("Entity5", new XmlElementDescriptor(io.pelle.mango.test.client.Entity5VO.class, false, false));
			put("Entity5ReferenceList", new XmlElementDescriptor(io.pelle.mango.test.client.Entity5VO.class, true, true));
			put("Entity5Reference", new XmlElementDescriptor(io.pelle.mango.test.client.Entity5VO.class, false, true));
			put("Entity6List", new XmlElementDescriptor(io.pelle.mango.test.client.Entity6VO.class, true, false));
			put("Entity6", new XmlElementDescriptor(io.pelle.mango.test.client.Entity6VO.class, false, false));
			put("Entity6ReferenceList", new XmlElementDescriptor(io.pelle.mango.test.client.Entity6VO.class, true, true));
			put("Entity6Reference", new XmlElementDescriptor(io.pelle.mango.test.client.Entity6VO.class, false, true));
			put("Entity7List", new XmlElementDescriptor(io.pelle.mango.test.client.Entity7VO.class, true, false));
			put("Entity7", new XmlElementDescriptor(io.pelle.mango.test.client.Entity7VO.class, false, false));
			put("Entity7ReferenceList", new XmlElementDescriptor(io.pelle.mango.test.client.Entity7VO.class, true, true));
			put("Entity7Reference", new XmlElementDescriptor(io.pelle.mango.test.client.Entity7VO.class, false, true));
		}
	};

	@Override
	public Map<String, XmlElementDescriptor> getElements()
	{
		return this.elements;
	}
	
}
