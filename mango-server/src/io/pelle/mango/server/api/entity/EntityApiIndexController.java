package io.pelle.mango.server.api.entity;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gwt.thirdparty.guava.common.base.Objects;

import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.base.vo.IEntityDescriptor;
import io.pelle.mango.server.vo.VOMetaDataService;

@Controller
public class EntityApiIndexController {

	public final static String ENTITY_LABEL_TEMPLATE_VARIABLE = "entityLabel";

	public final static String BASE_URL_TEMPLATE_VARIABLE = "baseUrl";

	public final static String INDEX_PATH = "index";

	public final static String INDEX_VIEW_NAME = EntityApiIndexController.class.getPackage().getName().replace(".", "/") + "/templates/EntityApiIndex";

	@Autowired
	private VOMetaDataService voMetaDataService;

	@RequestMapping(value = "api/entity/{entityName}/" + INDEX_PATH, method = RequestMethod.GET)
	public ModelAndView index(@PathVariable String entityName, HttpServletRequest request) {

		Class<? extends IBaseEntity> entityClass = voMetaDataService.getEntityClassForName(entityName);

		IEntityDescriptor<?> entityDescriptor = voMetaDataService.getEntityDescriptor(entityClass);
		
		Map<String, String> templateVariables = new HashMap<String, String>();
		templateVariables.put(ENTITY_LABEL_TEMPLATE_VARIABLE, Objects.firstNonNull(entityDescriptor.getLabel(), entityDescriptor.getName()));
		templateVariables.put(BASE_URL_TEMPLATE_VARIABLE, getDefaultBaseUrl(request));

		return new ModelAndView(INDEX_VIEW_NAME, templateVariables);
	}

	private String getDefaultBaseUrl(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getRequestURI().substring(0, request.getRequestURI().lastIndexOf(INDEX_PATH) - 1);
	}
}