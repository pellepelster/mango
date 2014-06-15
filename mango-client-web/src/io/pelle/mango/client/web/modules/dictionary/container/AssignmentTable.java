package io.pelle.mango.client.web.modules.dictionary.container;

import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IAssignmentTableModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

public class AssignmentTable<VOType extends IBaseVO> extends BaseTableElement<VOType, IAssignmentTableModel>
{

	public AssignmentTable(IAssignmentTableModel assignmentTableModel, BaseDictionaryElement<IBaseModel> parent)
	{
		super(assignmentTableModel, parent);
	}

}
