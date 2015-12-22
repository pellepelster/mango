package io.pelle.mango.server.documentation;

import java.util.ArrayList;
import java.util.List;

import io.pelle.mango.server.documentation.IDocumentationBean.NavigationItem;

public class NavigationModel {

	private List<NavigationItem> primaryNavigation = new ArrayList<NavigationItem>();

	private List<BreadCrumb> breadCrumbs = new ArrayList<BreadCrumb>();

	private String title;

	private final String applicationName;

	private String baseUrl;

	public NavigationModel(String baseUrl, String applicationName, String title) {
		super();
		this.baseUrl = baseUrl;
		this.applicationName = applicationName;
		this.title = title;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public String getTitle() {
		return title;
	}

	public List<NavigationItem> getPrimaryNavigation() {
		return primaryNavigation;
	}
	
	public String getBaseUrl() {
		return baseUrl;
	}

	public List<BreadCrumb> getBreadCrumbs() {
		return breadCrumbs;
	}
}