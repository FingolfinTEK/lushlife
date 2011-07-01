package w2.extensions.camel.twitter4j;

import org.apache.camel.Component;
import org.apache.camel.Producer;
import org.apache.camel.impl.ProcessorEndpoint;

import twitter4j.auth.AccessToken;

public class TwitterEndpoint extends ProcessorEndpoint {
	AccessToken accessToken;

	public TwitterEndpoint(String uri, Component component,
			AccessToken accessToken) {
		super(uri, component);
		this.accessToken = accessToken;
	}

	@Override
	public Producer createProducer() throws Exception {
		return new TwitterProducer(this, accessToken);
	}

}
