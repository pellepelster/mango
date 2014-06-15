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
import io.pelle.mango.client.base.vo.IBaseVO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Result<VOType extends IBaseVO> implements Serializable
{
	private static final long serialVersionUID = 2971295762387189829L;
	private VOType vo;
	private List<IValidationMessage> validationMessages = new ArrayList<IValidationMessage>();

	public Result()
	{
	}

	public List<IValidationMessage> getValidationMessages()
	{
		return validationMessages;
	}

	public VOType getVO()
	{
		return vo;
	}

	public void setValidationMessages(List<IValidationMessage> validationMessages)
	{
		this.validationMessages = validationMessages;
	}

	public void setVO(VOType vo)
	{
		this.vo = vo;
	}

}
