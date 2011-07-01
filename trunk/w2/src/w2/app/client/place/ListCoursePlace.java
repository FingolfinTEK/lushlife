package w2.app.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ListCoursePlace extends Place {
	private int start;
	private int length;

	public ListCoursePlace(int start, int length) {
		this.start = start;
		this.length = length;
	}

	public ListCoursePlace() {
		this(0, 1000);
	}

	public int getStart() {
		return start;
	}

	public int getLength() {
		return length;
	}

	public static class Tokenizer implements PlaceTokenizer<ListCoursePlace> {
		@Override
		public String getToken(ListCoursePlace place) {
			return place.start + ":" + place.length;
		}

		@Override
		public ListCoursePlace getPlace(String token) {
			String[] split = token.split(":");
			return new ListCoursePlace(Integer.parseInt(split[0]),
					Integer.parseInt(split[1]));
		}
	}
}