package w2.app.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ShowRoundPlace extends Place {
	private Long userId;

	public ShowRoundPlace(Long token) {
		this.userId = token;
	}

	public Long getCourseId() {
		return userId;
	}

	public static class Tokenizer implements PlaceTokenizer<ShowRoundPlace> {
		@Override
		public String getToken(ShowRoundPlace place) {
			if (place.getCourseId() == null || place.getCourseId().equals(0L)) {
				return "";
			}
			return place.getCourseId() + "";
		}

		@Override
		public ShowRoundPlace getPlace(String token) {
			if (token == null || token.isEmpty()) {
				return new ShowRoundPlace(null);
			}
			return new ShowRoundPlace(Long.parseLong(token));
		}
	}
}