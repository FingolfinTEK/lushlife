package lushfile.plugins.view;

import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;
import groovy.text.TemplateEngine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lushfile.core.groovy.LushGroovyShellManager;
import lushfile.core.lock.DoubleCheckBlocking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class GSPTemplateEngine {

	Logger logger = LoggerFactory.getLogger(GSPTemplateEngine.class);

	private TemplateEngine engine;

	@Inject
	private ClassLoader loader;

	@Inject
	@Named("encoding")
	private String encoding;

	private ConcurrentHashMap<String, Template> cache = new ConcurrentHashMap<String, Template>();
	private Lock lock = new ReentrantLock();

	@Inject
	public GSPTemplateEngine(LushGroovyShellManager manager) {
		this.engine = new SimpleTemplateEngine(manager.getShell());
	}

	public Template create(final String classLoaderPath) {
		return new DoubleCheckBlocking<String, Template>() {

			@Override
			protected Map<String, Template> cache() {
				return cache;
			}

			@Override
			protected Template create() {
				URL url = loader.getResource(classLoaderPath);
				if (url == null) {
					throw new IllegalStateException("template not found "
							+ classLoaderPath);
				}
				try {
					Reader reader = new InputStreamReader(url.openStream(),
							encoding);
					try {
						if (logger.isDebugEnabled()) {
							BufferedReader br = new BufferedReader(reader);
							StringBuilder sb = new StringBuilder();
							for (String s = br.readLine(); s != null; s = br
									.readLine()) {
								sb.append(s);
								sb.append("\n");
							}
							logger.debug(sb.toString());
							return engine.createTemplate(sb.toString());
						} else {
							return engine.createTemplate(reader);
						}
					} finally {
						reader.close();
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}

			@Override
			protected String key() {
				return classLoaderPath;
			}

			@Override
			protected Lock lock() {
				return lock;
			}

		}.get();

	}
}
