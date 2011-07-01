package w2.app.client.admin.view;

import java.util.Comparator;
import java.util.List;

import w2.app.client.admin.activity.ListGolferActivity;
import w2.app.model.Golfer;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;

public class GolferTable extends Composite {

	private static GolferTableBinder uiBinder = GWT
			.create(GolferTableBinder.class);

	interface GolferTableBinder extends UiBinder<Widget, GolferTable> {
	}

	@UiField
	CellTable<Golfer> cellTable;
	@UiField
	SimplePager pager;

	@UiField
	Button delete;

	private Column<Golfer, String> nameColumn;
	private Column<Golfer, String> addressColumn;
	private ListGolferActivity activity;
	private MultiSelectionModel<Golfer> selectionModel = new MultiSelectionModel<Golfer>(
			new ProvidesKey<Golfer>() {
				@Override
				public Object getKey(Golfer item) {
					return item.getId();
				}
			});

	private Column<Golfer, Boolean> checkColumn;

	private Column<Golfer, String> buttonColumn;

	public GolferTable(ListGolferActivity activity) {
		initWidget(uiBinder.createAndBindUi(this));
		initTable();
		this.activity = activity;
	}

	public Widget initTable() {
		pager.setDisplay(cellTable);
		cellTable.setWidth("100%");
		// cellTable.setSelectionModel(selectionModel);
		checkColumn = new Column<Golfer, Boolean>(new CheckboxCell(true, false)) {
			public Boolean getValue(Golfer object) {
				return selectionModel.isSelected(object);
			}
		};
		checkColumn.setFieldUpdater(new FieldUpdater<Golfer, Boolean>() {
			@Override
			public void update(int index, Golfer object, Boolean value) {
				selectionModel.setSelected(object, value);
			}
		});
		cellTable.setColumnWidth(checkColumn, "5%");
		cellTable.addColumn(checkColumn, "");
		
		// Create name column.
		nameColumn = new Column<Golfer, String>(new EditTextCell()) {
			@Override
			public String getValue(Golfer contact) {
				return contact.getName();
			}

		};
		nameColumn.setFieldUpdater(new FieldUpdater<Golfer, String>() {

			@Override
			public void update(int index, Golfer object, String value) {
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
		addressColumn = new Column<Golfer, String>(new EditTextCell()) {
			@Override
			public String getValue(Golfer contact) {
				return contact.getMailAdress();
			}
		};
		addressColumn.setFieldUpdater(new FieldUpdater<Golfer, String>() {

			@Override
			public void update(int index, Golfer object, String value) {
				if (value.equals(object.getMailAdress())) {
					return;
				}
				object.setMailAdress(value);
				activity.update(object);
				cellTable.redraw();
			}
		});
		addressColumn.setSortable(true);
		cellTable.addColumn(addressColumn, "Address");
		cellTable.getColumnSortList().push(addressColumn);

		buttonColumn = new Column<Golfer, String>(new ButtonCell()) {
			@Override
			public String getValue(Golfer contact) {
				return "Edit";
			}

		};
		buttonColumn.setFieldUpdater(new FieldUpdater<Golfer, String>() {
			@Override
			public void update(int index, Golfer object, String value) {
				activity.toEdit(object);
			}
		});

		cellTable.addColumn(buttonColumn, "Action");
		cellTable.setColumnWidth(buttonColumn, "5%");
		return cellTable;
	}

	public void loadData(List<Golfer> golfers) {
		ListDataProvider<Golfer> dataProvider = new ListDataProvider<Golfer>();
		dataProvider.addDataDisplay(cellTable);
		List<Golfer> list = dataProvider.getList();
		list.addAll(golfers);

		ListHandler<Golfer> columnSortHandler = new ListHandler<Golfer>(list);
		cellTable.addColumnSortHandler(columnSortHandler);
		columnSortHandler.setComparator(nameColumn, new Comparator<Golfer>() {
			public int compare(Golfer o1, Golfer o2) {
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
				new Comparator<Golfer>() {
					public int compare(Golfer o1, Golfer o2) {
						if (o1 == o2) {
							return 0;
						}

						// Compare the name columns.
						if (o1 != null) {
							return (o2 != null) ? o1.getMailAdress().compareTo(
									o2.getMailAdress()) : 1;
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
