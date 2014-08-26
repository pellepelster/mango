package io.pelle.mango.client.web.modules.dictionary.container;

import io.pelle.mango.client.base.modules.dictionary.container.IBaseTable;
import io.pelle.mango.client.base.modules.dictionary.controls.IBaseControl;
import io.pelle.mango.client.base.modules.dictionary.model.BaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IBaseTableModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.modules.dictionary.DictionaryElementUtil;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;
import io.pelle.mango.client.web.modules.dictionary.controls.BaseDictionaryControl;
import io.pelle.mango.client.web.modules.dictionary.controls.ControlFactory;
import io.pelle.mango.client.web.modules.dictionary.editor.EditorVOWrapper;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

public class TableRow<VOType extends IBaseVO, ModelType extends IBaseTableModel> extends BaseDictionaryElement<ModelType> implements
		IBaseTable.ITableRow<VOType>
{
	private List<BaseDictionaryControl<?, ?>> columns = new ArrayList<BaseDictionaryControl<?, ?>>();

	private final EditorVOWrapper<VOType> voWrapper;

	@SuppressWarnings("static-access")
	public TableRow(VOType vo, BaseTableElement<VOType, ModelType> parent)
	{
		super(parent.getModel(), parent);

		for (IBaseControlModel baseControlModel : parent.getModel().getControls())
		{
			this.columns.add(ControlFactory.getInstance().createControl(baseControlModel, this));
		}

		this.voWrapper = new EditorVOWrapper<VOType>(vo);
	}

	@Override
	public EditorVOWrapper<VOType> getVOWrapper()
	{
		return this.voWrapper;
	}

	@Override
	public VOType getVO()
	{
		return this.getVOWrapper().getVO();
	}

	public List<BaseDictionaryControl<?, ?>> getColumns()
	{
		return this.columns;
	}

	// TODO clean up his mess

	@SuppressWarnings("unchecked")
	@Override
	public <ElementType extends IBaseControl> ElementType getElement(BaseModel<ElementType> baseModel)
	{
		List<String> parentModelIds = DictionaryElementUtil.getParentModelIds(getParent().getModel());
		List<String> controlModelIds = DictionaryElementUtil.getParentModelIds(baseModel);

		DictionaryElementUtil.removeLeadingModelIds(parentModelIds, controlModelIds);

		return (ElementType) DictionaryElementUtil.getControl(this, controlModelIds);
	}

	@Override
	public <ElementType extends IBaseControl> ElementType getElement(IBaseControlModel baseControlModel)
	{
		return (ElementType) DictionaryElementUtil.getControl(this, Lists.newArrayList(baseControlModel.getName()));
	}

	@Override
	public List<? extends BaseDictionaryElement<?>> getAllChildren()
	{
		return this.columns;
	}

}