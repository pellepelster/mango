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

import java.io.Serializable;

/**
 * Representation for an validation error message
 * 
 * @author pelle
 * 
 */
public class Message implements IMessage, Serializable
{

	private static final long serialVersionUID = -8385373403323580526L;

	private SEVERITY severity;

	private String code;

	private String message;

	private String humanMessage;

	public Message()
	{
		super();
	}

	public Message(SEVERITY severity, String code, String message, String humanMessage)
	{
		super();
		this.severity = severity;
		this.code = code;
		this.message = message;
		this.humanMessage = humanMessage;
	}

	/** {@inheritDoc} */
	@Override
	public String getMessage()
	{
		return this.message;
	}

	/** {@inheritDoc} */
	@Override
	public SEVERITY getSeverity()
	{
		return this.severity;
	}

	/** {@inheritDoc} */
	@Override
	public String getCode()
	{
		return this.code;
	}

	@Override
	public String getHumanMessage()
	{
		return this.humanMessage;
	}

}
