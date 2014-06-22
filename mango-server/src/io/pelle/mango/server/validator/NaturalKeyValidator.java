package io.pelle.mango.server.validator;

import io.pelle.mango.client.base.db.vos.NaturalKey;
import io.pelle.mango.client.base.messages.IValidationMessage;
import io.pelle.mango.client.base.messages.ValidationMessage;
import io.pelle.mango.client.base.util.CollectionUtils;
import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.base.vo.query.expressions.ExpressionFactory;
import io.pelle.mango.db.dao.BaseVODAO;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.google.common.base.Optional;

/**
 * Checks all {@link IBaseVO} derived Vo's whether they contain values in
 * {@link NaturalKey} annotated properties that are already present in the
 * database
 * 
 * @author pelle
 * 
 */
public class NaturalKeyValidator implements IValidator {
	@Resource
	private BaseVODAO baseVODAO;

	public NaturalKeyValidator() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public boolean canValidate(Object o) {
		return IBaseVO.class.isAssignableFrom(o.getClass());
	}

	protected void setBaseVODAO(BaseVODAO baseVODAO) {
		this.baseVODAO = baseVODAO;
	}

	@Override
	public List<IValidationMessage> validate(Object o) {
		IBaseVO vo = (IBaseVO) o;

		List<IValidationMessage> result = new ArrayList<IValidationMessage>();

		SelectQuery<IBaseVO> selectQuery = (SelectQuery<IBaseVO>) SelectQuery.selectFrom(vo.getClass());
		Optional<IBooleanExpression> expression = Optional.absent();

		for (IAttributeDescriptor<?> attributeDescriptor : new AnnotationIterator(vo.getClass(), NaturalKey.class)) {
			Optional<Object> naturalKey = Optional.fromNullable(vo.get(attributeDescriptor.getAttributeName()));

			if (naturalKey.isPresent() && !naturalKey.get().toString().isEmpty()) {
				Optional<IBooleanExpression> compareExpression = ExpressionFactory.createStringEqualsExpression(vo.getClass(), attributeDescriptor.getAttributeName(), vo.get(attributeDescriptor.getAttributeName()).toString());

				if (compareExpression.isPresent()) {
					if (expression.isPresent()) {
						expression = Optional.of(expression.get().and(compareExpression.get()));
					} else {
						expression = compareExpression;
					}
				}
			} else {
				result.add(new ValidationMessage(ValidatorMessages.NATURAL_KEY_MANDATORY, CollectionUtils.getMap(IValidationMessage.ATTRIBUTE_CONTEXT_KEY, attributeDescriptor.getAttributeName())));
			}
		}

		if (expression.isPresent()) {
			selectQuery.where(expression.get());

			List<IBaseVO> filterResult = this.baseVODAO.filter(selectQuery);

			if (filterResult.size() > 1 || (filterResult.size() == 1 && filterResult.get(0).getOid() != vo.getOid())) {
				result.add(new ValidationMessage(ValidatorMessages.NATURAL_KEY, CollectionUtils.getMap(IValidationMessage.NATURAL_KEY_CONTEXT_KEY, vo.getNaturalKey())));
			}
		}

		return result;
	}
}
