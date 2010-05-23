package org.lushlife.guicexml.converter;

import java.lang.reflect.Type;

import javax.el.ValueExpression;

import org.lushlife.guicexml.el.Expressions;
import org.lushlife.guicexml.spi.Converter;

public class ValueExpressionConverter implements Converter<ValueExpression> {

	@Override
	public ValueExpression convert(String str) {
		return Expressions.createValueExpression(str);
	}

	@Override
	public Type[] getTypes() {
		return new Type[] { ValueExpression.class };
	}

}
