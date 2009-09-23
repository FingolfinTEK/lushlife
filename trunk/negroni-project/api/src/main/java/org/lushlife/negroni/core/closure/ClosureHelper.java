package org.lushlife.negroni.core.closure;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lushlife.negroni.core.closure.helpers.CR1Helper;
import org.lushlife.negroni.core.closure.helpers.CR2Helper;
import org.lushlife.negroni.core.closure.helpers.CRHelper;
import org.lushlife.negroni.core.closure.helpers.CV1Helper;
import org.lushlife.negroni.core.closure.helpers.CV2Helper;
import org.lushlife.negroni.core.closure.helpers.CVHelper;
import org.lushlife.negroni.core.exceptions.NegroniException;
import org.lushlife.negroni.core.impl.reflection.MethodId;



public abstract class ClosureHelper {
	private static final Map<MethodId, ClosureHelper> helperMap = new HashMap<MethodId, ClosureHelper>();

	static public ClosureHelper get(Method m) {
		MethodId id = new MethodId(m);
		if (helperMap.containsKey(id)) {
			return helperMap.get(id);
		}
		for (ClosureHelper helper : helpers) {
			if (helper.isAccept(m)) {
				helperMap.put(id, helper);
				return helper;
			}
		}

		throw new NegroniException("ClosureHelper not found " + m);

	}

	public static final List<ClosureHelper> helpers = new ArrayList<ClosureHelper>();
	static {
		helpers.add(new CRHelper());
		helpers.add(new CR1Helper());
		helpers.add(new CR2Helper());
		helpers.add(new CVHelper());
		helpers.add(new CV1Helper());
		helpers.add(new CV2Helper());

	}

	Class<? extends Closure> type;

	public ClosureHelper(Class<? extends Closure> type) {
		this.type = type;
	}

	public Class<? extends Closure> getType() {
		return type;
	}

	abstract public boolean isAccept(Method m);

	abstract public Closure<?> toDelegator(Object mixin, Method m);
}
