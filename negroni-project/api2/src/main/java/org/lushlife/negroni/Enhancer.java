package org.lushlife.negroni;

public interface Enhancer {

	/**
	 * mix-inとMethodMissingメソッドを未定義のメソッドに織り込む。
	 * 
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	<T> Class<? extends T> enhace(Class<T> clazz);

}
