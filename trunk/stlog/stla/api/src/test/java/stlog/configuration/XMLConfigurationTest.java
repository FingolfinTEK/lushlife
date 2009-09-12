package stlog.configuration;

import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

import stlog.decorator.AddLogIdToMessage;
import stlog.decorator.ChangeLoglevel;
import stlog.message.AnnotationMessageResolver;
import stlog.message.ResourceBunldeMessageResolver;
import stlog.spi.LevelResolver;
import stlog.spi.LocaleSelector;
import stlog.spi.LogProviderDecorator;
import stlog.spi.MessageResolver;

public class XMLConfigurationTest {
	@Test
	public void testXml() {
		URL stlogXml = Thread.currentThread().getContextClassLoader()
				.getResource("stlog.xml");
		new STLogXmlParser(new LoggingManagerBinder() {

			public void localeSelector(LocaleSelector localeSelector) {
				// TODO Auto-generated method stub

			}

			public void levelResolver(LevelResolver levelResolver) {
				// TODO Auto-generated method stub

			}

			public void install(LoggingManagerConfiguration config) {
				// TODO Auto-generated method stub

			}

			int counter = 0;

			public void addMessageResolver(MessageResolver messageResolver) {
				if (counter == 0) {
					Assert.assertEquals(messageResolver.getClass(),
							ResourceBunldeMessageResolver.class);
				}
				if (counter == 1) {
					Assert.assertEquals(messageResolver.getClass(),
							AnnotationMessageResolver.class);
				}
				counter++;
			}

			int decoratorCounter = 0;

			public void addDecorator(LogProviderDecorator decorator) {
				if (decoratorCounter == 0) {
					Assert.assertEquals(decorator.getClass(),
							ChangeLoglevel.class);
				}
				if (decoratorCounter == 1) {
					Assert.assertEquals(decorator.getClass(),
							AddLogIdToMessage.class);
				}
				decoratorCounter++;
			}
		}).parse(stlogXml);
	}
}
