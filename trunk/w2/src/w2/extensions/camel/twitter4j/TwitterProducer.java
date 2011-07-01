package w2.extensions.camel.twitter4j;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.FailedToCreateProducerException;
import org.apache.camel.impl.DefaultAsyncProducer;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import w2.app.repository.Twitter4J;

public class TwitterProducer extends DefaultAsyncProducer {
	AccessToken accessToken;

	public TwitterProducer(Endpoint endpoint, AccessToken accessToken) {
		super(endpoint);
		this.accessToken = accessToken;
	}

	@Override
	public boolean process(Exchange exchange, AsyncCallback callback) {
		try {
			Twitter twitter = Twitter4J.factory.getInstance(accessToken);
			twitter.updateStatus(exchange.getIn().getBody(String.class));
		} catch (TwitterException e) {
			throw new FailedToCreateProducerException(this.getEndpoint(), e);
		}
		return true;
	}

}
