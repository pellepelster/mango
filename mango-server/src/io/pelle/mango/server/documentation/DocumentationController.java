package io.pelle.mango.server.documentation;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(IDocumentationContributor.DOCUMENTATION_BASE_PATH)
public class DocumentationController {

	@Autowired
	private DocumentationService documentationService;

	public final static String INDEX_VIEW_NAME = DocumentationController.class.getPackage().getName().replace(".", "/") + "/templates/DocumentationIndex";

	@RequestMapping()
	public String catchAll() {
		 return "redirect:" + IDocumentationContributor.DOCUMENTATION_BASE_PATH + "/" + IDocumentationContributor.INDEX_PATH;
	}


	@RequestMapping(value = IDocumentationContributor.INDEX_PATH)
	public ModelAndView index() {
		return view(INDEX_VIEW_NAME);
	}

	
	public ModelAndView view(String viewName) {

		Map<String, Object> templateModel = documentationService.getDefaultTemplateModel();

		return new ModelAndView(viewName, templateModel);
		
	}

}
