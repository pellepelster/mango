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

	private List<VOTYPE> availableVOs = new ArrayList<VOTYPE>();

	private List<VOTYPE> selectedVOs = new ArrayList<VOTYPE>();

	public ReferenceListTestContainer(IReferenceList<VOTYPE> referenceList) {
		super();
		this.referenceList = referenceList;

		referenceList.addUpdateListener(this);
	}

	@Override
	public void onUpdate() {
		availableVOs.clear();
		availableVOs.addAll(referenceList.getAvailableVOs());

		selectedVOs.clear();
		selectedVOs.addAll(referenceList.getSelectedVOs());
	}

	public void updateReferenceList() {

		AsyncCallbackFuture<Void> future = AsyncCallbackFuture.create();

		referenceList.updateReferenceList(future);

		future.get();
	}

	public void assertAvailableVOsSize(int expected) {
		Assert.assertEquals(expected, availableVOs.size());
	}

	public void assertSelectedVOsSize(int expected) {
		Assert.assertEquals(expected, selectedVOs.size());
	}

	public void addVOs(List<VOTYPE> vos) {
		referenceList.addVOs(vos);
	}

	public void removeVOs(List<VOTYPE> vos) {
		referenceList.removeVOs(vos);
	}

	public List<VOTYPE> getAvailableVOs() {
		return availableVOs;
	}

	public List<VOTYPE> getSelectedVOs() {
		return selectedVOs;
	}

}
