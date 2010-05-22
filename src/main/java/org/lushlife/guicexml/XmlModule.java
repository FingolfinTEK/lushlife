package org.lushlife.guicexml;

import java.io.InputStream;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.lushlife.guicexml.xml.ComponentXmlReader;
import org.lushlife.guicexml.xml.FactoryXmlReader;
import org.lushlife.guicexml.xml.XML;
import org.lushlife.guicexml.xml.XmlManagement;

import com.google.inject.AbstractModule;

public class XmlModule extends AbstractModule {
	private InputStream stream;
	private XmlManagement xmlManagement;

	XmlModule(InputStream stream, XmlManagement xmlManagement) {
		this.stream = stream;
		this.xmlManagement = xmlManagement;
	}

	public XmlModule(InputStream stream) {
		this(stream, new XmlManagement());
	}

	public XmlModule(String classPath) {
		this(Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(classPath), new XmlManagement());
	}

	@Override
	protected void configure() {
		try {
			Element rootElement = XML.getRootElement(stream);
			for (Object obj : rootElement.elements()) {
				Element element = (Element) obj;
				if (element.getName().equals("component")) {
					ComponentXmlReader.create(element, xmlManagement).bind(
							this.binder());
				}
				if (element.getName().equals("factory")) {
					FactoryXmlReader.create(element, xmlManagement).bind(
							this.binder());
				}
			}
		} catch (DocumentException e) {
			addError(e);
		}
	}

}
