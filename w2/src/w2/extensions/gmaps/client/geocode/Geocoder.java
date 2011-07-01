package w2.extensions.gmaps.client.geocode;

import w2.extensions.gmaps.client.MVCObject;

public class Geocoder extends MVCObject {
	protected Geocoder() {
	}

	native public final void geocode(String address,
			final GeocodeEventFunction eventCallback)/*-{
		this
				.geocode(
						{
							'address' : address
						},
						function(results, status) {
							eventCallback.@w2.extensions.gmaps.client.geocode.GeocodeEventFunction::f(Lcom/google/gwt/core/client/JsArray;Ljava/lang/String;)(results,status);
						});
	}-*/;

	native static public final Geocoder create()/*-{
		return new $wnd.google.maps.Geocoder();
	}-*/;

}
