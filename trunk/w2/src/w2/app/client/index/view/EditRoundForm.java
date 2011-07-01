package w2.app.client.index.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import w2.app.client.W2;
import w2.app.client.index.activity.EditRoundActivity;
import w2.app.model.Course;
import w2.app.model.Golfer;
import w2.app.model.Round;
import w2.app.model.ref.CourseRef;
import w2.extensions.gmaps.client.GMap;
import w2.extensions.gmaps.client.Marker;
import w2.extensions.gmaps.client.geocode.GeocodeEventFunction;
import w2.extensions.gmaps.client.geocode.Geocoder;
import w2.extensions.gmaps.client.geocode.GeocoderResult;
import w2.extensions.gmaps.client.geocode.GeocoderStatus;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
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
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

public class EditRoundForm extends Composite {

	private static EditCourseFormUiBinder uiBinder = GWT
			.create(EditCourseFormUiBinder.class);

	interface EditCourseFormUiBinder extends UiBinder<Widget, EditRoundForm> {
	}

	private Set<Long> participators = new HashSet<Long>();
	private Set<Long> drivers = new HashSet<Long>();

	private EditRoundActivity activity;

	private GMap map;

	private List<Course> courses;

	public EditRoundForm(EditRoundActivity activity) {
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

	private void bindMap() {
		String name = course.getValue(course.getSelectedIndex());
		if (name != null) {
			bindMap(name);
		} else {
			clearMap();
		}
	}

	private void clearMap() {
		if (marker != null) {
			marker.remove();
		}
		if (map != null) {
			map.setCenter(W2.DEFAULT_POSITION);
		}
	}

	protected void init() {
		this.map = W2.getOrCreateMap(gmap);
		course.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				bindMap();
			}
		});

		pager.setDisplay(cellTable);
		// cellTable.setWidth("100%");

		Column<Golfer, Boolean> participate = new Column<Golfer, Boolean>(
				new CheckboxCell(true, false)) {
			public Boolean getValue(Golfer object) {
				return participators.contains(object.getId());
			}
		};
		participate.setFieldUpdater(new FieldUpdater<Golfer, Boolean>() {
			@Override
			public void update(int index, Golfer object, Boolean value) {
				if (value) {
					participators.add(object.getId());
				} else {
					participators.remove(object.getId());
				}
			}
		});
		cellTable.setColumnWidth(participate, "15%");
		cellTable.addColumn(participate, "参加");

		Column<Golfer, Boolean> driver = new Column<Golfer, Boolean>(
				new CheckboxCell(true, false)) {
			public Boolean getValue(Golfer object) {
				return drivers.contains(object.getId());
			}
		};
		driver.setFieldUpdater(new FieldUpdater<Golfer, Boolean>() {
			@Override
			public void update(int index, Golfer object, Boolean value) {
				if (value) {
					drivers.add(object.getId());
				} else {
					drivers.remove(object.getId());
				}
			}
		});
		cellTable.setColumnWidth(driver, "20%");
		cellTable.addColumn(driver, "ドライバ");

		TextColumn<Golfer> idColumn = new TextColumn<Golfer>() {
			@Override
			public String getValue(Golfer contact) {
				return contact.getId() + "";
			}
		};
		idColumn.setSortable(true);
		cellTable.setColumnWidth(idColumn, "5%");
		cellTable.addColumn(idColumn, "ID");
		cellTable.getColumnSortList().push(idColumn);

		// Create name column.
		Column<Golfer, String> nameColumn = new Column<Golfer, String>(
				new TextCell()) {
			@Override
			public String getValue(Golfer contact) {
				return contact.getName();
			}

		};
		cellTable.addColumn(nameColumn, "名前");
		cellTable.getColumnSortList().push(nameColumn);
	}

	public void loadGolfers(List<Golfer> golfers) {
		ListDataProvider<Golfer> dataProvider = new ListDataProvider<Golfer>();
		dataProvider.addDataDisplay(cellTable);
		List<Golfer> list = dataProvider.getList();
		list.addAll(golfers);
	}

	public void loadCourses(List<Course> courses) {
		this.courses = courses;
		this.course.addItem("");
		for (Course course : courses) {
			this.course.addItem(course.getName());
		}
	}

	@UiField
	CellTable<Golfer> cellTable;

	@UiField
	SimplePager pager;

	@UiField
	Label id;

	@UiField
	TextBox name;

	@UiField
	ListBox course;

	@UiField
	TextArea description;

	@UiField
	Element gmap;

	DisclosurePanel panel;

	@UiField
	Button add;
	@UiField
	Button update;
	@UiField
	Button delete;

	private Marker marker;

	private Round round;

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

	public void setCourse(Round course) {
		this.round = course;
		this.id.setText(course.getId() + "");
		this.name.setText(course.getName());
		this.course.setSelectedIndex(selectIndex(course));
		this.drivers = course.getDrivers();
		this.participators = course.getParticipators();

		add.setVisible(false);
		update.setVisible(true);
		delete.setVisible(true);
		bindMap();

	}

	private int selectIndex(Round c) {
		if (c.getCourse() == null) {
			return 0;
		}
		int i = 0;
		for (Course course : courses) {
			i++;
			if (course.getId().equals(c.getCourse().getRefId())) {
				return i;
			}
		}
		return 0;
	}

	@UiHandler("add")
	void onAddClick(ClickEvent e) {
		this.round = new Round();
		applyValuesToRound();
		activity.add(round);
	}

	private void applyValuesToRound() {
		round.setCourse(findCourse(course.getValue(course.getSelectedIndex())));
		round.setName(name.getText());
		round.setInformation(description.getText());
		round.setParticipators(participators);
		round.setDrivers(drivers);

	}

	private CourseRef findCourse(String name) {
		for (Course course : courses) {
			if (course.getName().equals(name)) {
				return new CourseRef(course.getId());
			}
		}
		return null;
	}

	@UiHandler("update")
	void onUpdateClick(ClickEvent e) {
		applyValuesToRound();
		activity.update(round);
	}

	@UiHandler("delete")
	void onDeleteClick(ClickEvent e) {
		activity.delete(round);
	}

}
