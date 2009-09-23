package negroni.extension.collection.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import negroni.annotation.Mixined;
import negroni.core.closure.CR1;
import negroni.core.closure.CR2;
import negroni.core.closure.CV1;
import negroni.core.closure.CV2;


public class EnumerableImpl<T> {

	@Mixined
	public boolean all(Collection<T> col, CR1<Boolean, T> c) {
		for (T t : col) {
			if (!c.call(t)) {
				return false;
			}
		}
		return true;
	}

	@Mixined
	public boolean any(Collection<T> col, CR1<Boolean, T> c) {
		for (T t : col) {
			if (c.call(t)) {
				return true;
			}
		}
		return false;
	}

	@Mixined
	public <R> List<R> collect(Collection<T> col, CR1<R, T> c) {
		return map(col, c);
	}

	@Mixined
	public <R> List<R> map(Collection<T> col, CR1<R, T> c) {
		ArrayList<R> list = new ArrayList<R>();
		for (T t : col) {
			list.add(c.call(t));
		}
		return list;
	}

	@Mixined
	public void each(Collection<T> col, CV1<T> c) {
		for (T t : col) {
			c.call(t);
		}
	}

	@Mixined
	public void eachWithIndex(Collection<T> col, CV2<T, Integer> c) {
		int i = -1;
		for (T t : col) {
			i++;
			c.call(t, i);
		}
	}

	@Mixined
	public T find(Collection<T> col, CR1<Boolean, T> c) {
		for (T t : col) {
			if (c.call(t)) {
				return t;
			}
		}
		return null;
	}

	@Mixined
	public T detect(Collection<T> col, CR1<Boolean, T> c) {
		return find(col, c);
	}

	@Mixined
	public List<T> findAll(Collection<T> col, CR1<Boolean, T> c) {
		List<T> list = new ArrayList<T>();
		for (T t : col) {
			if (c.call(t)) {
				list.add(t);
			}
		}
		return list;
	}

	@Mixined
	public List<T> select(Collection<T> col, CR1<Boolean, T> c) {
		return findAll(col, c);
	}

	// grep(pattern)
	// grep(pattern) {|item| ... }
	@Mixined
	public <R> R inject(Collection<T> col, R init, CR2<R, R, T> c) {
		R r = init;
		for (T t : col) {
			r = c.call(r, t);
		}
		return r;
	}

	@Mixined
	public boolean member(Collection<T> col, T val) {
		for (T t : col) {
			if (t.equals(val)) {
				return true;
			}
		}
		return false;
	}

	@Mixined
	public boolean include(Collection<T> col, T val) {
		return member(col, val);
	}

	static public class DefaultComparator<T> implements Comparator<T> {

		public int compare(T o1, T o2) {
			return ((Comparable<T>) o1).compareTo(o2);
		}
	}

	@Mixined
	public T max(Collection<T> col) {
		return max(col, new DefaultComparator<T>());
	}

	@Mixined
	public T max(Collection<T> col, Comparator<T> com) {
		T t = null;
		for (T t2 : col) {
			if (t == null) {
				t = t2;
			} else {
				if (com.compare(t, t2) < 0) {
					t = t2;
				}
			}
		}
		return t;
	}

	@Mixined
	public T min(Collection<T> col) {
		return min(col, new DefaultComparator<T>());
	}

	@Mixined
	public T min(Collection<T> col, Comparator<T> com) {
		T t = null;
		for (T t2 : col) {
			if (t == null) {
				t = t2;
			} else {
				if (com.compare(t, t2) > 0) {
					t = t2;
				}
			}
		}
		return t;
	}

	@Mixined
	public List<T>[] partition(Collection<T> col, CR1<Boolean, T> c) {
		List<T> y = new ArrayList<T>();
		List<T> n = new ArrayList<T>();
		for (T t : col) {
			boolean v = c.call(t);
			if (v) {
				y.add(t);
			} else {
				n.add(t);
			}
		}
		return new List[] { y, n };
	}

	@Mixined
	public List<T> reject(Collection<T> col, CR1<Boolean, T> c) {
		ArrayList<T> list = new ArrayList<T>();
		for (T t : col) {
			if (!c.call(t)) {
				list.add(t);
			}
		}
		return list;
	}

	@Mixined
	public void sort(Collection<T> col) {
		Collections.sort((List) col);
	}

	@Mixined
	public void sort(Collection<T> col, Comparator<T> com) {
		Collections.sort((List) col, com);
	}

	// sort_by {|item| ... } (ruby 1.7 feature)

	@Mixined
	public List<T> to_a(Collection<T> col) {
		return entries(col);
	}

	@Mixined
	public List<T> entries(Collection<T> col) {
		ArrayList<T> list = new ArrayList<T>();
		list.addAll(col);
		return list;
	}
}
