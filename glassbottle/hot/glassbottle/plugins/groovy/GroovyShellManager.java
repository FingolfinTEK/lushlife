package glassbottle.plugins.groovy;

import glassbottle.core.ClassLoaderProducer;
import glassbottle.core.binding.Encoding;
import glassbottle.core.scope.Singleton;
import groovy.lang.GroovyShell;

import org.codehaus.groovy.control.CompilerConfiguration;


@Singleton
public class GroovyShellManager
{

   @Encoding
   private String encoding;

   public GroovyShell getShell()
   {
      return shell;
   }

   private GroovyShell shell;

   public GroovyShellManager()
   {
      CompilerConfiguration config = new CompilerConfiguration();
      config.setSourceEncoding(encoding);
      this.shell = new GroovyShell(ClassLoaderProducer.getClassLoader());

   }

}
