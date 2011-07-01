package w2.extensions.gmaps.client.geocode;

import com.google.gwt.core.client.JsArray;

public abstract class GeocodeEventFunction {

	protected abstract void f(JsArray<GeocoderResult> results, String status);
}
