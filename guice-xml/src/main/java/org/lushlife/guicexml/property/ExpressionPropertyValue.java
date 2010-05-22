package org.lushlife.guicexml.property;

import java.lang.reflect.Type;

import javax.el.ValueExpression;

import org.lushlife.guicexml.el.Expressions;

public class ExpressionPropertyValue implements PropertyValue {
	private ValueExpression valueExpression;

	public ExpressionPropertyValue(ValueExpression valueExpression) {
		this.valueExpression = valueExpression;
	}

	@Override
	public Object resolveString(Type type, Expressions expressions) {
		return valueExpression.getValue(expressions.createELContext());
	}

	public String toString() {
		return valueExpression.getExpressionString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((valueExpression == null) ? 0 : valueExpression.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExpressionPropertyValue other = (ExpressionPropertyValue) obj;
		if (valueExpression == null) {
			if (other.valueExpression != null)
				return false;
		} else if (!valueExpression.equals(other.valueExpression))
			return false;
		return true;
	}

}
