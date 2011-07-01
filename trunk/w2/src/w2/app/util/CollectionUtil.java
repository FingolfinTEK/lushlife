package w2.app.util;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtil {
	static public <T> List<T> toList(Iterable<T> it) {
		ArrayList<T> list = new ArrayList<T>();
		for (T g : it) {
			list.add(g);
		}
		return list;
	}
}
