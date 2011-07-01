package w2.extensions.camel.twitter4j;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.ResolveEndpointFailedException;
import org.apache.camel.impl.DefaultComponent;

import twitter4j.auth.AccessToken;

public class TwitterComponent extends DefaultComponent {
	protected void validateParameters(String uri,
			Map<String, Object> parameters, String optionPrefix) {
		if (parameters.size() != 2 || !parameters.containsKey("token")
				|| !parameters.containsKey("tokenSecret")) {
			throw new ResolveEndpointFailedException(
					uri,
					"There are "
							+ parameters.size()
							+ " parameters that couldn't be set on the endpoint."
							+ " Check the uri if the parameters are spelt correctly and that they are properties of the endpoint."
							+ " Unknown parameters=[" + parameters + "]");
		}

	}

	@Override
	protected Endpoint createEndpoint(String uri, String remaining,
			Map<String, Object> parameters) throws Exception {
		String token = (String) parameters.get("token");
		String tokenSecret = (String) parameters.get("tokenSecret");
		return new TwitterEndpoint(uri, this, new AccessToken(token,
				tokenSecret));
	}

}
