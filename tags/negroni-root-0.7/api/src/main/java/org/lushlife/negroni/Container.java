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
 * mix-inのクラスをインスタンス化の役割を担うクラスです。
 * 
 * @author Takeshi Kondo
 */
public interface Container {
	/**
	 * 実装クラスのインスタンスを取得します。
	 */
	public <T> T getInstance(Class<T> clazz);

	/**
	 * コンテナでスコープ管理しているインスタンスかどうかを識別します。
	 * falseの場合にはDependeスコープ(組み込まれたクラスのフィールドと同じスコープ）になります。
	 */
	public boolean isManagementScope(Class<?> clazz);
}
