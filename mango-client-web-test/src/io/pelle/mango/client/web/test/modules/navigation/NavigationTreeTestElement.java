package io.pelle.mango.client.web.test.modules.navigation;

import io.pelle.mango.client.base.modules.navigation.NavigationTreeElement;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

public class NavigationTreeTestElement {
	private NavigationTreeElement navigationTreeElement;

	private List<NavigationTreeTestElement> children;

	public NavigationTreeTestElement(NavigationTreeElement navigationTreeElement) {
		super();
		this.navigationTreeElement = navigationTreeElement;

		this.children = new ArrayList(Collections2.transform(navigationTreeElement.getChildren(), new Function<NavigationTreeElement, NavigationTreeTestElement>() {

			@Override
			public NavigationTreeTestElement apply(NavigationTreeElement input) {
				return new NavigationTreeTestElement(input);
			}
		}));
	}

	public void assertNavigationText(String expected) {
		Assert.assertEquals(expected, this.navigationTreeElement.getLabel());
	}

	public void assertChildrenCount(int count) {
		Assert.assertEquals(count, this.children.size());
	}

	public void assertChildNavigationText(int index, String expected) {
		this.children.get(index).assertNavigationText(expected);

	}
}
