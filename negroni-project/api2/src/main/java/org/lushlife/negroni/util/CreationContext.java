package org.lushlife.negroni.util;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.lushlife.negroni.delegate.DelegateMethod;

/**
 * @author k2.junior.s@gmail.com
 */
public interface CreationContext {

	Map<Method, List<DelegateMethod>> getCash();

}
