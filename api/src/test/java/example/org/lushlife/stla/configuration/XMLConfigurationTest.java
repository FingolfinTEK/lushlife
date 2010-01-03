package example.org.lushlife.stla.configuration;

import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Assert;
import org.junit.Test;
import org.lushlife.stla.configuration.LoggingManagerBinder;
import org.lushlife.stla.configuration.LoggingManagerConfiguration;
import org.lushlife.stla.configuration.STLAXmlParser;
import org.lushlife.stla.decorator.AddLogIdToMessage;
import org.lushlife.stla.decorator.ChangeLoglevel;
import org.lushlife.stla.message.AnnotationMessageResolver;
import org.lushlife.stla.message.ResourceBunldeMessageResolver;
import org.lushlife.stla.spi.LevelResolver;
import org.lushlife.stla.spi.LocaleSelector;
import org.lushlife.stla.spi.LogProviderDecorator;
import org.lushlife.stla.spi.MessageResolver;



public class XMLConfigurationTest {
	@Test
	public void testXml() {
		URL stlogXml = Thread.currentThread().getContextClassLoader()
				.getResource("stla.xml");
		final AtomicBoolean callMessageResolver = new AtomicBoolean(false);
		final AtomicBoolean callDecorator = new AtomicBoolean(false);
		
		new STLAXmlParser(new LoggingManagerBinder() {

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
				callMessageResolver.set(true);
			}

			int decoratorCounter = 0;

			public void addDecorator(LogProviderDecorator decorator) {
				// if (decoratorCounter == 0) {
				// Assert.assertEquals(decorator.getClass(),
				// ChangeLoglevel.class);
				// }
				// if (decoratorCounter == 1) {
				// Assert.assertEquals(decorator.getClass(),
				// AddLogIdToMessage.class);
				// }
				decoratorCounter++;
				callDecorator.set(true);
			}
		}).parse(stlogXml);
		
		assert callMessageResolver.get();
		assert callDecorator.get();
	}
}
