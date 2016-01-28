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
package io.pelle.mango.client.web.modules.dictionary.databinding;

import io.pelle.mango.client.base.messages.IValidationMessage;

import java.util.List;

/**
 * Utilities for validator handling
 * 
 * @author pelle
 * 
 */
public final class ValidationUtils {

	private ValidationUtils() {
	}

	public static String getAttributeContext(IValidationMessage validationMessage) {
		return String.valueOf(validationMessage.getContext().get(IValidationMessage.ATTRIBUTE_CONTEXT_KEY));
	}

	public static String getErrorMessage(List<IValidationMessage> validationMessages) {
		StringBuilder sb = new StringBuilder();

		for (IValidationMessage validationMessage : validationMessages) {

			if (sb.length() > 0) {
				sb.append("; ");
			}
			sb.append(validationMessage.getMessage());
		}

		return sb.toString();
	}

}
