package lushfile.core.groovy;

import java.util.AbstractMap;
import java.util.Set;

import lushfile.core.context.LushNamingResolver;
import lushfile.core.context.RequestScopedMap;

import com.google.inject.Inject;

public class ShellMap extends AbstractMap<String, Object> {

	@Inject
	RequestScopedMap requestScopedMap;

	@Inject
	LushNamingResolver resolver;

	public Object get(Object key) {
		if (requestScopedMap.containsKey(key)) {
			return requestScopedMap.get(key);
		}
		return resolver.getInstneceByName((String) key);
	}

	@Override
	public Object put(String key, Object value) {
		return requestScopedMap.put(key, value);
	}

	@Override
	public boolean containsKey(Object key) {
		return this.get(key) != null;
	}

	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		throw new UnsupportedOperationException();
	}

}
