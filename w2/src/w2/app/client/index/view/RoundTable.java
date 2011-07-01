package w2.app.client.index.view;

import java.util.Comparator;
import java.util.List;

import w2.app.client.index.activity.ListRoundActivity;
import w2.app.model.Round;

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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;

public class RoundTable extends Composite {

	private static RoundTableBinder uiBinder = GWT
			.create(RoundTableBinder.class);

	interface RoundTableBinder extends UiBinder<Widget, RoundTable> {
	}

	@UiField
	CellTable<Round> cellTable;
	@UiField
	SimplePager pager;

	@UiField
	Button delete;

	private Column<Round, String> nameColumn;
	private ListRoundActivity activity;
	private MultiSelectionModel<Round> selectionModel = new MultiSelectionModel<Round>(
			new ProvidesKey<Round>() {
				@Override
				public Object getKey(Round item) {
					return item.getId();
				}
			});

	private Column<Round, Boolean> checkColumn;

	private Column<Round, String> editButtonColumn;

	public RoundTable(ListRoundActivity activity) {
		initWidget(uiBinder.createAndBindUi(this));
		initTable();
		this.activity = activity;
	}

	public Widget initTable() {
		pager.setDisplay(cellTable);
		cellTable.setWidth("100%");
		// cellTable.setSelectionModel(selectionModel);
		checkColumn = new Column<Round, Boolean>(new CheckboxCell(true, false)) {
			public Boolean getValue(Round object) {
				return selectionModel.isSelected(object);
			}
		};
		checkColumn.setFieldUpdater(new FieldUpdater<Round, Boolean>() {
			@Override
			public void update(int index, Round object, Boolean value) {
				selectionModel.setSelected(object, value);
			}
		});
		cellTable.setColumnWidth(checkColumn, "5%");
		cellTable.addColumn(checkColumn, "");

		Column<Round, String> showButtonColumn = new Column<Round, String>(
				new ButtonCell()) {
			@Override
			public String getValue(Round contact) {
				return "表示";
			}

		};
		showButtonColumn.setFieldUpdater(new FieldUpdater<Round, String>() {
			@Override
			public void update(int index, Round object, String value) {
				activity.toShow(object);
			}
		});

		cellTable.addColumn(showButtonColumn, "Show");
		cellTable.setColumnWidth(showButtonColumn, "10%");

		// Create name column.
		nameColumn = new Column<Round, String>(new EditTextCell()) {
			@Override
			public String getValue(Round contact) {
				return contact.getName();
			}

		};
		nameColumn.setFieldUpdater(new FieldUpdater<Round, String>() {

			@Override
			public void update(int index, Round object, String value) {
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

		editButtonColumn = new Column<Round, String>(new ButtonCell()) {
			@Override
			public String getValue(Round contact) {
				return "編集";
			}

		};
		editButtonColumn.setFieldUpdater(new FieldUpdater<Round, String>() {
			@Override
			public void update(int index, Round object, String value) {
				activity.toEdit(object);
			}
		});

		cellTable.addColumn(editButtonColumn, "Action");
		cellTable.setColumnWidth(editButtonColumn, "5%");
		return cellTable;
	}

	public void loadData(List<Round> golfers) {
		ListDataProvider<Round> dataProvider = new ListDataProvider<Round>();
		dataProvider.addDataDisplay(cellTable);
		List<Round> list = dataProvider.getList();
		list.addAll(golfers);

		ListHandler<Round> columnSortHandler = new ListHandler<Round>(list);
		cellTable.addColumnSortHandler(columnSortHandler);
		columnSortHandler.setComparator(nameColumn, new Comparator<Round>() {
			public int compare(Round o1, Round o2) {
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
	}

	@UiHandler("delete")
	public void clickDelete(ClickEvent click) {
		activity.delete(selectionModel.getSelectedSet());
	}
}
