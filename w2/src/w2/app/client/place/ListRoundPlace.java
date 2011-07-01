package w2.app.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ListRoundPlace extends Place {
	private int start;
	private int length;

	public ListRoundPlace(int start, int length) {
		this.start = start;
		this.length = length;
	}

	public ListRoundPlace() {
		this(0, 1000);
	}

	public int getStart() {
		return start;
	}

	public int getLength() {
		return length;
	}

	public static class Tokenizer implements PlaceTokenizer<ListRoundPlace> {
		@Override
		public String getToken(ListRoundPlace place) {
			return place.start + ":" + place.length;
		}

		@Override
		public ListRoundPlace getPlace(String token) {
			String[] split = token.split(":");
			if (split.length < 2) {
				return new ListRoundPlace(0, 1000);
			}
			return new ListRoundPlace(Integer.parseInt(split[0]),
					Integer.parseInt(split[1]));
		}
	}
}