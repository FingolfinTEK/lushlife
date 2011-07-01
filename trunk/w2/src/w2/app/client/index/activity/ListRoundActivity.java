package w2.app.client.index.activity;

import java.util.List;

import w2.app.client.W2;
import w2.app.client.index.view.RoundTable;
import w2.app.client.place.EditRoundPlace;
import w2.app.client.place.ListRoundPlace;
import w2.app.client.place.ShowRoundPlace;
import w2.app.client.rcp.RoundRcp;
import w2.app.client.rcp.RoundRcpAsync;
import w2.app.model.Round;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ListRoundActivity extends AbstractActivity {
	private RoundRcpAsync courseRcp = GWT.create(RoundRcp.class);
	private AcceptsOneWidget panel;
	private EventBus eventBus;
	int start;
	int length;
	private RoundTable table;

	public ListRoundActivity(int start, int length) {
		this.start = start;
		this.length = length;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		this.panel = panel;
		this.eventBus = eventBus;
		this.table = new RoundTable(this);
		panel.setWidget(table);
		find();
	}

	private void find() {
		courseRcp.findAll(start, length, new AsyncCallback<List<Round>>() {

			@Override
			public void onSuccess(List<Round> result) {
				table.loadData(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("onFailure", caught);
			}
		});
	}

	public void update(final Round course) {
		courseRcp.update(course, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				W2.removeMessage();
				W2.showMessage("Round " + course.getId() + "を更新しました。");
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("onFailure", caught);
			}
		});
	}

	public void toEdit(Round object) {
		eventBus.fireEvent(new PlaceChangeEvent(new EditRoundPlace(object
				.getId())));
	}

	public void delete(Iterable<Round> selectedSet) {
		courseRcp.delete(selectedSet, new AsyncCallback<List<Long>>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("onFailure", caught);
			}

			@Override
			public void onSuccess(List<Long> result) {
				W2.removeMessage();
				W2.showMessage("Round " + result + " を削除しました。");
				eventBus.fireEvent(new PlaceChangeEvent(new ListRoundPlace()));
			}
		});
	}

	public void toShow(Round object) {
		eventBus.fireEvent(new PlaceChangeEvent(new ShowRoundPlace(object
				.getId())));
	}

}
