
package io.pelle.mango.server;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

@org.springframework.web.bind.annotation.RestController
@org.springframework.web.bind.annotation.RequestMapping("hierachicalservice")
public class HierachicalServiceRestController  {

	@org.springframework.beans.factory.annotation.Autowired
	private io.pelle.mango.client.IHierachicalService hierachicalService;
	
	public void setHierachicalService(io.pelle.mango.client.IHierachicalService hierachicalService)
	{
		this.hierachicalService = hierachicalService;
	}

	@RequestMapping(value = "getrootnodes/{id}")
	public  java.util.List<io.pelle.mango.client.DictionaryHierarchicalNodeVO>
	 getRootNodes(@RequestParam java.lang.String id) {
		return this.hierachicalService.getRootNodes(id);
	}
	@RequestMapping(value = "getconfigurationbyid/{id}")
	public  io.pelle.mango.client.base.modules.hierarchical.HierarchicalConfigurationVO
	 getConfigurationById(@RequestParam java.lang.String id) {
		return this.hierachicalService.getConfigurationById(id);
	}
	@RequestMapping(value = "getconfigurations/")
	public  java.util.List<io.pelle.mango.client.base.modules.hierarchical.HierarchicalConfigurationVO>
	 getConfigurations() {
		return this.hierachicalService.getConfigurations();
	}
	@RequestMapping(value = "haschildren/{voClassName}/{voId}")
	public  Boolean
	 hasChildren(@RequestParam java.lang.String voClassName, @RequestParam java.lang.Long voId) {
		return this.hierachicalService.hasChildren(voClassName,voId);
	}
	@RequestMapping(value = "getchildnodes/{id}/{voId}/{voClassName}")
	public  java.util.List<io.pelle.mango.client.DictionaryHierarchicalNodeVO>
	 getChildNodes(@RequestParam java.lang.String id, @RequestParam java.lang.Long voId, @RequestParam java.lang.String voClassName) {
		return this.hierachicalService.getChildNodes(id,voId,voClassName);
	}
}
