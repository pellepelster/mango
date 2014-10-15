package io.pelle.mango.client.web.modules.dictionary.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.IReferenceControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IReferenceControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.query.ComparisonOperator;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.expressions.CompareExpression;
import io.pelle.mango.client.base.vo.query.expressions.LongExpression;
import io.pelle.mango.client.base.vo.query.expressions.PathExpression;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;
import io.pelle.mango.client.web.modules.dictionary.base.DictionaryUtil;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Optional;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ReferenceControl<VOTYPE extends IBaseVO> extends BaseDictionaryControl<IReferenceControlModel, VOTYPE> implements IReferenceControl<VOTYPE> {

	private String valueString;

	public ReferenceControl(IReferenceControlModel referenceControlModel, BaseDictionaryElement<? extends IBaseModel> parent) {
		super(referenceControlModel, parent);
	}

	@Override
	public String format() {
		if (getValue() != null && getValue() instanceof IBaseVO) {
			return DictionaryUtil.getLabel(getModel(), getValue());
		} else {
			return super.format();
		}
	}

	@Override
	public void parseValue(String valueString) {
		this.valueString = valueString;
	}

	@Override
	public void parseValue(String valueString, final AsyncCallback<List<ReferenceControl.Suggestion<VOTYPE>>> callback) {
		parseValue(valueString);
		DictionaryUtil.getEntitiesByDictionaryLabel(getModel(), valueString, new BaseErrorAsyncCallback<List<VOTYPE>>() {

			@Override
			public void onSuccess(List<VOTYPE> result) {

				List<ReferenceControl.Suggestion<VOTYPE>> suggestions = new ArrayList<ReferenceControl.Suggestion<VOTYPE>>();

				for (VOTYPE vo : result) {
					String label = DictionaryUtil.getLabel(getModel(), vo);
					Suggestion<VOTYPE> suggestion = new ReferenceControl.Suggestion<VOTYPE>(vo, label);
					suggestions.add(suggestion);
				}

				callback.onSuccess(suggestions);
			}
		});

	}

	@Override
	public void endEdit() {

		DictionaryUtil.getEntitiesByDictionaryLabel(getModel(), valueString, new BaseErrorAsyncCallback<List<VOTYPE>>() {

			@Override
			public void onSuccess(List<VOTYPE> result) {
				if (result.size() == 1) {
					setValue(result.get(0));
				}
			}
		});
	}

	@Override
	public Optional<IBooleanExpression> getExpression(PathExpression pathExpression) {
		if (getValue() != null) {
			return Optional.<IBooleanExpression> of(new CompareExpression(pathExpression, ComparisonOperator.EQUALS, new LongExpression(getValue().getId())));
		} else {
			return super.getExpression(pathExpression);
		}
	}

	@Override
	protected BaseDictionaryControl<IReferenceControlModel, VOTYPE>.ParseResult parseValueInternal(String valueString) {
		throw new RuntimeException("not implemented");
	}

}
