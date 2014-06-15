package io.pelle.mango.client.web.test.modules.dictionary.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.IReferenceControl;
import io.pelle.mango.client.base.vo.IBaseVO;

public class ReferenceControlTest<VOType extends IBaseVO> extends BaseControlTest<IReferenceControl<VOType>, VOType> {

	public ReferenceControlTest(IReferenceControl<VOType> referenceControl) {
		super(referenceControl);
	}

}
