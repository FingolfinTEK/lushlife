package w2.app.client;

import java.util.ArrayList;
import java.util.List;

import w2.extensions.gmaps.client.GMap;
import w2.extensions.gmaps.client.LatLng;
import w2.extensions.gmaps.client.MapTypeId;
import w2.extensions.gmaps.client.Marker;
import w2.extensions.gmaps.client.Options;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class W2 {

	static RootPanel panel = RootPanel.get("message");
	static List<LabelAndButton> list = new ArrayList<W2.LabelAndButton>();

	static public class LabelAndButton {
		String message;
		private Label label;
		private Button button;

		public LabelAndButton(String message) {
			this.message = message;
		}

		public void show() {
			list.add(this);
			label = new Label();
			label.setText(message);
			panel.add(label);
			button = new Button();
			button.setStyleName("w2-Button");
			button.setText("×");
			button.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					remove();
				}
			});
			panel.add(label);
			panel.add(button);
		}

		public void remove() {
			panel.remove(label);
			panel.remove(button);
		}
	}

	static public void removeMessage() {
		for (LabelAndButton lb : list) {
			lb.remove();
		}
	}

	static public void showMessage(String message) {
		new LabelAndButton(message).show();
	}

	static DivElement divElement;
	static {
		divElement = Document.get().createDivElement();
		divElement.getStyle().setWidth(500, Unit.PX);
		divElement.getStyle().setHeight(300, Unit.PX);

	}
	static GMap map;

	static public final LatLng DEFAULT_POSITION = LatLng.create(35.443708,
			139.638026);
	static private Options options = Options.create(9, DEFAULT_POSITION,
			MapTypeId.ROADMAP);

	static private List<Marker> markers = new ArrayList<Marker>();

	public static Marker createMarker(GMap gmap, LatLng position, String title) {
		Marker marker = gmap.createMarker(position, title);
		markers.add(marker);
		return marker;
	}

	public static GMap getOrCreateMap(Element gmap) {
		// gmap.setId(new Date().toGMTString());
		if (map == null) {
			map = GMap.create(divElement, options);
		}
		for (Marker marker : markers) {
			marker.remove();
		}
		markers.clear();
		gmap.insertFirst(divElement);
		return map;
	}
}
