package stlog.configuration;import java.beans.BeanInfo;import java.beans.IntrospectionException;import java.beans.Introspector;import java.beans.PropertyDescriptor;import java.io.IOException;import java.io.InputStream;import java.lang.reflect.InvocationTargetException;import java.lang.reflect.Method;import java.net.URL;import java.util.ArrayList;import java.util.List;import javax.xml.parsers.DocumentBuilder;import javax.xml.parsers.DocumentBuilderFactory;import javax.xml.parsers.ParserConfigurationException;import org.w3c.dom.DOMException;import org.w3c.dom.Document;import org.w3c.dom.Element;import org.w3c.dom.NamedNodeMap;import org.w3c.dom.Node;import org.xml.sax.SAXException;import stlog.i18n.LocaleSelectorImpl;import stlog.level.AnnotationLevelResolver;import stlog.message.AnnotationMessageResolver;import stlog.message.ResourceBunldeMessageResolver;import stlog.spi.LevelResolver;import stlog.spi.LocaleSelector;import stlog.spi.LogProviderDecorator;import stlog.spi.MessageResolver;public class STLogXmlParser {	private LoggingManagerBinder binder;	public STLogXmlParser(LoggingManagerBinder binder) {		this.binder = binder;	}	private static class XmlElement {		private URL file;		private Element element;		public XmlElement(URL file, Element element) {			super();			this.file = file;			this.element = element;		}		public URL getFile() {			return file;		}		public Element getElement() {			return element;		}		@Override		public String toString() {			return "File: " + getFile() + "; Node: " + getElement();		}	}	public void parse(URL url) {		DocumentBuilder documentBuilder;		try {			documentBuilder = DocumentBuilderFactory.newInstance()					.newDocumentBuilder();		} catch (ParserConfigurationException e) {			throw new IllegalArgumentException("Error configuring XML parser",					e);		}		List<XmlElement> messageResolverElements = new ArrayList<XmlElement>();		List<XmlElement> levelResolverElements = new ArrayList<XmlElement>();		List<XmlElement> decoratorsElements = new ArrayList<XmlElement>();		List<XmlElement> localeSelectorElements = new ArrayList<XmlElement>();		InputStream is;		boolean fileHasContents;		try {			is = url.openStream();			fileHasContents = is.available() > 0;		} catch (IOException e) {			throw new IllegalArgumentException("Error loading stlog.xml "					+ url.toString(), e);		}		if (fileHasContents) {			Document document;			try {				document = documentBuilder.parse(is);				document.normalize();			} catch (SAXException e) {				throw new IllegalArgumentException("Error parsing stlog.xml "						+ url.toString(), e);			} catch (IOException e) {				throw new IllegalArgumentException("Error loading stlog.xml "						+ url.toString(), e);			}			Element beans = document.getDocumentElement();			for (Node child : new stlog.util.NodeListIterable(beans					.getChildNodes())) {				if (child instanceof Element						&& "message-resolvers".equals(child.getNodeName())) {					messageResolverElements.add(new XmlElement(url,							(Element) child));				}				if (child instanceof Element						&& "level-resolver".equals(child.getNodeName())) {					levelResolverElements.add(new XmlElement(url,							(Element) child));				}				if (child instanceof Element						&& "locale-selector".equals(child.getNodeName())) {					localeSelectorElements.add(new XmlElement(url,							(Element) child));				}				if (child instanceof Element						&& "decorators".equals(child.getNodeName())) {					decoratorsElements							.add(new XmlElement(url, (Element) child));				}			}		}		try {			configureMessageResolver(messageResolverElements);			configureDecorator(decoratorsElements);			configureLocaleSelector(localeSelectorElements);			configureLevelResolver(levelResolverElements);		} catch (Exception e) {			throw new IllegalArgumentException("Error loading stlog.xml "					+ url.toString(), e);		}	}	private void configureLevelResolver(List<XmlElement> levelResolverElements)			throws DOMException, IllegalArgumentException,			ClassNotFoundException, InstantiationException,			IllegalAccessException, IntrospectionException,			InvocationTargetException {		if (levelResolverElements.size() == 0) {			// default value			binder.levelResolver(new AnnotationLevelResolver());			return;		}		LevelResolver resolver = createInstance(LevelResolver.class,				levelResolverElements.get(0).getElement().getAttributes());		binder.levelResolver(resolver);	}	private void configureLocaleSelector(List<XmlElement> localeSelectorElements)			throws DOMException, IllegalArgumentException,			ClassNotFoundException, InstantiationException,			IllegalAccessException, IntrospectionException,			InvocationTargetException {		if (localeSelectorElements.size() == 0) {			// default value			binder.localeSelector(new LocaleSelectorImpl());			return;		}		LocaleSelector resolver = createInstance(LocaleSelector.class,				localeSelectorElements.get(0).getElement().getAttributes());		binder.localeSelector(resolver);	}	private void configureDecorator(List<XmlElement> decoratorsElements)			throws DOMException, IllegalArgumentException,			ClassNotFoundException, InstantiationException,			IllegalAccessException, IntrospectionException,			InvocationTargetException {		for (XmlElement decoratorElement : decoratorsElements) {			for (Node child : new stlog.util.NodeListIterable(decoratorElement					.getElement().getChildNodes())) {				if (child.getNodeName().equals("decorator")) {					LogProviderDecorator resolver = createInstance(							LogProviderDecorator.class, child.getAttributes());					binder.addDecorator(resolver);				}			}		}	}	private void configureMessageResolver(	List<XmlElement> messageResolverElements) throws DOMException,			IllegalArgumentException, ClassNotFoundException,			InstantiationException, IllegalAccessException,			IntrospectionException, InvocationTargetException {		if (messageResolverElements.size() == 0) {			binder.addMessageResolver(new ResourceBunldeMessageResolver());			binder.addMessageResolver(new AnnotationMessageResolver());			return;		}		for (XmlElement messageResolver : messageResolverElements) {			for (Node child : new stlog.util.NodeListIterable(messageResolver					.getElement().getChildNodes())) {				if (child.getNodeName().equals("message-resolver")) {					MessageResolver resolver = createInstance(							MessageResolver.class, child.getAttributes());					binder.addMessageResolver(resolver);				}			}		}	}	private <T> T createInstance(Class<T> clazz, NamedNodeMap attributes)	throws DOMException, ClassNotFoundException, InstantiationException,	IllegalAccessException, IntrospectionException, IllegalArgumentException,			InvocationTargetException {		ClassLoader loader = Thread.currentThread().getContextClassLoader();		Class<?> loadClass = loader.loadClass(attributes.getNamedItem("class")				.getNodeValue());		Object obj = loadClass.newInstance();		BeanInfo beanInfo = Introspector.getBeanInfo(loadClass);		for (PropertyDescriptor desc : beanInfo.getPropertyDescriptors()) {			if (!String.class.equals(desc.getPropertyType())) {				continue;			}			Method writeMethod = desc.getWriteMethod();			if (writeMethod == null) {				continue;			}			Node namedItem = attributes.getNamedItem(desc.getName());			if (namedItem == null) {				continue;			}			writeMethod.invoke(obj, namedItem.getNodeValue());		}		return clazz.cast(obj);	}}