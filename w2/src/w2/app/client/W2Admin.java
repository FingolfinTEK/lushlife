package w2.app.client;

import w2.app.client.admin.AdminActivityMapper;
import w2.app.client.place.ListGolferPlace;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.place.shared.Place;

public class W2Admin extends AbstractW2Entry implements EntryPoint {
	protected Place defaultPlace() {
		return new ListGolferPlace(0, 1000);
	}

	@Override
	protected AdminActivityMapper activityMapper() {
		AdminActivityMapper activityMapper = new AdminActivityMapper();
		return activityMapper;
	}
}
