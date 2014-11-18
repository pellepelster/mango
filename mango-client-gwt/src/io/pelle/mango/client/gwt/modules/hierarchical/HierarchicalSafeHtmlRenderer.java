/**
 * Copyright (c) 2013 Christian Pelster.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Christian Pelster - initial API and implementation
 */
package io.pelle.mango.client.gwt.modules.hierarchical;

import io.pelle.mango.client.hierarchy.DictionaryHierarchicalNodeVO;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.text.shared.SafeHtmlRenderer;

public class HierarchicalSafeHtmlRenderer implements SafeHtmlRenderer<DictionaryHierarchicalNodeVO> {

	private static HierarchicalSafeHtmlRenderer instance;

	public static HierarchicalSafeHtmlRenderer getInstance() {
		if (instance == null) {
			instance = new HierarchicalSafeHtmlRenderer();
		}

		return instance;
	}

	private HierarchicalSafeHtmlRenderer() {
	}

	@Override
	public SafeHtml render(DictionaryHierarchicalNodeVO object) {
		return SafeHtmlUtils.fromString(object.getLabel());
	}

	@Override
	public void render(DictionaryHierarchicalNodeVO object, SafeHtmlBuilder builder) {
		builder.append(SafeHtmlUtils.fromString(object.getLabel()));
	}

}
