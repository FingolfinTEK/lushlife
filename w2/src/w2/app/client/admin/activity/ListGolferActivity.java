package w2.app.client.admin.activity;

import java.util.List;
import java.util.Set;

import w2.app.client.W2;
import w2.app.client.admin.view.GolferTable;
import w2.app.client.place.EditGolferPlace;
import w2.app.client.rcp.GolferRcp;
import w2.app.client.rcp.GolferRcpAsync;
import w2.app.model.Golfer;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ListGolferActivity extends AbstractActivity {
	private int start;
	private int length;
	private AcceptsOneWidget panel;
	private EventBus eventBus;
	GolferTable table;

	GolferRcpAsync rcp = GWT.create(GolferRcp.class);

	public ListGolferActivity(int start, int length) {
		this.start = start;
		this.length = length;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		this.panel = panel;
		this.eventBus = eventBus;
		table = new GolferTable(this);
		panel.setWidget(table);

		find();
	}

	private void find() {
		rcp.findAll(start, length, new AsyncCallback<List<Golfer>>() {

			public void onSuccess(List<Golfer> result) {
				table.loadData(result);
			}

			public void onFailure(Throwable caught) {
				GWT.log("onFailure", caught);
			}
		});
	}

	public void update(final Golfer object) {
		rcp.update(object, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				W2.removeMessage();
				W2.showMessage("Golfer " + object.getId() + "を更新しました。");
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("onFailure", caught);
			}
		});
	}

	public void delete(Set<Golfer> selectedSet) {
		rcp.delete(selectedSet, new AsyncCallback<List<Long>>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("onFailure", caught);
			}

			@Override
			public void onSuccess(List<Long> result) {
				W2.removeMessage();
				W2.showMessage("Golfer " + result + "を削除しました。");
				find();
			}
		});
	}

	public void toEdit(Golfer object) {
		eventBus.fireEvent(new PlaceChangeEvent(new EditGolferPlace(object
				.getId())));
	}
}
