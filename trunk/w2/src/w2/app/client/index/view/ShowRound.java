package w2.app.client.index.view;

import java.util.ArrayList;
import java.util.List;

import w2.app.client.W2;
import w2.app.client.index.activity.ShowRoundActivity;
import w2.app.model.Course;
import w2.app.model.Golfer;
import w2.app.model.Round;
import w2.extensions.gmaps.client.GMap;
import w2.extensions.gmaps.client.Marker;
import w2.extensions.gmaps.client.geocode.GeocodeEventFunction;
import w2.extensions.gmaps.client.geocode.Geocoder;
import w2.extensions.gmaps.client.geocode.GeocoderResult;
import w2.extensions.gmaps.client.geocode.GeocoderStatus;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;

public class ShowRound extends Composite {

	private static EditCourseFormUiBinder uiBinder = GWT
			.create(EditCourseFormUiBinder.class);

	interface EditCourseFormUiBinder extends UiBinder<Widget, ShowRound> {
	}

	private MultiSelectionModel<Golfer> selectionModel = new MultiSelectionModel<Golfer>(
			new ProvidesKey<Golfer>() {
				@Override
				public Object getKey(Golfer item) {
					return item.getId();
				}
			});

	private ShowRoundActivity activity;

	private GMap map;

	private List<Course> courses;

	public ShowRound(ShowRoundActivity activity) {
		initWidget(uiBinder.createAndBindUi(this));
		this.activity = activity;
		init();
	}

	protected void bindMap(String name) {
		for (Course course : courses) {
			if (course.getName().equals(name)) {
				bindToMap(course.getAdress());
				break;
			}
		}
	}

	protected void init() {
		this.map = W2.getOrCreateMap(gmap);

		pager.setDisplay(cellTable);
		// cellTable.setSelectionModel(selectionModel);
		TextColumn<Golfer> checkColumn = new TextColumn<Golfer>() {
			public String getValue(Golfer object) {
				if (round.getDrivers().contains(object.getId())) {
					return "○";
				}
				return "";
			}
		};
		cellTable.setColumnWidth(checkColumn, "20px");
		cellTable.addColumn(checkColumn, " ドライバー");

		// Create name column.
		Column<Golfer, String> nameColumn = new Column<Golfer, String>(
				new TextCell()) {
			@Override
			public String getValue(Golfer contact) {
				return contact.getName();
			}

		};
		cellTable.addColumn(nameColumn, "Name");
		cellTable.getColumnSortList().push(nameColumn);
	}

	public void loadGolfers(List<Golfer> golfers) {
		ListDataProvider<Golfer> dataProvider = new ListDataProvider<Golfer>();
		dataProvider.addDataDisplay(cellTable);
		List<Golfer> list = dataProvider.getList();
		for (Golfer g : golfers) {
			if (round.getParticipators().contains(g.getId())) {
				list.add(g);
			}
		}
	}

	@UiField
	CellTable<Golfer> cellTable;

	@UiField
	SimplePager pager;

	@UiField
	Label name;

	@UiField
	Label course;

	@UiField
	DivElement description;

	@UiField
	Element gmap;

	@UiField
	Button edit;

	private Marker marker;

	private Round round;

	private void bindToMap(String nameValue) {
		Geocoder.create().geocode(nameValue, new GeocodeEventFunction() {
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

	public void setRound(Round round) {
		this.round = round;
		name.setText(round.getName());
		// description.setHTML(round.getInformation());
		description.setInnerHTML(round.getInformation());
		activity.findCourse(round.getCourse());
		edit.setVisible(true);
	}

	@UiHandler("edit")
	void onUpdateClick(ClickEvent e) {
		activity.edit(round);
	}

	public void setCourse(Course course) {
		this.course.setText(course.getName());
		bindToMap(course.getAdress());
	}

}
