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
package org.lushlife.negroni.delegate;

import java.lang.reflect.Method;
import java.util.Map;

import org.lushlife.negroni.Container;

/**
 * @author Takeshi Kondo
 */
public interface DelegateMethod extends Comparable<DelegateMethod> {
	/**
	 * Method呼び出しの委譲が可能かどうか?
	 * 
	 * trueならば委譲可能、そうでなければ委譲不可
	 */
	boolean isAccept(Class<?> owner, Method m);

	Method getDelegateMethod();

	Object invoke(Map<String, Object> instanceContextMap, Container container,
			Object owner, Method method, Object[] args) throws Exception;

}
