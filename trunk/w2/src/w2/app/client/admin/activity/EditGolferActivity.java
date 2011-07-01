package w2.app.client.admin.activity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import w2.app.client.W2;
import w2.app.client.admin.view.EditGolferForm;
import w2.app.client.place.EditGolferPlace;
import w2.app.client.place.ListGolferPlace;
import w2.app.client.rcp.GolferRcp;
import w2.app.client.rcp.GolferRcpAsync;
import w2.app.model.Golfer;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class EditGolferActivity extends AbstractActivity {
	private GolferRcpAsync golferRcp = GWT.create(GolferRcp.class);
	Long userId;
	private EventBus eventBus;
	private AcceptsOneWidget panel;
	private EditGolferForm form;

	public EditGolferActivity(Long userId) {
		this.userId = userId;
	}

	public void add(final Golfer golfer) {
		golferRcp.addGolfer(golfer, new AsyncCallback<Long>() {

			@Override
			public void onSuccess(Long result) {
				eventBus.fireEvent(new PlaceChangeEvent(new ListGolferPlace()));
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("failed", caught);
			}
		});
	}

	public void find(Long id) {
		golferRcp.findById(id, new AsyncCallback<Golfer>() {

			@Override
			public void onSuccess(Golfer result) {
				form.setGolfer(result);
				W2.showMessage("Golfer " + result.getId() + "を検索しました。");
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("failed", caught);
			}

		});
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		this.eventBus = eventBus;
		this.panel = panel;
		this.form = new EditGolferForm(this);
		panel.setWidget(form);
		if (userId != null) {
			find(userId);
		}
	}

	public void delete(Golfer golfer) {
		golferRcp.delete(new HashSet<Golfer>(Arrays.asList(golfer)),
				new AsyncCallback<List<Long>>() {
					@Override
					public void onSuccess(List<Long> result) {
						W2.removeMessage();
						W2.showMessage("Golfer " + result + " を削除しました。");
						eventBus.fireEvent(new PlaceChangeEvent(
								new ListGolferPlace()));
					}

					@Override
					public void onFailure(Throwable caught) {
						GWT.log("onFailure", caught);
					}
				});
	}

	public void update(final Golfer golfer) {
		golferRcp.update(golfer, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				W2.removeMessage();
				W2.showMessage("Golfer " + golfer.getId() + " を更新しました。");
				eventBus.fireEvent(new PlaceChangeEvent(new ListGolferPlace()));
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("onFailure", caught);
			}
		});
	}
}
