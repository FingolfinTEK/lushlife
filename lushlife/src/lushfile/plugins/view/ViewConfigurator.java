package lushfile.plugins.view;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lushfile.core.controller.LushController;
import lushfile.core.controller.LushControllerMetadata;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class ViewConfigurator {

	@Inject
	public void init(LushControllerMetadata metadata, Injector injector) {
		metadata.getPackageMapping().put(
				"Lush",
				injector.getInstance(LushController.class).init(
						this.getClass().getPackage()));
	}

	@Inject
	public void init(ViewUtilMetadata metadata) {
		Map<String, Class<?>> map = metadata.getBindingItems();
		map.put("request", HttpServletRequest.class);
		map.put("response", HttpServletResponse.class);
		map.put("session", HttpSession.class);
		map.put("context", ServletContext.class);
		map.put("application", ServletContext.class);
	}
}
