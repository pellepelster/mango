package io.pelle.mango.client.web.modules.dictionary.container;

import io.pelle.mango.client.base.modules.dictionary.container.IBaseTable;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IBaseTableModel;
import io.pelle.mango.client.base.vo.IBaseVO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TableRowList<VOType extends IBaseVO, ModelType extends IBaseTableModel> extends ArrayList<IBaseTable.ITableRow<VOType>>
{
	private final List<VOType> wrappedVOList;

	private static final long serialVersionUID = -8145783594300424016L;

	public TableRowList(List<VOType> wrappedVOList, BaseTableElement<VOType, ModelType> tableRowParent)
	{
		super();
		this.wrappedVOList = wrappedVOList;

		for (VOType vo : wrappedVOList)
		{
			super.add(new TableRow<VOType, ModelType>(vo, tableRowParent));
		}

	}

	@Override
	public IBaseTable.ITableRow<VOType> set(int index, IBaseTable.ITableRow<VOType> element)
	{
		this.wrappedVOList.set(index, element.getVO());
		return super.set(index, element);
	}

	@Override
	public boolean add(IBaseTable.ITableRow<VOType> element)
	{
		this.wrappedVOList.add(element.getVO());
		return super.add(element);
	}

	@Override
	public IBaseTable.ITableRow<VOType> remove(int index)
	{
		this.wrappedVOList.remove(index);
		return super.remove(index);
	}

	@Override
	public boolean remove(Object element)
	{
		if (element instanceof IBaseTable.ITableRow)
		{
			IBaseTable.ITableRow tableRow = (IBaseTable.ITableRow) element;
			this.wrappedVOList.remove(tableRow.getVO());
		}

		return super.remove(element);
	}

	@Override
	public void clear()
	{
		this.wrappedVOList.clear();
		super.clear();
	}

	@Override
	public boolean addAll(Collection<? extends IBaseTable.ITableRow<VOType>> c)
	{
		throw new RuntimeException("not implemented");
	}

	@Override
	public boolean addAll(int index, Collection<? extends IBaseTable.ITableRow<VOType>> c)
	{
		throw new RuntimeException("not implemented");
	}

	@Override
	public boolean removeAll(Collection<?> elements)
	{
		List<VOType> vosToRemove = new ArrayList<VOType>();
		for (Object element : elements)
		{
			if (element instanceof IBaseTable.ITableRow)
			{
				IBaseTable.ITableRow<VOType> tableRow = (IBaseTable.ITableRow) element;
				vosToRemove.add(tableRow.getVO());
			}
		}

		this.wrappedVOList.removeAll(vosToRemove);

		return super.removeAll(elements);
	}

	@Override
	public boolean retainAll(Collection<?> c)
	{
		throw new RuntimeException("not implemented");
	}

}
