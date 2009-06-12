package lush.plugins.jquery;

import com.google.inject.Binder;
import com.google.inject.Module;

public class JQueryModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(JQueryConfiguration.class).asEagerSingleton();
	}

}
