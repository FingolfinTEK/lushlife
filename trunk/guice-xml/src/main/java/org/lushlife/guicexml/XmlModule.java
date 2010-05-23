package org.lushlife.guicexml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.lushlife.guicexml.xml.ComponentXmlReader;
import org.lushlife.guicexml.xml.FactoryXmlReader;
import org.lushlife.guicexml.xml.XML;
import org.lushlife.guicexml.xml.XmlManagement;
import org.lushlife.stla.Log;
import org.lushlife.stla.Logging;

import com.google.inject.AbstractModule;

public class XmlModule extends AbstractModule {
	static private Log log = Logging.getLog(XmlModule.class);
	private URL url;
	private XmlManagement xmlManagement;

	XmlModule(URL url, XmlManagement xmlManagement) {
		this.url = url;
		this.xmlManagement = xmlManagement;
	}

	public XmlModule(URL url) {
		this(url, new XmlManagement());
	}

	public XmlModule(String classPath) {
		this(Thread.currentThread().getContextClassLoader().getResource(
				classPath), new XmlManagement());
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

}
