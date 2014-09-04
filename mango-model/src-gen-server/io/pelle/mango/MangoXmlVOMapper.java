package io.pelle.mango;

import java.util.HashMap;
import java.util.Map;
import io.pelle.mango.server.base.xml.XmlElementDescriptor;

@org.springframework.stereotype.Component
public class MangoXmlVOMapper implements io.pelle.mango.server.base.xml.IXmlVOMapper
{
	
	@java.lang.SuppressWarnings("serial")
	private Map<String, XmlElementDescriptor> elements = new HashMap<String, XmlElementDescriptor>()
	{
		private final long serialVersionUID = 1L;
		{
			put("DictionaryLabelSearchIndexList", new XmlElementDescriptor(io.pelle.mango.client.DictionaryLabelSearchIndexVO.class, true, false));
			put("DictionaryLabelSearchIndex", new XmlElementDescriptor(io.pelle.mango.client.DictionaryLabelSearchIndexVO.class, false, false));
			put("DictionaryLabelSearchIndexReferenceList", new XmlElementDescriptor(io.pelle.mango.client.DictionaryLabelSearchIndexVO.class, true, true));
			put("DictionaryLabelSearchIndexReference", new XmlElementDescriptor(io.pelle.mango.client.DictionaryLabelSearchIndexVO.class, false, true));
			put("DictionaryHierarchicalNodeList", new XmlElementDescriptor(io.pelle.mango.client.DictionaryHierarchicalNodeVO.class, true, false));
			put("DictionaryHierarchicalNode", new XmlElementDescriptor(io.pelle.mango.client.DictionaryHierarchicalNodeVO.class, false, false));
			put("DictionaryHierarchicalNodeReferenceList", new XmlElementDescriptor(io.pelle.mango.client.DictionaryHierarchicalNodeVO.class, true, true));
			put("DictionaryHierarchicalNodeReference", new XmlElementDescriptor(io.pelle.mango.client.DictionaryHierarchicalNodeVO.class, false, true));
			put("FileList", new XmlElementDescriptor(io.pelle.mango.client.FileVO.class, true, false));
			put("File", new XmlElementDescriptor(io.pelle.mango.client.FileVO.class, false, false));
			put("FileReferenceList", new XmlElementDescriptor(io.pelle.mango.client.FileVO.class, true, true));
			put("FileReference", new XmlElementDescriptor(io.pelle.mango.client.FileVO.class, false, true));
		}
	};

	@Override
	public Map<String, XmlElementDescriptor> getElements()
	{
		return this.elements;
	}
	
}
