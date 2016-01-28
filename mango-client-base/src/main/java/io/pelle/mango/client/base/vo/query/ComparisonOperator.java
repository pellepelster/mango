package io.pelle.mango.client.base.vo.query;

public enum ComparisonOperator {

	GREATER(">", null), GREATER_EQUALS(">=", null), LESS("<", null), LESS_EQUALS("<=", null), EQUALS("=", null), NOT_EQUALS("!=", null), EQUALS_NO_CASE("=", "LOWER"), LIKE_NO_CASE("LIKE", "LOWER"), IS_NULL("IS NULL", null), NOT_NULL("IS NOT NULL", null);

	private String function;

	private String operator;

	private ComparisonOperator(String operator, String function) {
		this.operator = operator;
		this.function = function;
	}

	private ComparisonOperator(String operator) {
		this(operator, null);
	}

	@Override
	public String toString() {
		return operator;
	}

	public String operand1Function(String operand) {

		if (function == null) {
			return operand;
		} else {
			return function + "(" + operand + ")";
		}
	}

	public String operand2Function(String operand) {

		if (function == null) {
			return operand;
		} else {
			return function + "(" + operand + ")";
		}
	}

}
