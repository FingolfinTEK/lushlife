package glassbottle2.extension.groovy;

import glassbottle2.binding.Encoding;
import glassbottle2.util.loader.ClassLoaderProducer;
import groovy.lang.GroovyShell;

import org.codehaus.groovy.control.CompilerConfiguration;

@javax.inject.Singleton
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
