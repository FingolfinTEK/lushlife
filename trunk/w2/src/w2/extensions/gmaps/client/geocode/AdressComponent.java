package w2.extensions.gmaps.client.geocode;

import com.google.gwt.core.client.JsArrayString;

import w2.extensions.gmaps.client.MVCObject;

public class AdressComponent extends MVCObject {
	protected AdressComponent() {
	}

	native final public JsArrayString getTypes()/*-{
		return this.types;
	}-*/;

	native final public String getShortName()/*-{
		return this.short_name;
	}-*/;

	native final public String getLongName()/*-{
		return this.long_name;
	}-*/;

}
