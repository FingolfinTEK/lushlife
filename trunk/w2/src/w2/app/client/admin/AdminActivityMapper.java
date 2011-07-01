package w2.app.client.admin;

import w2.app.client.W2;
import w2.app.client.admin.activity.EditGolferActivity;
import w2.app.client.admin.activity.ListGolferActivity;
import w2.app.client.place.EditGolferPlace;
import w2.app.client.place.ListGolferPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class AdminActivityMapper implements ActivityMapper {

	public AdminActivityMapper() {
	}

	@Override
	public Activity getActivity(Place place) {
		W2.removeMessage();
		if (place instanceof EditGolferPlace) {
			return new EditGolferActivity(((EditGolferPlace) place).getUserId());
		} else if (place instanceof ListGolferPlace) {
			ListGolferPlace listUserPlace = (ListGolferPlace) place;
			return new ListGolferActivity(listUserPlace.getStart(),
					listUserPlace.getLength());
		}
		return null;
	}
}