package lushfile.plugins.context;

import lushfile.core.context.LushNamingResolver;

import com.google.inject.Binder;
import com.google.inject.Module;

public class ContextModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(LushNamingResolver.class).to(DefaultNamingResolver.class);
	}

}
