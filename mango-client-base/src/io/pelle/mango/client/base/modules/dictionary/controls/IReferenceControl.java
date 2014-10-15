package io.pelle.mango.client.base.modules.dictionary.controls;

import io.pelle.mango.client.base.modules.dictionary.model.controls.IReferenceControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IReferenceControl<VOTYPE extends IBaseVO> extends IBaseControl<VOTYPE, IReferenceControlModel> {

	public static class Suggestion<SUGGESTVOTYPE extends IBaseVO> {

		private final String label;

		private final SUGGESTVOTYPE vo;

		public Suggestion(SUGGESTVOTYPE vo, String label) {
			super();
			this.vo = vo;
			this.label = label;
		}

		public String getLabel() {
			return label;
		}

		public SUGGESTVOTYPE getVo() {
			return vo;
		}
	}

	void parseValue(String valueString, AsyncCallback<List<Suggestion<VOTYPE>>> callback);

}
