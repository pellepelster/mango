package io.pelle.mango.server.util;

import java.util.ArrayList;
import java.util.List;

import io.pelle.mango.server.util.IDocumentationBean.NavigationItem;

public class NavigationModel {

	private List<NavigationItem> primaryNavigation = new ArrayList<NavigationItem>();

	private String title;

	private final String applicationName;

	public NavigationModel(String applicationName, String title) {
		super();
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

}