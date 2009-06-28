package negroni.core.impl.conversions.converter;

import negroni.core.impl.conversions.Converter;

public class NumberConverter implements Converter<Number> {

	Converter<?>[] converters = new Converter<?>[] { new ByteConverter(),
			new ShortConverter(), new IntegerConverter(), new LongConverter(),
			new FloatConverter(), new DoubleConverter() };

	public Number convert(Object obj) {
		for (Converter<?> c : converters) {
			if (c.isAssignableTo(obj.getClass())) {
				return (Number) c.convert(obj);
			}
		}

		return null;
	}

	public boolean isAssignableTo(Class<?> from) {
		for (Converter<?> c : converters) {
			if (c.isAssignableTo(from)) {
				return true;
			}
		}
		return false;
	}

}
