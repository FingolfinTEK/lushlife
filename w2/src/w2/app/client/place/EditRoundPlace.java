package w2.app.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class EditRoundPlace extends Place {
	private Long userId;

	public EditRoundPlace(Long token) {
		this.userId = token;
	}

	public Long getCourseId() {
		return userId;
	}

	public static class Tokenizer implements PlaceTokenizer<EditRoundPlace> {
		@Override
		public String getToken(EditRoundPlace place) {
			if (place.getCourseId() == null || place.getCourseId().equals(0L)) {
				return "";
			}
			return place.getCourseId() + "";
		}

		@Override
		public EditRoundPlace getPlace(String token) {
			if (token == null || token.isEmpty()) {
				return new EditRoundPlace(null);
			}
			return new EditRoundPlace(Long.parseLong(token));
		}
	}
}