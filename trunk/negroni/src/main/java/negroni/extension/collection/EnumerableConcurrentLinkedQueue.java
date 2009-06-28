package negroni.extension.collection;

import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class EnumerableConcurrentLinkedQueue<T> extends
		ConcurrentLinkedQueue<T> implements Enumerable<T> {

}
