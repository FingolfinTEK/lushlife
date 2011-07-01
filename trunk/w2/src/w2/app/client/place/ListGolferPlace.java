package w2.app.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ListGolferPlace extends Place {
	private int start;
	private int length;

	public ListGolferPlace(int start, int length) {
		this.start = start;
		this.length = length;
	}

	public ListGolferPlace() {
		this(0, 1000);
	}

	public int getStart() {
		return start;
	}

	public int getLength() {
		return length;
	}

	public static class Tokenizer implements PlaceTokenizer<ListGolferPlace> {
		@Override
		public String getToken(ListGolferPlace place) {
			return place.start + ":" + place.length;
		}

		@Override
		public ListGolferPlace getPlace(String token) {
			String[] split = token.split(":");
			return new ListGolferPlace(Integer.parseInt(split[0]),
					Integer.parseInt(split[1]));
		}
	}
}