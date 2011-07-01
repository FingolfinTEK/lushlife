package w2.app.client.index.view;

import java.util.ArrayList;
import java.util.List;

import w2.app.client.W2;
import w2.app.client.index.activity.EditCourseActivity;
import w2.app.model.Course;
import w2.app.model.SLatLng;
import w2.extensions.gmaps.client.GMap;
import w2.extensions.gmaps.client.Marker;
import w2.extensions.gmaps.client.geocode.GeocodeEventFunction;
import w2.extensions.gmaps.client.geocode.Geocoder;
import w2.extensions.gmaps.client.geocode.GeocoderResult;
import w2.extensions.gmaps.client.geocode.GeocoderStatus;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditCourseForm extends Composite {

	private static EditCourseFormUiBinder uiBinder = GWT
			.create(EditCourseFormUiBinder.class);

	interface EditCourseFormUiBinder extends UiBinder<Widget, EditCourseForm> {
	}

	private EditCourseActivity activity;

	private GMap map;

	public EditCourseForm(EditCourseActivity activity) {
		initWidget(uiBinder.createAndBindUi(this));
		this.activity = activity;
		init();
	}

	protected void init() {
		this.map = W2.getOrCreateMap(gmap);
	}

	@UiField
	Label id;

	@UiField
	TextBox name;
	@UiField
	TextBox address;

	@UiField
	TextArea description;

	@UiField
	Element gmap;

	@UiField
	Button add;
	@UiField
	Button update;
	@UiField
	Button delete;

	private Course course;
	private Marker marker;

	@UiHandler("address")
	void onBindWithAdress(BlurEvent e) {
		String nameValue = address.getText();
		if (nameValue == null || nameValue.isEmpty()) {
			W2.removeMessage();
			W2.showMessage("Adressは入力されておりません。");
			return;
		}
		bindToMap(nameValue);
	}

	private void bindToMap(String nameValue) {
		Geocoder coder = Geocoder.create();
		coder.geocode(nameValue, new GeocodeEventFunction() {
			protected void f(JsArray<GeocoderResult> results, String status) {
				if (status.equals(GeocoderStatus.OK)) {
					W2.removeMessage();
					List<String> addresses = new ArrayList<String>();
					for (int i = 0; i < results.length(); i++) {
						addresses.add(results.get(i).getFormattedAddress());
					}
					W2.showMessage(results.length() + " 件" + addresses
							+ "の結果を取得しました。");
					GeocoderResult geocoderResult = results.get(0);
					if (marker != null) {
						marker.remove();
					}
					marker = W2.createMarker(map, geocoderResult.getGeometry()
							.getViewport().getCenter(),
							geocoderResult.getFormattedAddress());
					map.setCenter(geocoderResult.getGeometry().getViewport()
							.getCenter());
				} else {
					W2.removeMessage();
					W2.showMessage("ジオコードの取得に失敗しました。[reason=" + status + "];");
				}
			}
		});
	}

	public void setCourse(Course course) {
		this.course = course;
		this.name.setText(course.getName());
		this.address.setText(course.getAdress());
		if (course.getLatLng() != null) {
			int zoom = course.getLatLng().getZoom();
			map.setZoom(zoom);
			String adress = course.getAdress();
			if (adress != null) {
				bindToMap(adress);
			}
		}
		add.setVisible(false);
		update.setVisible(true);
		delete.setVisible(true);

	}

	@UiHandler("add")
	void onAddClick(ClickEvent e) {
		this.course = new Course();
		setCourse();
		activity.add(course);
	}

	private void setCourse() {
		course.setAdress(address.getText());
		course.setName(name.getText());
		SLatLng latlng = new SLatLng();
		course.setLatLng(latlng);
		latlng.setZoom(map.getZoom());
	}

	@UiHandler("update")
	void onUpdateClick(ClickEvent e) {
		setCourse();
		activity.update(course);
	}

	@UiHandler("delete")
	void onDeleteClick(ClickEvent e) {
		activity.delete(course);
	}

}
