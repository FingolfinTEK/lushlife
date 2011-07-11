package ex.gmaps.client;

import com.google.gwt.dom.client.Element;

public class Options extends Element {
	protected Options() {
	}

	static public native Options create(int zoom, LatLng center,
			String mapTypeId)/*-{
		return {
			"zoom" : zoom,
			"center" : center,
			"mapTypeId" : mapTypeId
		};
	}-*/;

}
