package w2.app.client;

import w2.app.client.index.IndexActivityMapper;
import w2.app.client.place.ListRoundPlace;

import com.google.gwt.place.shared.Place;

public class WaluWalu extends AbstractW2Entry {

	@Override
	protected Place defaultPlace() {
		return new ListRoundPlace();
	}

	@Override
	protected IndexActivityMapper activityMapper() {
		return new IndexActivityMapper();
	}

}
