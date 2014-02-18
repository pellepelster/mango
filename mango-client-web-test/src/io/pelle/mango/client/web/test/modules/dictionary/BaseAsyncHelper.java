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
package io.pelle.mango.client.web.test.modules.dictionary;

import io.pelle.mango.client.web.test.MangoAsyncGwtTestHelper.AsyncTestItem;

import java.util.LinkedList;
import java.util.Map;

@SuppressWarnings({ "unchecked" })
public abstract class BaseAsyncHelper<T>
{
	private final LinkedList<AsyncTestItem> asyncTestItems;

	private final Map<String, Object> asyncTestItemResults;

	private final String asyncTestItemResultId;

	public BaseAsyncHelper(String asyncTestItemResultId, LinkedList<AsyncTestItem> asyncTestItems, Map<String, Object> asyncTestItemResults)
	{
		super();
		this.asyncTestItemResultId = asyncTestItemResultId;
		this.asyncTestItems = asyncTestItems;
		this.asyncTestItemResults = asyncTestItemResults;
	}

	protected T getAsyncTestItemResult()
	{
		return (T) this.asyncTestItemResults.get(this.asyncTestItemResultId);
	}

	protected void addAsyncTestItem(AsyncTestItem asyncTestItem)
	{
		this.asyncTestItems.addLast(asyncTestItem);
	}

	protected Map<String, Object> getAsyncTestItemResults()
	{
		return this.asyncTestItemResults;
	}

	protected String getAsyncTestItemResultId()
	{
		return this.asyncTestItemResultId;
	}

	protected LinkedList<AsyncTestItem> getAsyncTestItems()
	{
		return this.asyncTestItems;
	}

}
