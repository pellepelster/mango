package io.pelle.mango.client.web.test.container;

import java.util.ArrayList;
import java.util.List;

import io.pelle.mango.client.base.modules.dictionary.IUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.container.IReferenceList;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.test.AsyncCallbackFuture;
import junit.framework.Assert;

public class ReferenceListTestContainer<VOTYPE extends IBaseVO> extends BaseTestContainer implements IUpdateListener {

	private IReferenceList<VOTYPE> referenceList;

	private List<IBaseVO> availableVOs = new ArrayList<IBaseVO>();

	public ReferenceListTestContainer(IReferenceList<VOTYPE> referenceList) {
		super();
		this.referenceList = referenceList;

		referenceList.addUpdateListener(this);
	}

	@Override
	public void onUpdate() {
		availableVOs.clear();
		availableVOs.addAll(referenceList.getAvailableVOs());
	}

	public void updateReferenceList() {

		AsyncCallbackFuture<Void> future = AsyncCallbackFuture.create();

		referenceList.updateReferenceList(future);

		future.get();
	}

	public void assertReferenceListSize(int expected) {
		Assert.assertEquals(expected, availableVOs.size());

	}

}
