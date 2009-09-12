/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package stlog.util;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentEnumMapCache<V> {

	ReentrantLock lock = new ReentrantLock();
	Map<Class<? extends Enum<?>>, EnumMap<?, V>> cache = new ConcurrentHashMap<Class<? extends Enum<?>>, EnumMap<?, V>>();

	@SuppressWarnings("unchecked")
	public <E extends Enum<E>> V putIfAbsent(Enum<?> key,
			Closure<EnumMap<E, V>> callable) {

		Class<? extends Enum<?>> clazz = key.getDeclaringClass();
		EnumMap<?, V> enumMap = cache.get(clazz);
		if (enumMap == null) {
			lock.lock();
			try {
				enumMap = cache.get(clazz);
				if (enumMap == null) {
					enumMap = callable.call();
					cache.put(clazz, enumMap);
				}

			} finally {
				lock.unlock();
			}
		}
		return enumMap.get(key);
	}
	
	public void clear(){
		this.cache.clear();
	}

}