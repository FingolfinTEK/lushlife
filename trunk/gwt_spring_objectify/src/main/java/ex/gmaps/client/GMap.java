package ex.gmaps.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.query.client.Function;

public class GMap extends MVCObject {

	protected GMap() {
	}

	static native public GMap create(Element element, Options options)/*-{
		var m = new $wnd.google.maps.Map(element, options);
		return m;
	}-*/;

	static native public GMap create(String id, Options options)/*-{
		var m = new $wnd.google.maps.Map($wnd.document.getElementById(id),
				options);
		return m;
	}-*/;

	final native public void setCenter(LatLng center)/*-{
		this.setCenter(center);
	}-*/;

	final native public void setZoom(int zoom)/*-{
		this.setZoom(zoom);
	}-*/;

	final public void addZoomChangedLitener(Function function) {
		addEvent("zoom_changed", function);
	}

	final public native Marker createMarker(LatLng position, String title)/*-{
		var marker = new $wnd.google.maps.Marker({
			position : position,
			map : this,
			title : title
		});

		return marker;
	}-*/;

	native final public int getZoom()/*-{
		return this.getZoom();
	}-*/;

	static GMap map;

	static public final LatLng DEFAULT_POSITION = LatLng.create(35.443708,
			139.638026);

	static DivElement divElement;
	static {
		divElement = Document.get().createDivElement();
		divElement.getStyle().setWidth(500, Unit.PX);
		divElement.getStyle().setHeight(300, Unit.PX);

	}

	public static GMap getOrCreateMap(Element gmap) {
		return getOrCreateMap(gmap, DEFAULT_POSITION);
	}

	public static GMap getOrCreateMap(Element gmap, LatLng center) {
		// gmap.setId(new Date().toGMTString());
		if (map == null) {
			Options options = Options.create(9, center, MapTypeId.ROADMAP);
			map = GMap.create(divElement, options);
		} else {
			map.setCenter(center);
		}
		gmap.insertFirst(divElement);
		return map;
	}

}
