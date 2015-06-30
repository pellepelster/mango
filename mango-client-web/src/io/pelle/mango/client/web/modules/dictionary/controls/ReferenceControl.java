package io.pelle.mango.client.web.modules.dictionary.controls;

import io.pelle.mango.client.base.messages.IMessage;
import io.pelle.mango.client.base.messages.ValidationMessage;
import io.pelle.mango.client.base.modules.dictionary.controls.IReferenceControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.IDictionaryModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IReferenceControlModel;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.query.ComparisonOperator;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.expressions.CompareExpression;
import io.pelle.mango.client.base.vo.query.expressions.NumberExpression;
import io.pelle.mango.client.base.vo.query.expressions.PathExpression;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;
import io.pelle.mango.client.web.modules.dictionary.base.DictionaryUtil;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Optional;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ReferenceControl<VOTYPE extends IBaseVO> extends BaseDictionaryControl<IReferenceControlModel, VOTYPE> implements IReferenceControl<VOTYPE> {

	private String valueString;

	private boolean changed = false;

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
		changed = true;
		this.valueString = valueString;
		setValueInternal(null, false);
	}

	@Override
	public void parseValue(String valueString, final AsyncCallback<List<ReferenceControl.Suggestion<VOTYPE>>> callback) {
		parseValue(valueString);

		IDictionaryModel dictionaryModel = DictionaryUtil.getDictionaryModel(getModel());
				
		AsyncCallback<List<VOTYPE>> resultCallback = new BaseErrorAsyncCallback<List<VOTYPE>>() {

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
		};

		if (dictionaryModel.getLabelControls().isEmpty()) {
			MangoClientWeb.getInstance().getRemoteServiceLocator().getBaseEntityService().searchByNaturalKey(dictionaryModel.getVOClass().getName(), valueString, resultCallback);
		} else {
			DictionaryUtil.getEntitiesByDictionaryLabel(getModel(), valueString, resultCallback);
		}

	}

	public void setValue(VOTYPE value) {
		super.setValue(value);
		changed = false;
	}

	@Override
	public void endEdit() {

		if (changed) {

			if (valueString == null || valueString.trim().isEmpty()) {
				setValue(null);
			} else {
				DictionaryUtil.getEntitiesByDictionaryLabel(getModel(), valueString, new BaseErrorAsyncCallback<List<VOTYPE>>() {

					@Override
					public void onSuccess(List<VOTYPE> result) {
						if (result.size() == 1) {
							setValue(result.get(0));
						} else if (result.size() == 0) {
							addValidationMessage(new ValidationMessage(IMessage.SEVERITY.ERROR, ReferenceControl.class.getName(), MangoClientWeb.MESSAGES.referenceParseError(valueString)));
						}
					}
				});
			}
		}
	}

	@Override
	public Optional<IBooleanExpression> getExpression(PathExpression pathExpression) {
		if (getValue() != null) {
			return Optional.<IBooleanExpression> of(new CompareExpression(pathExpression, ComparisonOperator.EQUALS, new NumberExpression(getValue().getId())));
		} else {
			return super.getExpression(pathExpression);
		}
	}

	@Override
	protected BaseDictionaryControl<IReferenceControlModel, VOTYPE>.ParseResult parseValueInternal(String valueString) {
		throw new RuntimeException("not implemented");
	}

}
