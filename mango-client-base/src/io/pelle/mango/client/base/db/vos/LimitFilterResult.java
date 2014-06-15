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

import io.pelle.mango.client.base.vo.IBaseVO;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a limited filter result and the total number of results it would
 * have returned without the limit
 * 
 * @author pelle
 * @version $Rev: 85 $, $Date: 2009-06-11 23:36:35 +0200 (Thu, 11 Jun 2009) $
 * 
 */
public class LimitFilterResult implements Serializable
{

	private static final long serialVersionUID = 8435872490339699182L;
	private List<? extends IBaseVO> result;
	private int totalResults;

	public LimitFilterResult()
	{
		super();
	}

	public LimitFilterResult(List<? extends IBaseVO> result, int totalResults)
	{
		super();
		this.result = result;
		this.totalResults = totalResults;
	}

	public List<? extends IBaseVO> getResult()
	{
		return this.result;
	}

	public int getTotalResults()
	{
		return this.totalResults;
	}

}
