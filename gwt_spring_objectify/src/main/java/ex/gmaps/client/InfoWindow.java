package ex.gmaps.client;

public class InfoWindow extends MVCObject {
	protected InfoWindow() {
	}

	static public native InfoWindow create(String content, Size size)/*-{
		return new $wnd.google.maps.InfoWindow({
			content : content,
			size : size
		});
	}-*/;

	final public native void open(GMap maps, Marker marker)/*-{
		this.open(maps, marker);
	}-*/;

}
