package lushfile.plugins.controller.event;

import java.lang.reflect.Method;

import lushfile.core.controller.ActionParameter;
import lushfile.core.controller.LushEvent;
import lushfile.core.controller.ReflectionErrorParameter;
import lushfile.plugins.context.RequestContext;

import com.google.inject.Inject;
import com.google.inject.Injector;

public abstract class ActionEvent implements LushEvent {
	@Inject
	private ClassLoader loader;

	@Inject
	private Injector injector;

	@Inject
	private EventConverter converter;

	@Inject
	protected RequestContext parameter;

	protected String packageName;

	protected LushEvent toEvent(ReflectionErrorParameter param) {
		return converter.toEvent(param);
	}

	protected LushEvent toEvent(ActionParameter returnValue) {
		return converter.toEvent(returnValue);
	}

	public ActionEvent() {
		super();
	}

	public LushEvent init(String packageName) {
		this.packageName = packageName;
		return this;
	}

	@Override
	public LushEvent fire() {
		String oldPackageName = parameter.getPackageName();
		try {
			parameter.setPackageName(packageName);

			Class<?> clazz;
			Object instance;
			Method method;
			String className = resolveActionClass();
			String methodName = resolveActionMethod();
			try {
				clazz = loader.loadClass(className);
				method = clazz.getMethod(methodName);
				instance = injector.getInstance(clazz);
			} catch (Exception e) {
				ReflectionErrorParameter param = injector.getInstance(
						ReflectionErrorParameter.class).init(packageName, null,
						null, e);
				return toEvent(param);
			}

			try {
				Object returnValue = method.invoke(instance);
				ActionParameter parameter = injector.getInstance(
						ActionParameter.class).init(packageName, method,
						instance, returnValue);
				return toEvent(parameter);
			} catch (Exception e) {
				ReflectionErrorParameter parameter = injector.getInstance(
						ReflectionErrorParameter.class).init(packageName,
						method, instance, e);
				return toEvent(parameter);
			}
		} finally {
			parameter.setPackageName(oldPackageName);
		}
	}

	abstract protected String resolveActionMethod();

	abstract protected String resolveActionClass();

}