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
package io.pelle.mango.client.base.modules.dictionary.model.controls;

import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.IContentAwareModel;

/**
 * Model for a control element
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public interface IBaseControlModel extends IBaseModel, IContentAwareModel {

	static final String EDITOR_LABEL_MESSAGE_KEY = "dictionaryControlEditorLabel";

	static final int DEFAULT_WIDTH = 32;

	static final int MAX_LENGTH_DEFAULT = DEFAULT_WIDTH;

	String getLabel();

	String getColumnLabel();

	String getEditorLabel();

	String getFilterLabel();

	String getToolTip();

	int getWidth();

	boolean isMandatory();

	boolean isReadonly();

	String getHelpText();

}
