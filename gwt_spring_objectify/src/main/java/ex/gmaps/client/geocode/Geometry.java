package ex.gmaps.client.geocode;

import ex.gmaps.client.LatLng;
import ex.gmaps.client.LatLngBounds;
import ex.gmaps.client.MVCObject;

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
