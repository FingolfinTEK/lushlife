package w2.app.client.index.view;

import java.util.Comparator;
import java.util.List;

import w2.app.client.index.activity.ListCourseActivity;
import w2.app.model.Course;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;

public class CourseTable extends Composite {

	private static GolferTableBinder uiBinder = GWT
			.create(GolferTableBinder.class);

	interface GolferTableBinder extends UiBinder<Widget, CourseTable> {
	}

	@UiField
	CellTable<Course> cellTable;
	@UiField
	SimplePager pager;

	@UiField
	Button delete;

	private Column<Course, String> nameColumn;
	private Column<Course, String> addressColumn;
	private ListCourseActivity activity;
	private MultiSelectionModel<Course> selectionModel = new MultiSelectionModel<Course>(
			new ProvidesKey<Course>() {
				@Override
				public Object getKey(Course item) {
					return item.getId();
				}
			});

	private Column<Course, Boolean> checkColumn;

	private Column<Course, String> buttonColumn;

	public CourseTable(ListCourseActivity activity) {
		initWidget(uiBinder.createAndBindUi(this));
		initTable();
		this.activity = activity;
	}

	public Widget initTable() {
		pager.setDisplay(cellTable);
		cellTable.setWidth("100%");
		// cellTable.setSelectionModel(selectionModel);
		checkColumn = new Column<Course, Boolean>(new CheckboxCell(true, false)) {
			public Boolean getValue(Course object) {
				return selectionModel.isSelected(object);
			}
		};
		checkColumn.setFieldUpdater(new FieldUpdater<Course, Boolean>() {
			@Override
			public void update(int index, Course object, Boolean value) {
				selectionModel.setSelected(object, value);
			}
		});
		cellTable.setColumnWidth(checkColumn, "5%");
		cellTable.addColumn(checkColumn, "");

		// Create name column.
		nameColumn = new Column<Course, String>(new EditTextCell()) {
			@Override
			public String getValue(Course contact) {
				return contact.getName();
			}

		};
		nameColumn.setFieldUpdater(new FieldUpdater<Course, String>() {

			@Override
			public void update(int index, Course object, String value) {
				if (value.equals(object.getName())) {
					return;
				}
				object.setName(value);
				activity.update(object);
				cellTable.redraw();
			}
		});
		nameColumn.setSortable(true);
		cellTable.addColumn(nameColumn, "Name");
		cellTable.getColumnSortList().push(nameColumn);

		// Create address column.
		addressColumn = new Column<Course, String>(new EditTextCell()) {
			@Override
			public String getValue(Course contact) {
				return contact.getAdress();
			}
		};
		addressColumn.setFieldUpdater(new FieldUpdater<Course, String>() {

			@Override
			public void update(int index, Course object, String value) {
				if (value.equals(object.getAdress())) {
					return;
				}
				object.setAdress(value);
				activity.update(object);
				cellTable.redraw();
			}
		});
		addressColumn.setSortable(true);
		cellTable.addColumn(addressColumn, "Address");
		cellTable.getColumnSortList().push(addressColumn);

		buttonColumn = new Column<Course, String>(new ButtonCell()) {
			@Override
			public String getValue(Course contact) {
				return "Edit";
			}

		};
		buttonColumn.setFieldUpdater(new FieldUpdater<Course, String>() {
			@Override
			public void update(int index, Course object, String value) {
				activity.toEdit(object);
			}
		});

		cellTable.addColumn(buttonColumn, "Action");
		cellTable.setColumnWidth(buttonColumn, "5%");
		return cellTable;
	}

	public void loadData(List<Course> golfers) {
		ListDataProvider<Course> dataProvider = new ListDataProvider<Course>();
		dataProvider.addDataDisplay(cellTable);
		List<Course> list = dataProvider.getList();
		list.addAll(golfers);

		ListHandler<Course> columnSortHandler = new ListHandler<Course>(list);
		cellTable.addColumnSortHandler(columnSortHandler);
		columnSortHandler.setComparator(nameColumn, new Comparator<Course>() {
			public int compare(Course o1, Course o2) {
				if (o1 == o2) {
					return 0;
				}

				// Compare the name columns.
				if (o1 != null) {
					return (o2 != null) ? o1.getName().compareTo(o2.getName())
							: 1;
				}
				return -1;
			}
		});
		columnSortHandler.setComparator(addressColumn,
				new Comparator<Course>() {
					public int compare(Course o1, Course o2) {
						if (o1 == o2) {
							return 0;
						}

						// Compare the name columns.
						if (o1 != null) {
							return (o2 != null) ? o1.getAdress().compareTo(
									o2.getAdress()) : 1;
						}
						return -1;
					}
				});
	}

	@UiHandler("delete")
	public void clickDelete(ClickEvent click) {
		activity.delete(selectionModel.getSelectedSet());
	}
}
