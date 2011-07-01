package w2.app.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class EditGolferPlace extends Place {
	private Long userId;

	public EditGolferPlace(Long token) {
		this.userId = token;
	}

	public Long getUserId() {
		return userId;
	}

	public static class Tokenizer implements PlaceTokenizer<EditGolferPlace> {
		@Override
		public String getToken(EditGolferPlace place) {
			if (place.getUserId() == null || place.getUserId().equals(0L)) {
				return "";
			}
			return place.getUserId() + "";
		}

		@Override
		public EditGolferPlace getPlace(String token) {
			if (token == null || token.isEmpty()) {
				return new EditGolferPlace(null);
			}
			return new EditGolferPlace(Long.parseLong(token));
		}
	}
}