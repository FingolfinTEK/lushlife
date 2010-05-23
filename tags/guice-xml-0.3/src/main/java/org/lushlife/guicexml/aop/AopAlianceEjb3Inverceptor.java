/*
 * Copyright 2010 the original author or authors.
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
package org.lushlife.guicexml.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Takeshi Kondo
 */
public class AopAlianceEjb3Inverceptor implements MethodInterceptor {
	private Ejb3Interceptor interceptor;

	public AopAlianceEjb3Inverceptor(Ejb3Interceptor interceptor) {
		this.interceptor = interceptor;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		return interceptor.aroundInvoke(new AopAlianceInvocationContext(
				invocation));
	}

}