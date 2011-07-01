package w2.app.client;

import w2.app.client.place.AppPlaceHistoryMapper;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public abstract class AbstractW2Entry {

	public void onModuleLoad() {
		GWT.runAsync(new RunAsyncCallback() {

			@Override
			public void onSuccess() {

				SimplePanel appWidget = new SimplePanel();
				EventBus eventBus = new SimpleEventBus();
				PlaceController placeController = new PlaceController(eventBus);
				ActivityMapper activityMapper = activityMapper();

				// Start ActivityManager for the main widget with our
				// ActivityMapper
				ActivityManager activityManager = new ActivityManager(
						activityMapper, eventBus);
				activityManager.setDisplay(appWidget);

				// Start PlaceHistoryHandler with our PlaceHistoryMapper
				AppPlaceHistoryMapper historyMapper = GWT
						.create(AppPlaceHistoryMapper.class);
				PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(
						historyMapper);

				historyHandler.register(placeController, eventBus,
						defaultPlace());

				RootPanel.get("contents").add(appWidget);
				// Goes to the place represented on URL else default place
				historyHandler.handleCurrentHistory();
			}

			@Override
			public void onFailure(Throwable reason) {
				GWT.log("failure", reason);
			}
		});

	}

	abstract protected ActivityMapper activityMapper();

	abstract protected Place defaultPlace();

}