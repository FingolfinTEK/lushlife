package lushfile.plugins.view;

import lushfile.core.view.ErrorViewHandler;
import lushfile.core.view.Renderer;
import lushfile.core.view.ViewHandler;

import com.google.inject.Binder;
import com.google.inject.Module;

public class ViewModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(Renderer.class).to(GSPRenderer.class);
		binder.bind(ViewHandler.class).to(DefaultViewHandler.class);
		binder.bind(ErrorViewHandler.class).to(DefaultErrorViewHandler.class);
		binder.bind(ViewConfigurator.class).asEagerSingleton();
	}

}
