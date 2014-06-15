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
package io.pelle.mango.client.gwt.utils;

/**
 * Simple pair class.
 * 
 * @param <A>
 *            any type
 * @param <B>
 *            any type
 */
public class Pair<A, B>
{
	private final A a;
	private final B b;

	public Pair(A a, B b)
	{
		this.a = a;
		this.b = b;
	}

	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof Pair<?, ?>))
		{
			return false;
		}
		Pair<?, ?> other = (Pair<?, ?>) o;
		return a.equals(other.a) && b.equals(other.b);
	}

	public A getA()
	{
		return a;
	}

	public B getB()
	{
		return b;
	}

	@Override
	public int hashCode()
	{
		return a.hashCode() * 13 + b.hashCode() * 7;
	}
}