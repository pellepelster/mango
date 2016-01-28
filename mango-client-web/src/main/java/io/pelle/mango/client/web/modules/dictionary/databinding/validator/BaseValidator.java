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
package io.pelle.mango.client.web.modules.dictionary.databinding.validator;

import io.pelle.mango.client.base.messages.IValidationMessage;
import io.pelle.mango.client.web.modules.dictionary.databinding.IValidator;

import java.util.Arrays;
import java.util.List;

public abstract class BaseValidator implements IValidator
{

	protected RuntimeException getValidationException(Object value)
	{
		String valueString = "null";

		if (value != null)
		{
			valueString = value.getClass().getName();
		}

		return new RuntimeException("unable to validate '" + valueString + "'");

	}

	protected List<IValidationMessage> resultListHelper(IValidationMessage... validationMessages)
	{
		return Arrays.asList(validationMessages);
	}

}
