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
package io.pelle.mango.client.gwt.modules.dictionary;

import io.pelle.mango.client.base.layout.LAYOUT_TYPE;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.ColumnLayoutStrategy;
import io.pelle.mango.client.web.modules.dictionary.filter.DictionaryFilter;

import org.gwtbootstrap3.client.ui.html.Div;

/**
 * Generic dictionary model based implementation of {@link IDictionaryFilterUI}
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 * @param <VOType>
 */
public class DictionaryFilterPanel<VOType extends IBaseVO> extends Div {

	private final ColumnLayoutStrategy layoutStrategy = new ColumnLayoutStrategy(LAYOUT_TYPE.FILTER);

	/**
	 * Constructor for {@link DictionaryFilterPanel}
	 * 
	 * @param filterModel
	 *            Model describing the filter
	 */
	public DictionaryFilterPanel(DictionaryFilter<VOType> dictionaryFilter, Class<VOType> voClass) {
		layoutStrategy.createLayout(this, dictionaryFilter.getRootComposite());
	}

	public void clearFilter() {
	}

}
