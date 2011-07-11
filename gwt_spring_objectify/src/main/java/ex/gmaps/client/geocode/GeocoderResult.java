package ex.gmaps.client.geocode;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;

import ex.gmaps.client.MVCObject;

public class GeocoderResult extends MVCObject {
	protected GeocoderResult() {
	}

	native final public JsArrayString getTypes()/*-{
		return this.types;
	}-*/;

	native final public String getFormattedAddress()/*-{
		return this.formatted_address;
	}-*/;

	native final public JsArray<AdressComponent> getAddressComponents()/*-{
		return this.address_components;
	}-*/;

	native final public Geometry getGeometry()/*-{
		return this.geometry;
	}-*/;

}
