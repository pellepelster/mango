package io.pelle.mango.server.documentation;

import java.util.List;

public interface IDocumentationBean {
	
	public final static String NAVIGATION_MODEL_TEMPLATE_VARIABLE = "navigationModel";

	public final static String DOCUMENTATION_BASE_PATH = "documentation";

	public final static String INDEX_PATH = "index";

	public class NavigationItem {

		private final String id;

		private final String name;

		private String path;
		
		public NavigationItem(String id, String name, String path) {
			super();
			this.id = id;
			this.name = name;
			this.path = path;
		}

		public String getId() {
			return id;
		}

		public String getName() {
			return name;
		}
		
		public String getPath() {
			return path;
		}

	}
	
	List<NavigationItem> getPrimaryNavigation();
	
}
