package lush.application.wiki;

import com.google.inject.Binder;
import com.google.inject.Module;

public class WikiModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(Initializer.class).asEagerSingleton();
	}

}
