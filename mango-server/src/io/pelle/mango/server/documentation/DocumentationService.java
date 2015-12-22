package io.pelle.mango.server.documentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class DocumentationService {

	@Autowired
	private List<IDocumentationBean> documentationBeans = new ArrayList<IDocumentationBean>();
	
	
	public Map<String, Object> getDefaultTemplateModel(BreadCrumb... breadCrumbs) {

		NavigationModel navigationModel = new NavigationModel(IDocumentationBean.DOCUMENTATION_BASE_PATH, "xx", "yyy");

		navigationModel.getBreadCrumbs().add(new BreadCrumb("index", "Home"));
		navigationModel.getBreadCrumbs().addAll(Arrays.asList(breadCrumbs));
		
		for(IDocumentationBean documentationBean: documentationBeans) {
			navigationModel.getPrimaryNavigation().addAll(documentationBean.getPrimaryNavigation());
		}
		
		Map<String, Object> templateModel = new HashMap<String, Object>();
		templateModel.put(IDocumentationBean.NAVIGATION_MODEL_TEMPLATE_VARIABLE, navigationModel);
		
		
		return templateModel;
	}

}
