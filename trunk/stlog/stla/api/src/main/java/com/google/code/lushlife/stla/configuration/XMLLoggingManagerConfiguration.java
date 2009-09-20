package com.google.code.lushlife.stla.configuration;

import java.net.URL;

public class XMLLoggingManagerConfiguration extends
		LoggingManagerConfigurationBase {

	private URL url;

	public XMLLoggingManagerConfiguration(URL url) {
		this.url = url;
	}

	@Override
	protected void configure() {
		new STLAXmlParser(this).parse(url);
	}

}
