package io.pelle.mango.server.base.query;

import java.util.Collection;
import java.util.Iterator;

public class BaseCollectionQuery<T> implements Iterable<T>
{
	private Collection<T> collection;

	public BaseCollectionQuery(Collection<T> eObjects)
	{
		this.collection = eObjects;
	}
	

	public int getCount() {
		return collection.size();
	}

	public Collection<T> getCollection()
	{
		return collection;
	}
	
	public boolean hasExactlyOne()
	{
		return collection.size() == 1;
	}

	public T getSingleResult()
	{
		if (!hasExactlyOne())
		{
			throw new RuntimeException(String.format("found %d but expected expected one", collection.size()));
		}

		return collection.iterator().next();
	}

	@Override
	public Iterator<T> iterator() {
		return collection.iterator();
	}

}
