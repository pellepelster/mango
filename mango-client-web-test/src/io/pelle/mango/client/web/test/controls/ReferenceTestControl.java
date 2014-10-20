package io.pelle.mango.client.web.test.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.IReferenceControl;
import io.pelle.mango.client.base.modules.dictionary.controls.IReferenceControl.Suggestion;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.test.util.FocusManager;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

public class ReferenceTestControl<VOType extends IBaseVO> extends BaseTestControl<IReferenceControl<VOType>, VOType> {

	private List<Suggestion<VOType>> suggestions = new ArrayList<IReferenceControl.Suggestion<VOType>>();

	public ReferenceTestControl(IReferenceControl<VOType> control) {
		super(control);
	}

	public void enterValue(String valueString) {
		FocusManager.getInstance().setCurrentWidget(this);

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

	public void leaveControl() {
		FocusManager.getInstance().leaveWidget(this);
	}
}
