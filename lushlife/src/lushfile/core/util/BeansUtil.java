package lushfile.core.util;

public class BeansUtil {

	static public String toPropertyName(String clazzName) {
		if (clazzName == null || clazzName.length() == 0) {
			return clazzName;
		}
		char[] chars = clazzName.toCharArray();
		if (chars.length == 1) {
			return new String(new char[] { Character.toLowerCase(chars[0]) });
		}
		if (Character.isUpperCase(chars[1])) {
			return clazzName;
		}
		return Character.toLowerCase(chars[0]) + clazzName.substring(1);
	}

	static public String toClassName(String propertyName) {
		if (propertyName == null || propertyName.length() == 0) {
			return propertyName;
		}
		char[] chars = propertyName.toCharArray();
		return Character.toUpperCase(chars[0]) + propertyName.substring(1);

	}

}
