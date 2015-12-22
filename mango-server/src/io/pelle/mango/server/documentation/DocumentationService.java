package io.pelle.mango.server.documentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class DocumentationService {

	@Autowired
	private List<IDocumentationContributor> documentationBeans = new ArrayList<IDocumentationContributor>();

	public Map<String, Object> getDefaultTemplateModel(BreadCrumb... breadCrumbs) {

		NavigationModel navigationModel = new NavigationModel(IDocumentationContributor.DOCUMENTATION_BASE_PATH, "xx", "yyy");

		navigationModel.getBreadCrumbs().add(new BreadCrumb(IDocumentationContributor.INDEX_PATH, "Home"));
		navigationModel.getBreadCrumbs().addAll(Arrays.asList(breadCrumbs));

		for (IDocumentationContributor documentationBean : documentationBeans) {
			navigationModel.getPrimaryNavigation().addAll(documentationBean.getPrimaryNavigation());
		}

		Map<String, Object> templateModel = new HashMap<String, Object>();
		templateModel.put(IDocumentationContributor.NAVIGATION_MODEL_TEMPLATE_VARIABLE, navigationModel);

		return templateModel;
	}

}
