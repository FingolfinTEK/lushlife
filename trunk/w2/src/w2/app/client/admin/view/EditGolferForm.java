package w2.app.client.admin.view;

import java.util.ArrayList;
import java.util.List;

import w2.app.client.W2;
import w2.app.client.admin.activity.EditGolferActivity;
import w2.app.model.Golfer;
import w2.extensions.gmaps.client.GMap;
import w2.extensions.gmaps.client.Marker;
import w2.extensions.gmaps.client.geocode.GeocodeEventFunction;
import w2.extensions.gmaps.client.geocode.Geocoder;
import w2.extensions.gmaps.client.geocode.GeocoderResult;
import w2.extensions.gmaps.client.geocode.GeocoderStatus;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditGolferForm extends Composite {

	private static EditGolferFormUiBinder uiBinder = GWT
			.create(EditGolferFormUiBinder.class);

	interface EditGolferFormUiBinder extends UiBinder<Widget, EditGolferForm> {
	}

	EditGolferActivity presenter;

	private GMap map;

	private Marker marker;

	public EditGolferForm(EditGolferActivity presenter) {
		initWidget(uiBinder.createAndBindUi(this));
		this.presenter = presenter;
		this.map = W2.getOrCreateMap(gmap);
		this.adress.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				bindMap();
			}
		});
	}

	private void bindMap() {
		String a = adress.getText();
		if (a != null) {
			Geocoder.create().geocode(a, new GeocodeEventFunction() {
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
						marker = W2.createMarker(map, geocoderResult
								.getGeometry().getViewport().getCenter(),
								geocoderResult.getFormattedAddress());
						map.setCenter(geocoderResult.getGeometry()
								.getViewport().getCenter());
					} else {
						W2.removeMessage();
						W2.showMessage("ジオコードの取得に失敗しました。[reason=" + status
								+ "];");
					}
				}
			});
		}
	}

	@UiField
	Label id;

	@UiField
	TextBox name;

	@UiField
	TextBox email;

	@UiField
	TextBox adress;

	@UiField
	DivElement gmap;

	@UiField
	Button add;

	@UiField
	Button delete;

	@UiField
	Button update;

	private Golfer golfer;

	@UiHandler("delete")
	void onDeleteClick(ClickEvent e) {
		presenter.delete(golfer);
	}

	@UiHandler("update")
	void onUpdateClick(ClickEvent e) {
		golfer.setName(name.getText());
		golfer.setMailAdress(email.getText());
		golfer.setAdress(adress.getText());
		presenter.update(golfer);
	}

	@UiHandler("add")
	void onAddClick(ClickEvent e) {
		if (golfer == null) {
			golfer = new Golfer();
		}
		golfer.setName(name.getText());
		golfer.setMailAdress(email.getText());
		golfer.setAdress(adress.getText());
		presenter.add(golfer);
	}

	public void setGolfer(Golfer golfer) {
		this.add.setVisible(false);
		this.delete.setVisible(true);
		this.update.setVisible(true);

		this.golfer = golfer;
		id.setText(golfer.getId() + "");
		name.setText(golfer.getName());
		email.setText(golfer.getMailAdress());
		adress.setText(golfer.getAdress());
		bindMap();
	}

}
