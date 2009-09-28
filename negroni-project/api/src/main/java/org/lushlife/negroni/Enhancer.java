/*
 * Copyright 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.lushlife.negroni;

/**
 * mix-inとmethod missingを実装したクラスの生成を担当する。
 * 
 * @author Takeshi Kondo
 */
public interface Enhancer {

	/**
	 * mix-inとMethodMissingメソッドを未定義のメソッドに織り込む。
	 */
	<T> Class<? extends T> enhace(Class<T> clazz);

	/**
	 * instanceにmixinメソッドを動的に追加する。
	 */
	<T> T mixin(Class<T> mixinInterface, Object instance);

}
