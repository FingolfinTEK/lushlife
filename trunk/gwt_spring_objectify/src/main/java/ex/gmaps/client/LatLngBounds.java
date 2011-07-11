package ex.gmaps.client;

public class LatLngBounds extends MVCObject {
	protected LatLngBounds() {
	}

	final native public boolean contains(LatLng latLng)/*-{
		return this.contains(latLng);
	}-*/;

	final native public boolean equals(LatLngBounds other)/*-{
		return this.equals(other);
	}-*/;

	final native public LatLngBounds extend(LatLng point)/*-{
		return this.extend(point);
	}-*/;

	final native public LatLng getCenter()/*-{
		return this.getCenter();
	}-*/;

	final native public LatLng getNorthEast()/*-{
		return this.getSouthWest();
	}-*/;

	final native public LatLng getSouthWest()/*-{
		return this.getSouthWest();
	}-*/;

	final native public boolean intersects(LatLngBounds other)/*-{
		return this.intersects(other);
	}-*/;

	final native public boolean isEmpty()/*-{
		return this.isEmpty();
	}-*/;

	final native public LatLng toSpan()/*-{
		return this.toSpan();
	}-*/;

}
