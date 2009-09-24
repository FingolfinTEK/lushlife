package org.lushlife.negroni.core.impl.guice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.lushlife.negroni.Identifier;
import org.lushlife.negroni.annotation.literal.Configuration;
import org.lushlife.negroni.core.impl.ConfiguratorBase;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.LatteGuiceUtil;
import com.google.inject.Module;

public class GuiceConfigurator extends ConfiguratorBase {
	List<Module> module = new ArrayList<Module>();

	public List<Module> getModule() {
		return module;
	}

	public GuiceConfigurator() {
		module.add(new Module() {

			public void configure(Binder binder) {
				binder.bindScope(
						org.lushlife.negroni.annotation.InstanceScoped.class,
						new InstanceScope());
			}
		});
		init();

	}

	public GuiceConfigurator add(Module m) {
		module.add(m);
		return this;
	}

	public GuiceEnhancer create() {
		final Injector injector = Guice.createInjector(module);
		return new GuiceEnhancer(injector, this);
	}

	public <T> GuiceConfigurator add(final Class<T> clazz) {
		module.add(new Module() {
			public void configure(Binder binder) {
				LatteGuiceUtil
						.bindFactory(binder, Key.get(clazz),
								new LatteInternalFactory<T>(
										GuiceConfigurator.this,
										new Configuration(clazz,
												GuiceConfigurator.this)));
			}
		});
		return this;
	}

	@SuppressWarnings("unchecked")
	public Identifier<?> toId(Type type, Annotation bindingTypes) {
		if (bindingTypes == null) {
			return new GuiceIdentifer(Key.get(type));
		} else {
			return new GuiceIdentifer(Key.get(type, bindingTypes));
		}
	}

	protected void configuration(final Configuration configuration,
			final Identifier<?> id) {
		module.add(new Module() {
			public void configure(Binder binder) {
				LatteGuiceUtil.bindFactory(binder, ((GuiceIdentifer) id)
						.getKey(), new LatteInternalFactory(
						GuiceConfigurator.this, configuration));
			}
		});
	}
}
