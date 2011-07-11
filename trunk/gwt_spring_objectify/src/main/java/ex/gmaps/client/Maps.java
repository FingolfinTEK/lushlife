package ex.gmaps.client;

import com.google.gwt.core.client.JavaScriptObject;

public class Maps {

	static native public <T extends JavaScriptObject> T attr(
			JavaScriptObject element, String attr) /*-{
		return element[attr];
	}-*/;

}
