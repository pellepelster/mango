package io.pelle.mango.server.documentation;

import java.util.List;

public interface IDocumentationContributor {

	public final static String NAVIGATION_MODEL_TEMPLATE_VARIABLE = "navigationModel";

	public final static String DOCUMENTATION_BASE_PATH = "documentation";

	public final static String INDEX_PATH = "index";

	public final static String DOCUMENTATION_RESTAPI_NAME_MESSAGE_KEY = "documentation.restapi.name";

	public final static String DOCUMENTATION_INDEX_TITLE_MESSAGE_KEY = "documentation.index.title";

	List<NavigationItem> getPrimaryNavigation();

}
