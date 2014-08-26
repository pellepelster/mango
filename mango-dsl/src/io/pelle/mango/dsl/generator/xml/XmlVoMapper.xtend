package io.pelle.mango.dsl.generator.xml

import com.google.inject.Inject
import io.pelle.mango.dsl.generator.client.ClientNameUtils
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.Model

class XmlVOMapper  {

	@Inject
	extension XmlNameUtils

	@Inject
	extension ClientNameUtils

	//- entityVOMapperPackageEntity -----------------------------------------------
	def xmlVOMapperEntity(Entity entity) '''
	put("«entity.xsdElementListName»", new XmlElementDescriptor(«entity.voFullQualifiedName».class, true, false));
	put("«entity.xsdElementName»", new XmlElementDescriptor(«entity.voFullQualifiedName».class, false, false));
	put("«entity.xsdElementReferenceListWrapperName»", new XmlElementDescriptor(«entity.voFullQualifiedName».class, true, true));
	put("«entity.xsdElementReferenceName»", new XmlElementDescriptor(«entity.voFullQualifiedName».class, false, true));
	'''

	//- entityVOMapper ------------------------------------------------------------
	def xmlVOMapper(Model model) '''
	package «model.xmlVOMapperPackage»;
	
	import java.util.HashMap;
	import java.util.Map;
	import io.pelle.mango.server.base.xml.XmlElementDescriptor;
	
	@org.springframework.stereotype.Component
	public class «model.xmlVOMapperName» implements io.pelle.mango.server.base.xml.IXmlVOMapper
	{
		
		@java.lang.SuppressWarnings("serial")
		private Map<String, XmlElementDescriptor> elements = new HashMap<String, XmlElementDescriptor>()
		{
			private final long serialVersionUID = 1L;
			{
				«FOR entity : model.eAllContents.toIterable.filter(Entity)»
				«xmlVOMapperEntity(entity)»
				«ENDFOR»
			}
		};
	
		@Override
		public Map<String, XmlElementDescriptor> getElements()
		{
			return this.elements;
		}
		
	}
	'''
}
