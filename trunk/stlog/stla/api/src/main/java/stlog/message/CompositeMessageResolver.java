package stlog.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import stlog.spi.MessageResolver;

public class CompositeMessageResolver implements MessageResolver {

	List<MessageResolver> resolvers = new ArrayList<MessageResolver>();

	public <E extends Enum<E>> String toMessage(E logId, Locale locale) {
		for (MessageResolver resolver : resolvers) {
			String message = resolver.toMessage(logId, locale);
			if (message != null) {
				return message;
			}
		}
		return null;
	}

	public void add(MessageResolver resolver) {
		this.resolvers.add(resolver);
	}

}
