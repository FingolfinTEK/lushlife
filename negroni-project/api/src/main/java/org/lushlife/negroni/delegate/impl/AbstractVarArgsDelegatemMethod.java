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
package org.lushlife.negroni.delegate.impl;

import java.lang.reflect.Method;

/**
 * 可変長引数のMethodDelegate
 * 
 * @author Takeshi Kondo
 */
public abstract class AbstractVarArgsDelegatemMethod extends
		AbstractDelegateMethod {

	// 可変長引数の開始位置
	private int varArgsPosition;
	// 可変長引数の型
	private Class<?> varArgsType;

	public AbstractVarArgsDelegatemMethod(Method method) {
		super(method);
		Class<?>[] typs = method.getParameterTypes();
		this.varArgsPosition = typs.length - 1;
		this.varArgsType = typs[typs.length - 1].getComponentType();
	}

	public int getVarArgsPosition() {
		return varArgsPosition;
	}

	public Class<?> getVarArgsType() {
		return varArgsType;
	}
}
