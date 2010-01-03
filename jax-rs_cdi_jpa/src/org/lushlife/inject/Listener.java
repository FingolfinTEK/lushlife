/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lushlife.inject;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;

import org.jboss.weld.BeanManagerImpl;
import org.jboss.weld.Container;
import org.jboss.weld.Container.Status;
import org.jboss.weld.bootstrap.api.Bootstrap;
import org.jboss.weld.bootstrap.api.Environments;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.weld.context.api.BeanStore;
import org.jboss.weld.context.api.helpers.ConcurrentHashMapBeanStore;
import org.jboss.weld.environment.servlet.deployment.ServletDeployment;
import org.jboss.weld.environment.servlet.services.ServletServicesImpl;
import org.jboss.weld.environment.servlet.util.Reflections;
import org.jboss.weld.environment.tomcat.WeldAnnotationProcessor;
import org.jboss.weld.manager.api.WeldManager;
import org.jboss.weld.servlet.api.ServletListener;
import org.jboss.weld.servlet.api.ServletServices;
import org.jboss.weld.servlet.api.helpers.ForwardingServletListener;
import org.lushlife.stla.Log;
import org.lushlife.stla.Logging;

/**
 * @author Takeshi Kondo
 */
public class Listener extends ForwardingServletListener {
	private static final Log logger = Logging.getLog(Listener.class);

	private static final String BOOTSTRAP_IMPL_CLASS_NAME = "org.jboss.weld.bootstrap.WeldBootstrap";
	private static final String WELD_LISTENER_CLASS_NAME = "org.jboss.weld.servlet.WeldListener";
	private static final String APPLICATION_BEAN_STORE_ATTRIBUTE_NAME = Listener.class
			.getName()
			+ ".applicationBeanStore";
	private static final String EXPRESSION_FACTORY_NAME = "org.jboss.weld.el.ExpressionFactory";

	private final transient Bootstrap bootstrap;
	private final transient ServletListener weldListener;
	private WeldManager manager;

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		logger.log(LogInject.END_REQUEST);
		Injector injector = new Injector(manager);

		ClientContextManager hiddenContextManager = injector
				.getInstnace(ClientContextManager.class);

		hiddenContextManager.end();
		super.requestDestroyed(sre);

	}

	@Override
	public void requestInitialized(ServletRequestEvent sre) {

		logger.log(LogInject.BEGIN_REQUEST);
		super.requestInitialized(sre);
		Injector injector = new Injector(manager);
		ClientContextManager hiddenContextManager = injector
				.getInstnace(ClientContextManager.class);
		hiddenContextManager
				.begin((HttpServletRequest) sre.getServletRequest());
	}

	public Listener() {
		try {
			bootstrap = Reflections.newInstance(BOOTSTRAP_IMPL_CLASS_NAME);
		} catch (IllegalArgumentException e) {
			throw new IllegalStateException(
					"Error loading Weld bootstrap, check that Weld is on the classpath",
					e);
		}
		try {
			weldListener = Reflections.newInstance(WELD_LISTENER_CLASS_NAME);
		} catch (IllegalArgumentException e) {
			throw new IllegalStateException(
					"Error loading Weld listener, check that Weld is on the classpath",
					e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		bootstrap.shutdown();
		try {
			Reflections.classForName("org.apache.AnnotationProcessor");
			sce.getServletContext().removeAttribute(
					WeldAnnotationProcessor.class.getName());
		} catch (IllegalArgumentException e) {
		}
		super.contextDestroyed(sce);
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		BeanStore applicationBeanStore = new ConcurrentHashMapBeanStore();

		sce.getServletContext().setAttribute(
				APPLICATION_BEAN_STORE_ATTRIBUTE_NAME, applicationBeanStore);
		ServletDeployment deployment = new ServletDeployment(sce
				.getServletContext());
		BeanDeploymentArchive archive = deployment
				.getWebAppBeanDeploymentArchive();
		deployment.getServices().add(ServletServices.class,
				new ServletServicesImpl(archive));
		bootstrap.startContainer(Environments.SERVLET, deployment,
				applicationBeanStore).startInitialization();
		manager = bootstrap.getManager(archive);
		bootstrap.deployBeans().validateBeans().endInitialization();
		super.contextInitialized(sce);
		Container.initialize((BeanManagerImpl) manager, deployment
				.getServices());
		Container.instance().setStatus(Status.INITIALIZED);
		addContext();
	}

	private void addContext() {
		Injector injector = new Injector(manager);
		ClientContext hiddenContext = injector.getInstnace(ClientContext.class);
		((BeanManagerImpl) manager).addContext(hiddenContext);
	}

	@Override
	protected ServletListener delegate() {
		return weldListener;
	}

}
