package ex.gmaps.client;

public class Size extends MVCObject {
	protected Size() {
	}

	static public native Size create(int x, int y)/*-{
		return new $wnd.google.maps.Size(x, y);
	}-*/;

}
