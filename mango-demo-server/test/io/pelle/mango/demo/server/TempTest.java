package io.pelle.mango.demo.server;

import io.pelle.mango.client.base.vo.IBaseVO;
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
import io.pelle.mango.db.test.mockup.EntityVOMapper;
import io.pelle.mango.demo.client.showcase.CountryVO;

import org.junit.Test;
import org.springframework.expression.spel.SpelNode;
import org.springframework.expression.spel.ast.IntLiteral;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.expression.spel.ast.OpEQ;
import org.springframework.expression.spel.ast.PropertyOrFieldReference;
import org.springframework.expression.spel.ast.StringLiteral;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class TempTest extends BaseDemoTest {

	@Test
	public void testLogEntriesFromPingTask() {

		SpelExpressionParser parser = new SpelExpressionParser();
		SpelExpression exp = parser.parseRaw("name == 'Nikola Tesla' && abc == 12");

		IExpression expression = temp(exp.getAST(), CountryVO.class);

		SelectQuery<CountryVO> s = SelectQuery.selectFrom(CountryVO.class).where((IBooleanExpression) expression);

		String jpql = ServerSelectQuery.adapt(s).getJPQL(EntityVOMapper.INSTANCE);
		jpql.toString();
	}

	public IExpression temp(SpelNode spelNode, Class<? extends IBaseVO> voClass) {

		if (spelNode instanceof OpAnd) {
			OpAnd node = (OpAnd) spelNode;

			return new BooleanExpression(temp(node.getLeftOperand(), voClass), LOGICAL_OPERATOR.AND, temp(node.getRightOperand(), voClass));
		}

		if (spelNode instanceof OpEQ) {
			OpEQ node = (OpEQ) spelNode;

			return new CompareExpression(temp(node.getLeftOperand(), voClass), ComparisonOperator.EQUALS, temp(node.getRightOperand(), voClass));
		}

		if (spelNode instanceof PropertyOrFieldReference) {
			PropertyOrFieldReference node = (PropertyOrFieldReference) spelNode;
			return new PathExpression(node.getName(), voClass.getName());
		}

		if (spelNode instanceof IntLiteral) {
			IntLiteral node = (IntLiteral) spelNode;
			return new StringExpression(node.getOriginalValue());
		}

		if (spelNode instanceof StringLiteral) {
			StringLiteral node = (StringLiteral) spelNode;
			return new StringExpression(node.getOriginalValue());
		}

		throw new RuntimeException(String.format("unsupported spel node '%s'", spelNode));
	}

}
