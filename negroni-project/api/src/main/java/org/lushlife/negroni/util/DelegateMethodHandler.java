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
package org.lushlife.negroni.util;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javassist.util.proxy.MethodHandler;

import org.lushlife.negroni.Container;
import org.lushlife.negroni.LogMsgNGLN;
import org.lushlife.negroni.delegate.DelegateMethod;
import org.lushlife.stla.Log;
import org.lushlife.stla.Logging;

/**
 * @author Takeshi Kondo
 */
public class DelegateMethodHandler implements MethodHandler {
	static public Log log = Logging.getLog(DelegateMethodHandler.class);
	private Object owner;
	private Container container;
	private Map<String, Object> contextMap = new ConcurrentHashMap<String, Object>();
	private Map<Method, DelegateMethod> mapping;

	public DelegateMethodHandler(Object owner, Container container,
			Map<Method, DelegateMethod> mapping) {
		this.container = container;
		this.mapping = mapping;
		this.owner = owner;
	}

	public Object invoke(Object self, Method thisMethod, Method proceed,
			Object[] args) throws Throwable {
		Object obj = mapping.get(thisMethod).invoke(contextMap, container,
				this.owner, thisMethod, args);
		log.log(LogMsgNGLN.NGLN00004, thisMethod, obj);
		return obj;
	}
}