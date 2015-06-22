package io.pelle.mango.client.base.vo;

import java.util.ArrayList;
import java.util.Collection;

public class ChangeTrackingArrayList<T> extends ArrayList<T> implements IVOEntityMetadata {

	private boolean hasChanged = false;

	private static final long serialVersionUID = 1394709596650118025L;

	public ChangeTrackingArrayList() {
		super();
	}

	public ChangeTrackingArrayList(Collection<? extends T> c) {
		super(c);
	}

	public ChangeTrackingArrayList(int initialCapacity) {
		super(initialCapacity);
	}

	@Override
	public boolean add(T e) {
		this.hasChanged = true;
		return super.add(e);
	}

	@Override
	public void add(int index, T element) {
		this.hasChanged = true;
		super.add(index, element);
	}

	@Override
	public T remove(int index) {
		this.hasChanged = true;
		return super.remove(index);
	}

	@Override
	public boolean remove(Object o) {
		this.hasChanged = true;
		return super.remove(o);
	}

	@Override
	public void clear() {
		this.hasChanged = true;
		super.clear();
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		this.hasChanged = true;
		return super.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		this.hasChanged = true;
		return super.addAll(index, c);
	}

	@Override
	protected void removeRange(int fromIndex, int toIndex) {
		this.hasChanged = true;
		super.removeRange(fromIndex, toIndex);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		this.hasChanged = true;
		return super.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		this.hasChanged = true;
		return super.retainAll(c);
	}

	@Override
	public boolean hasChanges() {
		return this.hasChanged;
	}

	@Override
	public void clearChanges() {
		this.hasChanged = false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void copyChanges(IVOEntityMetadata source) {
		assert source instanceof IVOEntityMetadata;
		this.hasChanged = ((ChangeTrackingArrayList) source).hasChanged;
	}

	@Override
	public void setLoaded(String attributeName) {
		throw new RuntimeException("not implemented");

	}

	@Override
	public void disableLoadChecking() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public boolean isLoaded(String attributeName) {
		throw new RuntimeException("not implemented");
	}

}