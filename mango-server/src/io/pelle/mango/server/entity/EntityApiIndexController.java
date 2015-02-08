package io.pelle.mango.server.entity;

import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.server.vo.VOMetaDataService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EntityApiIndexController {

	public final static String ENTITY_LABEL_TEMPLATE_VARIABLE = "entityLabel";

	@Autowired
	private VOMetaDataService voMetaDataService;

	@RequestMapping(value = "entity/{entityName}/api/index", method = RequestMethod.GET)
	public ModelAndView index(@PathVariable String entityName) {

		Class<? extends IBaseEntity> entityClass = voMetaDataService.getEntityClassForName(entityName);

		Map<String, String> templateVariables = new HashMap<String, String>();
		templateVariables.put(ENTITY_LABEL_TEMPLATE_VARIABLE, voMetaDataService.getLabel(entityClass));

		return new ModelAndView("EntityApiIndex", templateVariables);
	}
}