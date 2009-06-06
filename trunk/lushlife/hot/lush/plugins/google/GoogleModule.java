package lush.plugins.google;

import com.google.inject.Binder;
import com.google.inject.Module;

public class GoogleModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(GoogleConfigurator.class).asEagerSingleton();
	}

}
