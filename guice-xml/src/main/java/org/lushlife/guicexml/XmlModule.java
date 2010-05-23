package org.lushlife.guicexml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.dom4j.Namespace;
import org.lushlife.guicexml.reflection.DependencyManagement;
import org.lushlife.guicexml.xml.ComponentXmlReader;
import org.lushlife.guicexml.xml.FactoryXmlReader;
import org.lushlife.guicexml.xml.XML;
import org.lushlife.stla.Log;
import org.lushlife.stla.Logging;

import com.google.inject.AbstractModule;

public class XmlModule extends AbstractModule {
	static private Log log = Logging.getLog(XmlModule.class);
	private URL url;
	private DependencyManagement xmlManagement;

	XmlModule(URL url, DependencyManagement xmlManagement) {
		this.url = url;
		this.xmlManagement = xmlManagement;
	}

	public XmlModule(URL url) {
		this(url, new DependencyManagement());
	}

	public XmlModule(String classPath) {
		this(Thread.currentThread().getContextClassLoader().getResource(
				classPath), new DependencyManagement());
	}

	@Override
	protected void configure() {
		InputStream stream = null;
		try {
			log.log(GuiceXmlLogMessage.READ_XML_FILE, url);
			if (url == null) {
				return;
			}
			stream = url.openStream();
			if (stream == null) {
				return;
			}
			Element rootElement = XML.getRootElement(stream);
			Map<String, Namespace> namespaces = new HashMap<String, Namespace>();
			for (Object obj : rootElement.content()) {
				if (obj instanceof Namespace) {
					Namespace namespace = (Namespace) obj;
					String prefix = namespace.getPrefix();
					if (prefix == null || prefix.length() == 0) {
						continue;
					}
					namespaces.put(prefix, namespace);
				}
			}
			for (Object obj : rootElement.elements()) {
				Element element = (Element) obj;
				Namespace namespace = element.getNamespace();
				if (namespace.getText().isEmpty()) {
					configureDefaultNameSpace(element);
					continue;
				}
				if ("http://code.google.com/p/lushlife/guice-xml"
						.equals(namespace.getStringValue())) {
					configureDefaultNameSpace(element);
					continue;
				}
				if (namespace.getText().startsWith("urn:import:")) {
					String packageName = namespace.getText().substring(
							"urn:import:".length());
					String className = element.getName();
					ComponentXmlReader.create(packageName, className, element,
							xmlManagement).bind(this.binder());
					continue;
				}
				ComponentXmlReader.create(namespace, element.getName(),
						element, xmlManagement).bind(this.binder());

			}
		} catch (Exception e) {
			addError(e);
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private void configureDefaultNameSpace(Element element) {
		if (element.getName().equals("component")) {
			ComponentXmlReader.create(element, xmlManagement).bind(
					this.binder());
		}
		if (element.getName().equals("factory")) {
			FactoryXmlReader.create(element, xmlManagement).bind(this.binder());
		}
	}
}
