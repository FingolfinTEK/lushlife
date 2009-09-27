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

import java.util.Set;

import org.lushlife.negroni.impl.EnhancerImpl;
import org.lushlife.negroni.impl.SimpleConstructContainer;
import org.lushlife.negroni.util.Reflections;

/**
 * Negroni API のファサードクラスです。
 * 
 * @author Takeshi Kondo
 */
public class Negroni {

	/**
	 * Enhancerを生成します。ContainerはSimpleContainerを使用します。
	 * 
	 * @return
	 */
	static public Enhancer create() {
		return create(new SimpleConstructContainer());
	}

	/**
	 * DIコンテナと連携させたEnhahcerを生成します。 Containerを連携させたいDIコンテナ用に作成してください。
	 * 
	 * @param container
	 * @return
	 */
	static public Enhancer create(Container container) {
		return new EnhancerImpl(container);
	}

	/**
	 * @Mixinedアノテーションの一覧を取得します。
	 * @param clazz
	 * @return
	 */
	static public Set<Class<?>> mixinCLasses(Class<?> clazz) {
		return Reflections.mixinImplementClasses(clazz);
	}
}
