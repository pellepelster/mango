package io.pelle.mango.server.documentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import io.pelle.mango.server.Messages;
import io.pelle.mango.server.base.MangoConstants;

public class DocumentationService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private List<IDocumentationContributor> documentationBeans = new ArrayList<IDocumentationContributor>();

	public Map<String, Object> getDefaultTemplateModel(BreadCrumb... breadCrumbs) {

		String applicationName = environment.getProperty(MangoConstants.APPLICATION_NAME_PROPERTY_KEY);
		String title = Messages.getString(IDocumentationContributor.DOCUMENTATION_INDEX_TITLE_MESSAGE_KEY, applicationName);
				
		NavigationModel navigationModel = new NavigationModel(IDocumentationContributor.DOCUMENTATION_BASE_PATH, applicationName, title);

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
