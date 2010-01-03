package org.lushlife;

import java.net.URL;

import org.lushlife.inject.HiddenContext;
import org.lushlife.inject.HiddenContextManager;
import org.lushlife.inject.Injector;
import org.lushlife.inject.dsl.ModuleBase;
import org.lushlife.log.SLF4JLoggerProducer;
import org.lushlife.log.STLALogProducer;
import org.lushlife.log.TraceInterceptor;
import org.lushlife.mvc.JSPPage;
import org.lushlife.mvc.JSPPageManager;
import org.lushlife.mvc.Messages;
import org.lushlife.persistence.EntityManagerFactoryProducer;
import org.lushlife.persistence.ManagedEntityManager;
import org.lushlife.servlet.ServletManager;
import org.lushlife.transaction.TransactionalInterceptor;
import org.lushlife.validator.ValidationProducer;

import com.sun.jersey.server.impl.jcdi.JCDIComponentExtension;

public class LushLifeModule extends ModuleBase {

	public void configutaion() {
		// mvc
		bean(JSPPageManager.class);
		bean(JSPPage.class);
		bean(Messages.class);

		// servlet
		bean(ServletManager.class);

		// inject
		bean(JCDIComponentExtension.class);
		bean(Injector.class);
		bean(HiddenContext.class);
		bean(HiddenContextManager.class);

		// persistence
		bean(ManagedEntityManager.class);
		bean(EntityManagerFactoryProducer.class);
		bean(TransactionalInterceptor.class);

		// validator
		bean(ValidationProducer.class);

		// log
		bean(STLALogProducer.class);
		bean(SLF4JLoggerProducer.class);
		bean(TraceInterceptor.class);

		// beansXml
		URL beansXml = Thread.currentThread().getContextClassLoader()
				.getResource("META-INF/beans.xml");
		if (beansXml != null) {
			beansXml(beansXml);
		}
	}

}
