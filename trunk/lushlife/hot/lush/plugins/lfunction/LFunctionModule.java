package lush.plugins.lfunction;

import com.google.inject.Binder;
import com.google.inject.Module;

public class LFunctionModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(LFunctionConfigurator.class).asEagerSingleton();

	}

}
