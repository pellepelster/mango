package io.pelle.mango.client.web.modules.dictionary.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.IGroupControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IControlGroupModel;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

public class GroupControl extends BaseDictionaryControl<IControlGroupModel, String> implements IGroupControl {

	public GroupControl(IControlGroupModel controlGroupModel, BaseDictionaryElement<? extends IBaseModel> parent) {
		super(controlGroupModel, parent);
	}

	@Override
	protected ParseResult parseValueInternal(String valueString) {
		return new ParseResult();
	}

}
