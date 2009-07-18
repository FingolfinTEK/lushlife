package glassbottle.util.reflection;


import glassbottle.core.ClassLoaderProducer;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


import javassist.ClassPool;
import javassist.CtClass;
import javassist.LoaderClassPath;

public class ReferenceClasses
{
   static public Set<Class<?>> getReferenceClasses(Class<?> clazz)
   {
      Set<Class<?>> classes = new HashSet<Class<?>>();
      LinkedList<Class<?>> stack = new LinkedList<Class<?>>();
      ClassPool classpool = new ClassPool();
      ClassLoader loader = ClassLoaderProducer.getClassLoader();
      classpool.appendClassPath(new LoaderClassPath(loader));

      stack.add(clazz);
      try
      {
         while (!stack.isEmpty())
         {
            Class<?> target = stack.poll();
            if (target.getPackage().getName().startsWith("java"))
            {
               continue;
            }
            if (target.isInterface())
            {
               continue;
            }
            if ((target.getModifiers() & Modifier.ABSTRACT) != 0)
            {
               continue;
            }

            CtClass ctclazz = classpool.get(target.getName());
            Collection<String> refClasses = ctclazz.getRefClasses();
            for (String className : refClasses)
            {
               Class<?> loadClass = loader.loadClass(className);
               if (!classes.contains(loadClass))
               {
                  classes.add(loadClass);
                  stack.add(loadClass);
               }
            }
         }
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
      return classes;
   }
}
