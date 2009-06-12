package lush.plugins.maven;

import com.google.inject.Binder;
import com.google.inject.Module;

public class MavenModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(MavenConfigurator.class).asEagerSingleton();
	}

}
