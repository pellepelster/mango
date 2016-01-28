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
package io.pelle.mango.client.base.db.vos;

import io.pelle.mango.client.base.messages.IValidationMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Result<Type extends Serializable> implements Serializable
{
	private Type value;
	
	private List<IValidationMessage> validationMessages = new ArrayList<IValidationMessage>();

	public Result()
	{
	}

	public List<IValidationMessage> getValidationMessages()
	{
		return validationMessages;
	}

	public Type getValue()
	{
		return value;
	}

	public void setValidationMessages(List<IValidationMessage> validationMessages)
	{
		this.validationMessages = validationMessages;
	}

	public void setValue(Type value)
	{
		this.value = value;
	}

	public boolean isOk() {
		return validationMessages.isEmpty();
	}

}
