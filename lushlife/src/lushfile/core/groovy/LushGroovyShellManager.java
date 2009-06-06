package lushfile.core.groovy;

import org.codehaus.groovy.control.CompilerConfiguration;

import groovy.lang.GroovyShell;
import lushfile.core.context.ClassLoaderManager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class LushGroovyShellManager {

	@Inject
	@Named("encoding")
	private String encoding;

	public GroovyShell getShell() {
		return shell;
	}

	private GroovyShell shell;

	public LushGroovyShellManager() {
		CompilerConfiguration config = new CompilerConfiguration();
		config.setSourceEncoding(encoding);
		this.shell = new GroovyShell(ClassLoaderManager.getClassLoader());

	}

}
