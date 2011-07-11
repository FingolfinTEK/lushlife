package ex.gmaps.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;

public class MVCObject extends Element {
	protected MVCObject() {
	}

	final public void addClickLitener(MouseEventFunction function) {
		addEventLatLng("click", function);
	}

	final protected native void addEventLatLng(String eventType,
			MouseEventFunction f)/*-{
		$wnd.google.maps.event
				.addListener(
						this,
						eventType,
						function(event) {
							f.@ex.gmaps.client.MouseEventFunction::f(Lex/gmaps/client/LatLng;)(event.latLng);
						});
	}-*/;

	final protected native void addEvent(String eventType, Function f)/*-{
		$wnd.google.maps.event.addListener(this, eventType, function() {
			f.@com.google.gwt.query.client.Function::f()();
		});
	}-*/;
}
