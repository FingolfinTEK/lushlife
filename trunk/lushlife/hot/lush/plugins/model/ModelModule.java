package lush.plugins.model;

import lush.plugins.model.converter.LongConverter;
import lush.plugins.model.converter.StringConverter;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Module;

public class ModelModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(ModuleConfiguration.class).asEagerSingleton();
	}

	static public class ModuleConfiguration {

		@Inject
		public void init(ConverterMetadata meta) {
			meta.addConverter(String.class, StringConverter.class);
			meta.addConverter(Long.class, LongConverter.class);
			meta.addConverter(long.class, LongConverter.class);
		}

	}

}
