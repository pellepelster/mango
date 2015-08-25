package io.pelle.mango.client.web.modules.dictionary.controls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Optional;
import com.google.common.base.Strings;

import io.pelle.mango.client.base.messages.IValidationMessage;
import io.pelle.mango.client.base.messages.IValidationMessages;
import io.pelle.mango.client.base.modules.dictionary.IBaseDictionaryElement;
import io.pelle.mango.client.base.modules.dictionary.IUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.controls.IBaseControl;
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelUtil;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.expressions.PathExpression;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;
import io.pelle.mango.client.web.modules.dictionary.databinding.IValidator;
import io.pelle.mango.client.web.modules.dictionary.databinding.validator.MandatoryValidator;
import io.pelle.mango.client.web.modules.dictionary.search.DictionarySearch;

public abstract class BaseDictionaryControl<ModelType extends IBaseControlModel, ValueType> extends BaseDictionaryElement<ModelType> implements IBaseControl<ValueType, ModelType> {

	protected class ParseResult {

		private IValidationMessage validationMessage;

		private ValueType value;

		public ParseResult() {
			super();
		}

		public ParseResult(ValueType value) {
			super();
			this.value = value;
		}

		public ParseResult(IValidationMessage validationMessage) {
			super();
			this.validationMessage = validationMessage;
		}

		public IValidationMessage getValidationMessage() {
			return this.validationMessage;
		}

		public ValueType getValue() {
			return this.value;
		}

	}

	private List<IUpdateListener> controlUpdateListeners = new ArrayList<IUpdateListener>();

	private List<IValidator> validators = new ArrayList<IValidator>();

	private static final MandatoryValidator MANDATORY_VALIDATOR = new MandatoryValidator();

	private boolean hasSearchAsRoot() {

		IBaseDictionaryElement<?> current = getParent();
		while (current != null) {

			if (current instanceof DictionarySearch<?>) {
				return true;
			}

			current = current.getParent();
		}

		return false;
	}

	public BaseDictionaryControl(ModelType baseControlModel, BaseDictionaryElement<? extends IBaseModel> parent) {
		super(baseControlModel, parent);

		if (!hasSearchAsRoot()) {
			if (baseControlModel.isMandatory()) {
				this.validators.add(MANDATORY_VALIDATOR);
			}
		}
	}

	public String getEditorLabel() {
		String label = DictionaryModelUtil.getEditorLabel(getModel());

		if (getModel().isMandatory()) {
			label += MangoClientWeb.MESSAGES.mandatoryMarker();
		}

		return label;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ValueType getValue() {
		return (ValueType) getVOWrapper().get(getAttributePathInternal());
	}

	@Override
	public void setValue(ValueType value) {
		setValueInternal(value);
		fireUpdateListeners();
	}

	private String getAttributePathInternal() {
		if (getParent() instanceof ControlGroup) {
			return getParent().getModel().getName() + "#" + getModel().getAttributePath();
		} else {
			return getModel().getAttributePath();
		}
	}

	protected void setValueInternal(ValueType value) {
		setValueInternal(value, true);
	}

	protected void setValueInternal(ValueType value, boolean fireDirtyListeners) {

		if (!isReadonly()) {
			getRootElement().clearValidationMessages(this);

			validate(value);

			if (getRootElement().getValidationMessages(this).hasErrors()) {
				getVOWrapper().set(getAttributePathInternal(), null, fireDirtyListeners);
			} else {
				getVOWrapper().set(getAttributePathInternal(), value, fireDirtyListeners);
			}
		}
	}

	private void validate(ValueType value) {
		for (IValidator validator : this.validators) {
			getRootElement().addValidationMessages(this, validator.validate(value, getModel()));
		}
	}

	@Override
	public ModelType getModel() {
		return super.getModel();
	}

	@Override
	public String format() {
		return getValue() != null ? getValue().toString() : "";
	}

	@Override
	public void parseValue(String valueString) {

		if (Strings.isNullOrEmpty(valueString)) {
			setValueInternal(null);
		} else {

			ParseResult parseResult = parseValueInternal(valueString);

			if (parseResult != null) {
				if (parseResult.getValidationMessage() == null) {
					setValueInternal(parseResult.getValue());
				} else {
					setValueInternal(null);
					getRootElement().getValidationMessages(this).addValidationMessage(parseResult.getValidationMessage());
				}
			}
		}

		fireUpdateListeners();
	}

	@Override
	public boolean isMandatory() {
		return getModel().isMandatory();
	}

	@Override
	public List<BaseDictionaryElement<?>> getAllChildren() {
		return Collections.emptyList();
	}

	public void addValidationMessage(IValidationMessage validationMessage) {
		getRootElement().getValidationMessages(this).addValidationMessage(validationMessage);
		fireUpdateListeners();
	}

	@Override
	public IValidationMessages getValidationMessages() {
		return getRootElement().getValidationMessages(this);
	}

	@Override
	public void addUpdateListener(IUpdateListener controlUpdateListener) {
		this.controlUpdateListeners.add(controlUpdateListener);
	}

	protected void fireUpdateListeners() {
		for (IUpdateListener controlUpdateListener : this.controlUpdateListeners) {
			controlUpdateListener.onUpdate();
		}
	}

	@Override
	public Optional<IBooleanExpression> getExpression(PathExpression pathExpression) {
		return Optional.absent();
	}

	@Override
	public void update() {
		fireUpdateListeners();
	}

	@Override
	public void beginEdit() {
	}

	@Override
	public void endEdit() {
	}

	@Override
	public boolean isReadonly() {
		return getModel().isReadonly();
	}

	protected abstract ParseResult parseValueInternal(String valueString);
}
