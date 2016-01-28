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
package io.pelle.mango.client.base.messages;

import java.util.Map;

/**
 * Represents a validation result
 * 
 * @author pelle
 * 
 */
public interface IValidationMessage extends IMessage {

	final static String DICTIONARY_EDITOR_LABEL_CONTEXT_KEY = "dictionaryEditorLabel";

	final static String NATURAL_KEY_CONTEXT_KEY = "naturalKey";

	final static String ATTRIBUTE_CONTEXT_KEY = "attribute";

	final static String VOCLASS_CONTEXT_KEY = "voClass";

	final static String VALUE_CONTEXT_KEY = "value";

	Map<String, Object> getContext();
}
