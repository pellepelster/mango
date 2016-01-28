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

/**
 * Represents system message
 * 
 * @author pelle
 * 
 */
public interface IMessage
{
	enum SEVERITY
	{
		ERROR(3), WARNING(2), INFO(1), NONE(0);

		private int order;

		private SEVERITY(int order)
		{
			this.order = order;
		}

		public int getOrder()
		{
			return order;
		}
	}

	/**
	 * Returns the validation code text for this message
	 * 
	 * @return
	 */
	String getCode();

	/**
	 * Returns the validation status text for this message
	 * 
	 * @return
	 */
	String getMessage();

	/**
	 * Returns a human readable error message
	 * 
	 * @return
	 */
	String getHumanMessage();

	/**
	 * Returns the severity status for this message
	 * 
	 * @return
	 */
	SEVERITY getSeverity();

}
