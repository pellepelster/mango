package io.pelle.mango.client.web.modules.dictionary.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.IStateControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IStateControlModel;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

public class StateControl extends BaseDictionaryControl<IStateControlModel, String> implements IStateControl {

	public StateControl(IStateControlModel integerControlModel, BaseDictionaryElement<? extends IBaseModel> parent) {
		super(integerControlModel, parent);
	}

	@Override
	protected BaseDictionaryControl<IStateControlModel, String>.ParseResult parseValueInternal(String valueString) {
		return null;
	}

}
