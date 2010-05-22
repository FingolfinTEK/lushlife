package org.lushlife.guicexml.xml;

import java.io.InputStream;
import java.io.Serializable;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

public class DTDEntityResolver implements EntityResolver, Serializable {

	private static final long serialVersionUID = -4553926061006790714L;

	private static final String USER_NAMESPACE = "classpath://";

	public InputSource resolveEntity(String publicId, String systemId) {
		if (systemId != null) {
			if (systemId.startsWith(USER_NAMESPACE)) {
				String path = systemId.substring(USER_NAMESPACE.length());

				InputStream stream = resolveInSeamNamespace(path);
				if (stream == null) {
				} else {
					InputSource source = new InputSource(stream);
					source.setPublicId(publicId);
					source.setSystemId(systemId);
					return source;
				}
			}
		}
		return null;
	}

	protected InputStream resolveInSeamNamespace(String path) {
		return this.getClass().getClassLoader().getResourceAsStream(path);
	}

}
