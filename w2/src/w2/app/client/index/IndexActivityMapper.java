package w2.app.client.index;

import w2.app.client.W2;
import w2.app.client.index.activity.EditCourseActivity;
import w2.app.client.index.activity.EditRoundActivity;
import w2.app.client.index.activity.ListCourseActivity;
import w2.app.client.index.activity.ListRoundActivity;
import w2.app.client.index.activity.ShowRoundActivity;
import w2.app.client.place.EditCoursePlace;
import w2.app.client.place.EditRoundPlace;
import w2.app.client.place.ListCoursePlace;
import w2.app.client.place.ListRoundPlace;
import w2.app.client.place.ShowRoundPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class IndexActivityMapper implements ActivityMapper {

	public IndexActivityMapper() {
	}

	@Override
	public Activity getActivity(Place place) {
		W2.removeMessage();

		if (place instanceof EditCoursePlace) {
			EditCoursePlace ecp = (EditCoursePlace) place;
			Long courseId = ecp.getCourseId();
			return new EditCourseActivity(courseId);
		}
		if (place instanceof ListCoursePlace) {
			ListCoursePlace ecp = (ListCoursePlace) place;
			int start = ecp.getStart();
			int length = ecp.getLength();
			return new ListCourseActivity(start, length);
		}
		if (place instanceof EditRoundPlace) {
			EditRoundPlace roundPlace = (EditRoundPlace) place;
			return new EditRoundActivity(roundPlace.getCourseId());
		}
		if (place instanceof ShowRoundPlace) {
			ShowRoundPlace showRound = (ShowRoundPlace) place;
			return new ShowRoundActivity(showRound.getCourseId());
		}
		if (place instanceof ListRoundPlace) {
			ListRoundPlace listRoundPlace = (ListRoundPlace) place;
			int start = listRoundPlace.getStart();
			int length = listRoundPlace.getLength();
			return new ListRoundActivity(start, length);
		}
		return null;
	}
}