package stla.configuration;

import java.net.URL;

public class XMLLoggingManagerConfiguration extends
		LoggingManagerConfigurationBase {

	private URL url;

	public XMLLoggingManagerConfiguration(URL url) {
		super();
		this.url = url;
	}

	@Override
	protected void configure() {
		new STLogXmlParser(this).parse(url);
	}

}
