package io.pelle.mango.client.base.modules.dictionary.model.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.ITextControl;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.vo.IAttributeDescriptor;

public class TextControlModel extends BaseControlModel<ITextControl>implements ITextControlModel {

	private static final long serialVersionUID = -6029017257538622486L;

	private int maxLength = MAX_LENGTH_DEFAULT;

	private int minLength = MIN_LENGTH_DEFAULT;

	public TextControlModel(String name, IBaseModel parent) {
		super(name, parent);
	}

	public TextControlModel(String name, IAttributeDescriptor<?> attributeDescriptor) {
		this(name, attributeDescriptor.getAttributeName());
	}

	public TextControlModel(String name, String attributeName) {
		super(name, null);
		setLabel(name);
		setAttributePath(attributeName);
	}

	@Override
	public String getFormatRegularExpression() {
		return null;
	}

	@Override
	public int getMaxLength() {
		return this.maxLength;
	}

	@Override
	public int getMinLength() {
		return this.minLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

}
