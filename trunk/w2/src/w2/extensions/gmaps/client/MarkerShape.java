package w2.extensions.gmaps.client;

import com.google.gwt.core.client.JsArrayNumber;

public class MarkerShape extends MVCObject {
	protected MarkerShape() {
	}

	final public native JsArrayNumber getCoord()/*-{
		return this.coord;
	}-*/;

	final public native void setCoord(JsArrayNumber numbers)/*-{
		this.coord = numbers;
	}-*/;

	final public native String getType()/*-{
		return this.type;
	}-*/;

	final public native void setType(String type)/*-{
		this.type = type;
	}-*/;

}
