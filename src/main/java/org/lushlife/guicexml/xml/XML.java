package org.lushlife.guicexml.xml;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.UnknownHostException;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XML {
	public static Element getRootElement(InputStream stream)
			throws DocumentException {
		try {
			SAXReader saxReader = new SAXReader();
			saxReader.setMergeAdjacentText(true);
			return saxReader.read(stream).getRootElement();
		} catch (DocumentException e) {
			Throwable nested = e.getNestedException();
			if (nested != null) {
				if (nested instanceof FileNotFoundException) {
					throw new RuntimeException(
							"Can't find schema/DTD reference: "
									+ nested.getMessage(), e);
				} else if (nested instanceof UnknownHostException) {
					throw new RuntimeException(
							"Cannot connect to host from schema/DTD reference: "
									+ nested.getMessage()
									+ " - check that your schema/DTD reference is current",
							e);
				}
			}
			throw e;
		}
	}

}
