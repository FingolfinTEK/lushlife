package negroni.core.configuration;

import negroni.annotation.Config;
import negroni.extension.collection.EnumerableArrayList;
import negroni.extension.collection.EnumerableConcurrentLinkedQueue;
import negroni.extension.collection.EnumerableHashSet;
import negroni.extension.collection.EnumerableLinkedHashSet;
import negroni.extension.collection.EnumerableLinkedList;
import negroni.extension.collection.EnumerableTreeSet;

public enum LCollections {

	@Config(type = EnumerableArrayList.class)
	LArrayList,

	@Config(type = EnumerableConcurrentLinkedQueue.class)
	LConcurrentLinkedQueue,

	@Config(type = EnumerableHashSet.class)
	LHashSet,

	@Config(type = EnumerableLinkedHashSet.class)
	LLLinkedHashSet,

	@Config(type = EnumerableLinkedList.class)
	LinkedList,

	@Config(type = EnumerableTreeSet.class)
	LTreeSet

}
