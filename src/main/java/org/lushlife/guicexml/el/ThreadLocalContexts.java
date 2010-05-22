package org.lushlife.guicexml.el;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class ThreadLocalContexts {

	private static ThreadLocal<LinkedList<Map<String, Object>>> context = new ThreadLocal<LinkedList<Map<String, Object>>>() {
		@Override
		protected LinkedList<Map<String, Object>> initialValue() {
			return new LinkedList<Map<String, Object>>();
		}
	};

	static public Map<String, Object> getContextMap() {
		LinkedList<Map<String, Object>> list = context.get();
		if (list.size() == 0) {
			list.add(new HashMap<String, Object>());
		}
		return list.getLast();
	}

	static abstract public class Put<E extends Throwable> implements Callable<E> {

		public Put(String key, Object value) throws E {
			Map<String, Object> map = getContextMap();
			Object oldObject = map.put(key, value);
			try {
				call();
			} finally {
				map.put(key, oldObject);
			}

		}

		static abstract public class Nest<E extends Throwable> implements
				Callable<E> {
			public Nest(Map<String, Object> map) throws E {
				LinkedList<Map<String, Object>> list = context.get();
				try {
					list.add(map);
					call();
				} finally {
					list.removeLast();
				}
			}
		}
	}
}
