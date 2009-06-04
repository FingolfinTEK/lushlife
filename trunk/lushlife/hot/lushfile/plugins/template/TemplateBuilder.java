package lushfile.plugins.template;

import groovy.util.BuilderSupport;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.Map;

import lushfile.core.context.RequestScopedMap;

import com.google.inject.Inject;

public class TemplateBuilder extends BuilderSupport {

	@Inject
	RequestScopedMap map;

	LinkedList<PrintWriter> writers = new LinkedList<PrintWriter>();

	@Override
	protected Object createNode(Object name) {
		System.out.println("1 create node " + name);
		return replaceWriter();
	}

	private StringWriter replaceWriter() {
		StringWriter writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		PrintWriter w = (PrintWriter) map.put("out", pw);
		writers.addLast(w);
		return writer;
	}

	@Override
	protected Object createNode(Object name, Object value) {
		replaceWriter();
		System.out.println("2 create node " + name + " " + value);
		return replaceWriter();
	}

	@Override
	protected void nodeCompleted(Object parent, Object node) {
		map.put("out", writers.pollLast());
		System.out.println(" " + parent + " " + node);
	}

	@Override
	protected Object createNode(Object name, Map attributes) {
		replaceWriter();
		System.out.println("3 create node " + name + " " + attributes);
		return replaceWriter();
	}

	@Override
	protected Object createNode(Object name, Map attributes, Object value) {
		replaceWriter();
		System.out.println("4 create node " + name + " " + attributes + " "
				+ value);
		return replaceWriter();
	}

	@Override
	protected void setParent(Object parent, Object child) {
		System.out.println("5 setParent " + parent + " " + child);
	}

}
