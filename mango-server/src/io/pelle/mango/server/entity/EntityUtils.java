package io.pelle.mango.server.entity;

import io.pelle.mango.client.base.vo.EntityDescriptor;
import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.client.base.vo.LongAttributeDescriptor;
import io.pelle.mango.client.base.vo.StringAttributeDescriptor;
import io.pelle.mango.client.base.vo.query.ComparisonOperator;
import io.pelle.mango.client.base.vo.query.IBooleanExpression;
import io.pelle.mango.client.base.vo.query.IExpression;
import io.pelle.mango.client.base.vo.query.LOGICAL_OPERATOR;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.base.vo.query.expressions.BooleanExpression;
import io.pelle.mango.client.base.vo.query.expressions.CompareExpression;
import io.pelle.mango.client.base.vo.query.expressions.PathExpression;
import io.pelle.mango.client.base.vo.query.expressions.StringExpression;
import io.pelle.mango.db.query.ServerSelectQuery;
import io.pelle.mango.db.voquery.AttributesDescriptorQuery;
import io.pelle.mango.db.voquery.VOClassQuery;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.spel.SpelNode;
import org.springframework.expression.spel.ast.IntLiteral;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.expression.spel.ast.OpEQ;
import org.springframework.expression.spel.ast.OpOr;
import org.springframework.expression.spel.ast.OperatorMatches;
import org.springframework.expression.spel.ast.PropertyOrFieldReference;
import org.springframework.expression.spel.ast.StringLiteral;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class EntityUtils {

	public static class ReferenceTree {

		private Map<IAttributeDescriptor<?>, ReferenceTree> attributePaths = new HashMap<IAttributeDescriptor<?>, ReferenceTree>();

		public Map<IAttributeDescriptor<?>, ReferenceTree> getAttributePaths() {
			return attributePaths;
		}

	}

	@SuppressWarnings("unchecked")
	public static ReferenceTree parseReferenceTree(Class<? extends IVOEntity> voEntityClass, String referenceTree) {

		ReferenceTree result = new ReferenceTree();

		AttributesDescriptorQuery<?> attributesDescriptorQuery = VOClassQuery.createQuery(voEntityClass).attributesDescriptors();
		StringTokenizer referenceTreeTokenizer = new StringTokenizer(referenceTree, ",");

		while (referenceTreeTokenizer.hasMoreTokens()) {

			String currentToken = referenceTreeTokenizer.nextToken();
			String attributeName = currentToken;
			String subAttributeName = null;

			if (currentToken.contains("[") && currentToken.endsWith("]")) {

				int i = currentToken.indexOf("[");

				attributeName = currentToken.substring(0, i);
				subAttributeName = currentToken.substring(i + 1, currentToken.length() - 1);
			}

			AttributesDescriptorQuery<?> tmp = attributesDescriptorQuery.byName(attributeName);

			if (tmp.hasExactlyOne()) {

				IAttributeDescriptor<?> attributeDescriptor = tmp.getSingleResult();
				ReferenceTree subTree = null;

				if (subAttributeName != null) {

					if (!IVOEntity.class.isAssignableFrom(attributeDescriptor.getAttributeType())) {
						throw new RuntimeException(String.format("attribute '%s' of class '%s' is no an IVOEntity type", currentToken, voEntityClass.getName()));
					} else {
						subTree = parseReferenceTree((Class<? extends IVOEntity>) attributeDescriptor.getAttributeType(), subAttributeName);
					}
				}

				result.getAttributePaths().put(attributeDescriptor, subTree);
			} else {
				throw new RuntimeException(String.format("class '%s' has no attribute '%s'", voEntityClass.getName(), currentToken));
			}
		}

		return result;
	}

	public static <T extends IVOEntity> SelectQuery<T> createSelectQuery(Class<T> voEntityClass, String expressionString) {
		SelectQuery<T> selectQuery = SelectQuery.selectFrom(voEntityClass);

		if (!StringUtils.isEmpty(expressionString)) {
			IExpression expression = createExpression(voEntityClass, expressionString);
			selectQuery.where((IBooleanExpression) expression);
		}

		return selectQuery;
	}

	public static <T extends IVOEntity> ServerSelectQuery<T> createServerSelectQuery(Class<T> voEntityClass, String expressionString) {
		return ServerSelectQuery.adapt(createSelectQuery(voEntityClass, expressionString));
	}

	public static <T extends IVOEntity> IExpression createExpression(Class<T> voEntityClass, String expressionString) {

		SpelExpressionParser parser = new SpelExpressionParser();
		SpelExpression exp = parser.parseRaw(expressionString);
		IExpression expression = createExpression(exp.getAST(), voEntityClass);
		return expression;
	}

	public static <T extends IVOEntity> IExpression createExpression(SpelNode spelNode, Class<T> voEntityClass) {

		VOClassQuery voClassQuery = VOClassQuery.createQuery(voEntityClass);

		if (spelNode instanceof OpAnd) {
			OpAnd node = (OpAnd) spelNode;
			return new BooleanExpression(createExpression(node.getLeftOperand(), voEntityClass), LOGICAL_OPERATOR.AND, createExpression(node.getRightOperand(), voEntityClass));
		}

		if (spelNode instanceof OpOr) {
			OpOr node = (OpOr) spelNode;
			return new BooleanExpression(createExpression(node.getLeftOperand(), voEntityClass), LOGICAL_OPERATOR.OR, createExpression(node.getRightOperand(), voEntityClass));
		}

		if (spelNode instanceof OpEQ) {
			OpEQ node = (OpEQ) spelNode;

			return new CompareExpression(createExpression(node.getLeftOperand(), voEntityClass), ComparisonOperator.EQUALS, createExpression(node.getRightOperand(), voEntityClass));
		}

		if (spelNode instanceof OperatorMatches) {
			OperatorMatches node = (OperatorMatches) spelNode;

			return new CompareExpression(createExpression(node.getLeftOperand(), voEntityClass), ComparisonOperator.LIKE_NO_CASE, createExpression(node.getRightOperand(), voEntityClass));
		}

		if (spelNode instanceof PropertyOrFieldReference) {
			PropertyOrFieldReference node = (PropertyOrFieldReference) spelNode;

			String attributeName = node.getName();

			if (!voClassQuery.attributesDescriptors().byName(attributeName).hasExactlyOne()) {
				throw new RuntimeException(String.format("attribute '%s' not found for class '%s'", attributeName, voEntityClass.getName()));
			}

			return new PathExpression(voEntityClass.getName(), node.getName());
		}

		if (spelNode instanceof IntLiteral) {
			IntLiteral node = (IntLiteral) spelNode;
			return new StringExpression(node.getOriginalValue());
		}

		if (spelNode instanceof StringLiteral) {
			StringLiteral node = (StringLiteral) spelNode;
			return new StringExpression(node.getLiteralValue().getValue().toString());
		}

		throw new RuntimeException(String.format("unsupported spel node '%s'", spelNode));
	}

	public static ComparisonOperator parseOperatorFromText(String text) {

		if (text.endsWith("%")) {
			return ComparisonOperator.LIKE_NO_CASE;
		}

		return ComparisonOperator.EQUALS_NO_CASE;
	}
	
	public static StringAttributeDescriptor createStringAttributeDescriptor(Class<? extends  IBaseVO> voClass, String attributeName) {
		return new StringAttributeDescriptor(new EntityDescriptor<IBaseVO>(voClass, null, null, null), attributeName);
	}

	public static LongAttributeDescriptor createLongAttributeDescriptor(Class<? extends  IBaseVO> voClass, String attributeName) {
		return new LongAttributeDescriptor(new EntityDescriptor<IBaseVO>(voClass, null, null, null), attributeName);
	}
}
