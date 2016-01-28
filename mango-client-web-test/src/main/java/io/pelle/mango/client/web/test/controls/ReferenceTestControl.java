package io.pelle.mango.client.web.test.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.IReferenceControl;
import io.pelle.mango.client.base.modules.dictionary.controls.IReferenceControl.Suggestion;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.test.AsyncCallbackFuture;
import io.pelle.mango.client.web.test.util.FocusManager;

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

		AsyncCallbackFuture<List<Suggestion<VOType>>> future = AsyncCallbackFuture.create();
		
		getBaseControl().parseValue(valueString, future);
		
		suggestions.clear();
		suggestions.addAll(future.get());
	}

	public void assertHasSuggestions(int count) {
		Assert.assertEquals(count, suggestions.size());
	}

	public void leaveControl() {
		FocusManager.getInstance().leaveWidget(this);
	}
}
