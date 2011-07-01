package w2.app.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class EditCoursePlace extends Place {
	private Long userId;

	public EditCoursePlace(Long token) {
		this.userId = token;
	}

	public Long getCourseId() {
		return userId;
	}

	public static class Tokenizer implements PlaceTokenizer<EditCoursePlace> {
		@Override
		public String getToken(EditCoursePlace place) {
			if (place.getCourseId() == null || place.getCourseId().equals(0L)) {
				return "";
			}
			return place.getCourseId() + "";
		}

		@Override
		public EditCoursePlace getPlace(String token) {
			if (token == null || token.isEmpty()) {
				return new EditCoursePlace(null);
			}
			return new EditCoursePlace(Long.parseLong(token));
		}
	}
}