package org.lushlife.negroni.extension.collection;

import java.util.Comparator;
import java.util.List;

import org.lushlife.negroni.annotation.MixinImplementedBy;
import org.lushlife.negroni.core.closure.CR1;
import org.lushlife.negroni.core.closure.CR2;
import org.lushlife.negroni.core.closure.CV1;
import org.lushlife.negroni.core.closure.CV2;
import org.lushlife.negroni.extension.collection.impl.EnumerableImpl;


/**
 * @param <T>
 */
@MixinImplementedBy(EnumerableImpl.class)
public interface Enumerable<T>
{
   boolean all(CR1<Boolean, T> c);

   boolean any(CR1<Boolean, T> c);

   <R> List<R> collect(CR1<R, T> c);

   <R> List<R> map(CR1<R, T> c);

   void each(CV1<T> c);

   void eachWithIndex(CV2<T, Integer> c);

   T find(CR1<Boolean, T> c);

   T detect(CR1<Boolean, T> c);

   List<T> findAll(CR1<Boolean, T> c);

   List<T> select(CR1<Boolean, T> c);

   // grep(pattern)
   // grep(pattern) {|item| ... }
   <R> R inject(R init, CR2<R, R, T> c);

   boolean member(T val);

   boolean include(T val);

   T max();

   T max(Comparator<T> com);

   T min();

   T min(Comparator<T> com);

   List<T>[] partition(CR1<Boolean, T> c);

   List<T> reject(CR1<Boolean, T> c);

   void sort();

   void sort(Comparator<T> com);

   // sort_by {|item| ... } (ruby 1.7 feature)

   List<T> to_a();

   List<T> entries();

   // zip([ary1[, ary2[, ...]]])
   // zip([ary1[, ary2[, ...]]]) {|v1, v2, ...| ...}

}
