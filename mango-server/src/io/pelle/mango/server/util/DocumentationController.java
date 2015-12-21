package io.pelle.mango.server.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(IDocumentationBean.DOCUMENTATION_BASE_PATH)
public class DocumentationController {

	@Autowired
	private List<IDocumentationBean> documentationBeans = new ArrayList<IDocumentationBean>();
	
	public final static String INDEX_VIEW_NAME = "DocumentationIndex";


	@RequestMapping(value = "/")
	public ModelAndView index() {
		return view(INDEX_VIEW_NAME);
	}

	public ModelAndView view(String viewName) {

		NavigationModel navigationModel = new NavigationModel("xx", "yyy");
		
		for(IDocumentationBean documentationBean: documentationBeans) {
			navigationModel.getPrimaryNavigation().addAll(documentationBean.getPrimaryNavigation());
		}

		Map<String, Object> templateModel = new HashMap<String, Object>();
		templateModel.put(IDocumentationBean.NAVIGATION_MODEL_TEMPLATE_VARIABLE, navigationModel);

		return new ModelAndView(viewName, templateModel);
		
	}

}
