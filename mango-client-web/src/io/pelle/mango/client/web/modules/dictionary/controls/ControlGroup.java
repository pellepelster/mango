package io.pelle.mango.client.web.modules.dictionary.controls;

import io.pelle.mango.client.base.modules.dictionary.controls.IControlGroup;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IControlGroupModel;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.expressions.PathExpression;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Optional;

public class ControlGroup extends BaseDictionaryControl<IControlGroupModel, Object[]> implements IControlGroup {

	private List<BaseDictionaryControl<IBaseControlModel, ?>> controls = new ArrayList<BaseDictionaryControl<IBaseControlModel, ?>>();

	public ControlGroup(IControlGroupModel controlGroupModel, BaseDictionaryElement<? extends IBaseModel> parent) {
		super(controlGroupModel, parent);

		for (IBaseControlModel baseControlModel : controlGroupModel.getControls()) {
			controls.add(ControlFactory.createControl(baseControlModel, this));
		}
	}

	@Override
	public void parseValue(String valueString) {
		for (BaseDictionaryControl<? extends IBaseControlModel, ?> baseControl : controls) {
			baseControl.parseValue(valueString);
		}
		super.parseValue(valueString);
	}

	@Override
	public Optional<IBooleanExpression> getExpression(PathExpression pathExpression) {

		Optional<IBooleanExpression> expression = Optional.absent();

		for (BaseDictionaryControl<? extends IBaseControlModel, ?> baseControl : controls) {

			PathExpression groupPathExpression = new PathExpression(pathExpression.getClassName(), baseControl.getModel().getAttributePath());
			Optional<IBooleanExpression> compareExpression = baseControl.getExpression(groupPathExpression);

			if (compareExpression.isPresent()) {
				if (expression.isPresent()) {
					expression = Optional.of(expression.get().or(compareExpression.get()));
				} else {
					expression = compareExpression;
				}
			}
		}

		return expression;
	}

	@Override
	protected BaseDictionaryControl<IControlGroupModel, Object[]>.ParseResult parseValueInternal(String valueString) {
		return new ParseResult(new String[] { valueString });
	}

	public List<BaseDictionaryControl<IBaseControlModel, ?>> getControls() {
		return controls;
	}

}
