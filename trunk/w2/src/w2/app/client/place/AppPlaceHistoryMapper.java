package w2.app.client.place;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ EditGolferPlace.Tokenizer.class,
		ListGolferPlace.Tokenizer.class, EditCoursePlace.Tokenizer.class,
		ListCoursePlace.Tokenizer.class, EditRoundPlace.Tokenizer.class,
		ListRoundPlace.Tokenizer.class, ShowRoundPlace.Tokenizer.class })
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {
}