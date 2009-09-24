package org.lushlife.negroni.core.configuration;

import org.lushlife.negroni.annotation.Config;
import org.lushlife.negroni.extension.collection.EnumerableArrayList;
import org.lushlife.negroni.extension.collection.EnumerableConcurrentLinkedQueue;
import org.lushlife.negroni.extension.collection.EnumerableHashSet;
import org.lushlife.negroni.extension.collection.EnumerableLinkedHashSet;
import org.lushlife.negroni.extension.collection.EnumerableLinkedList;
import org.lushlife.negroni.extension.collection.EnumerableTreeSet;

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
