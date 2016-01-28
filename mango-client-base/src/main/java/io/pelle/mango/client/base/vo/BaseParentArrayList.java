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
package io.pelle.mango.client.base.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public abstract class BaseParentArrayList<ListType> extends ChangeTrackingArrayList<ListType> implements Serializable
{

	private ArrayList<ListType> list = new ArrayList<ListType>();
	private static final long serialVersionUID = 8433392269045432804L;
	private String listItemParentVOAttributeName;
	private ListType parent;

	public BaseParentArrayList()
	{
	}

	public BaseParentArrayList(ListType parent, String listItemParentVOAttributeName)
	{
		super();
		this.parent = parent;
		this.listItemParentVOAttributeName = listItemParentVOAttributeName;
	}

	@Override
	public void add(int index, ListType element)
	{
		this.list.add(index, element);
	}

	/** {@inheritDoc} */
	@Override
	public boolean addAll(Collection<? extends ListType> c)
	{
		return this.list.addAll(c);
	}

	/** {@inheritDoc} */
	@Override
	public boolean addAll(int index, Collection<? extends ListType> c)
	{
		return this.list.addAll(index, c);
	}

	/** {@inheritDoc} */
	@Override
	public void clear()
	{
		this.list.clear();
	}

	/** {@inheritDoc} */
	@Override
	public boolean contains(Object o)
	{
		return this.list.contains(o);
	}

	/** {@inheritDoc} */
	@Override
	public boolean containsAll(Collection<?> c)
	{
		return this.list.containsAll(c);
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object o)
	{
		return this.list.equals(o);
	}

	/** {@inheritDoc} */
	@Override
	public ListType get(int index)
	{
		return this.list.get(index);
	}

	public ArrayList<ListType> getList()
	{
		return this.list;
	}

	public String getListItemParentVOAttributeName()
	{
		return this.listItemParentVOAttributeName;
	}

	public ListType getParent()
	{
		return this.parent;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		return this.list.hashCode();
	}

	/** {@inheritDoc} */
	@Override
	public int indexOf(Object o)
	{
		return this.list.indexOf(o);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isEmpty()
	{
		return this.list.isEmpty();
	}

	/** {@inheritDoc} */
	@Override
	public Iterator<ListType> iterator()
	{
		return this.list.iterator();
	}

	/** {@inheritDoc} */
	@Override
	public int lastIndexOf(Object o)
	{
		return this.list.lastIndexOf(o);
	}

	/** {@inheritDoc} */
	@Override
	public ListIterator<ListType> listIterator()
	{
		return this.list.listIterator();
	}

	/** {@inheritDoc} */
	@Override
	public ListIterator<ListType> listIterator(int index)
	{
		return this.list.listIterator(index);
	}

	/** {@inheritDoc} */
	@Override
	public ListType remove(int index)
	{
		return this.list.remove(index);
	}

	/** {@inheritDoc} */
	@Override
	public boolean removeAll(Collection<?> c)
	{
		return this.list.removeAll(c);
	}

	/** {@inheritDoc} */
	@Override
	public boolean retainAll(Collection<?> c)
	{
		return this.list.retainAll(c);
	}

	/** {@inheritDoc} */
	@Override
	public ListType set(int index, ListType element)
	{
		return this.list.set(index, element);
	}

	public void setList(ArrayList<ListType> list)
	{
		this.list = list;
	}

	public void setListItemParentVOAttributeName(String listItemParentVOAttributeName)
	{
		this.listItemParentVOAttributeName = listItemParentVOAttributeName;
	}

	public void setParentVO(ListType parent)
	{
		this.parent = parent;
	}

	/** {@inheritDoc} */
	@Override
	public int size()
	{
		return this.list.size();
	}

	/** {@inheritDoc} */
	@Override
	public List<ListType> subList(int fromIndex, int toIndex)
	{
		return this.list.subList(fromIndex, toIndex);
	}

	/** {@inheritDoc} */
	@Override
	public Object[] toArray()
	{
		return this.list.toArray();
	}

	/** {@inheritDoc} */
	@Override
	public <ArrayType> ArrayType[] toArray(ArrayType[] a)
	{
		return this.list.toArray(a);
	}

	/** {@inheritDoc} */
	@Override
	public String toString()
	{
		return this.list.toString();
	}

}
