package w2.extensions.gmaps.client.geocode;

import w2.extensions.gmaps.client.LatLng;
import w2.extensions.gmaps.client.LatLngBounds;
import w2.extensions.gmaps.client.MVCObject;

public class Geometry extends MVCObject {
	protected Geometry() {
	}

	native public final LatLng getLocation()/*-{
		return this.location();
	}-*/;

	native public final GeocoderLocationType getLocationType()/*-{
		return this.location_type;
	}-*/;

	native public final LatLngBounds getViewport()/*-{
		return this.viewport;
	}-*/;

	native public final LatLngBounds getBounds()/*-{
		return this.bounds;
	}-*/;

}
