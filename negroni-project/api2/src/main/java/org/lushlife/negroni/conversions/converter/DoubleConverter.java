package org.lushlife.negroni.conversions.converter;

import org.lushlife.negroni.conversions.Converter;

public class DoubleConverter implements Converter<Double> {

	public Double convert(Object obj) {
		if (obj instanceof Double) {
			return (Double) obj;
		}
		return Double.valueOf(obj.toString());
	}

	public boolean isAssignableTo(Class<?> from) {
		if (from.equals(Double.class)) {
			return true;
		}
		if (from.equals(double.class)) {
			return true;
		}
		return false;
	}

}
