package io.pelle.mango.client.base.modules.dictionary.controls;

import io.pelle.mango.client.base.modules.dictionary.model.controls.IEnumerationControlModel;

import java.util.Map;

public interface IEnumerationControl<ENUM_TYPE> extends IBaseControl<ENUM_TYPE, IEnumerationControlModel<ENUM_TYPE>> {

	Map<String, String> getEnumerationMap();

}
