package io.pelle.mango.client.web.test.sync.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.IReferenceControl;
import io.pelle.mango.client.base.modules.dictionary.controls.IReferenceControl.Suggestion;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

public class ReferenceTestControl<VOType extends IBaseVO> extends BaseControlTest<IReferenceControl<VOType>, VOType> {

	private List<Suggestion<VOType>> suggestions = new ArrayList<IReferenceControl.Suggestion<VOType>>();

	public ReferenceTestControl(IReferenceControl<VOType> control) {
		super(control);
	}

	public void parseValue(String valueString) {
		beginEdit();
		getBaseControl().parseValue(valueString, new BaseErrorAsyncCallback<List<Suggestion<VOType>>>() {

			@Override
			public void onSuccess(List<Suggestion<VOType>> result) {
				suggestions.clear();
				suggestions.addAll(result);
			}
		});
	}

	public void assertHasSuggestions(int count) {
		Assert.assertEquals(count, suggestions.size());
	}
}
