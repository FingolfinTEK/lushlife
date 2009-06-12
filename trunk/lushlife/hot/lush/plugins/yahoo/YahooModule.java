package lush.plugins.yahoo;

import com.google.inject.Binder;
import com.google.inject.Module;

public class YahooModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(YahooConfigurator.class).asEagerSingleton();
	}

}
