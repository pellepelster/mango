package io.pelle.mango.server.entity;

import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.IVOEntity;
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

import org.springframework.expression.spel.SpelNode;
import org.springframework.expression.spel.ast.IntLiteral;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.expression.spel.ast.OpEQ;
import org.springframework.expression.spel.ast.OpOr;
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

	public static <T extends IBaseVO> ServerSelectQuery<T> createSelectQuery(Class<T> voClass, String expressionString) {

		IExpression expression = createExpression(voClass, expressionString);

		SelectQuery<T> selectQuery = SelectQuery.selectFrom(voClass).where((IBooleanExpression) expression);

		return ServerSelectQuery.adapt(selectQuery);
	}

	public static IExpression createExpression(Class<? extends IBaseVO> voClass, String expressionString) {

		SpelExpressionParser parser = new SpelExpressionParser();
		SpelExpression exp = parser.parseRaw(expressionString);
		IExpression expression = createExpression(exp.getAST(), voClass);
		return expression;
	}

	public static IExpression createExpression(SpelNode spelNode, Class<? extends IBaseVO> voClass) {

		VOClassQuery voClassQuery = VOClassQuery.createQuery(voClass);

		if (spelNode instanceof OpAnd) {
			OpAnd node = (OpAnd) spelNode;
			return new BooleanExpression(createExpression(node.getLeftOperand(), voClass), LOGICAL_OPERATOR.AND, createExpression(node.getRightOperand(), voClass));
		}

		if (spelNode instanceof OpOr) {
			OpOr node = (OpOr) spelNode;
			return new BooleanExpression(createExpression(node.getLeftOperand(), voClass), LOGICAL_OPERATOR.OR, createExpression(node.getRightOperand(), voClass));
		}

		if (spelNode instanceof OpEQ) {
			OpEQ node = (OpEQ) spelNode;

			return new CompareExpression(createExpression(node.getLeftOperand(), voClass), ComparisonOperator.EQUALS, createExpression(node.getRightOperand(), voClass));
		}

		if (spelNode instanceof PropertyOrFieldReference) {
			PropertyOrFieldReference node = (PropertyOrFieldReference) spelNode;

			String attributeName = node.getName();

			if (!voClassQuery.attributesDescriptors().byName(attributeName).hasExactlyOne()) {
				throw new RuntimeException(String.format("attribute '%s' not found for class '%s'", attributeName, voClass.getName()));
			}

			return new PathExpression(voClass.getName(), node.getName());
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
}
