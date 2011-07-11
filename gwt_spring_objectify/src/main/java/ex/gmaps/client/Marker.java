package ex.gmaps.client;

public class Marker extends MVCObject {
	protected Marker() {
	}

	final public native boolean getClickable()/*-{
		return this.getClickable();
	}-*/;

	final public native String getCursor()/*-{
		return this.getCursor();
	}-*/;

	final public native boolean getDraggable()/*-{
		return this.getDraggable();
	}-*/;

	final public native boolean getFlat()/*-{
		return this.getFlat();
	}-*/;

	final public native String getIcon()/*-{
		return this.getIcon();
	}-*/;

	final public native GMap getMap()/*-{
		return this.getMap();
	}-*/;

	final public native LatLng getPosition()/*-{
		return this.getPosition();
	}-*/;

	final public native String getShadow()/*-{
		return this.getShadow();
	}-*/;

	final public native MarkerShape getShape()/*-{
		return this.getShape();
	}-*/;

	final public native String getTitleGmap()/*-{
		return this.getTitle();
	}-*/;

	final public native boolean getVisible()/*-{
		return this.getVisible();
	}-*/;

	final public native int getZIndex()/*-{
		return this.getZIndex();
	}-*/;

	final public native void remove()/*-{
		this.setMap(null);
	}-*/;

}
