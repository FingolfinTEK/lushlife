package glassbottle.plugins.service.impl;


import glassbottle.core.Injector;
import glassbottle.core.scope.Singleton;
import glassbottle.plugins.service.TypeFormatter;
import glassbottle.plugins.service.dsl.TypeFormatBinder;
import glassbottle.plugins.service.dsl.TypeFormatBinding;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Current;
import javax.enterprise.inject.spi.BeanManager;



@Singleton
public class TypeFormatterRegistry
{
   private Map<String, Class<? extends TypeFormatter>> formatters = new HashMap<String, Class<? extends TypeFormatter>>();

   public TypeFormatter get(String type)
   {
      Class<? extends TypeFormatter> formatter = formatters.get(type);
      if (formatter == null)
      {
         throw new RuntimeException("un supported type format" + type);
      }
      return injector.getInstanceByType(formatter);
   }

   @Current
   Injector injector;

   @Current
   BeanManager beanManager;

   @PostConstruct
   public void init()
   {
      TypeFormatBinder configurator = new TypeFormatBinder()
      {
         @Override
         public TypeFormatBinding bind(final String type)
         {
            return new TypeFormatBinding()
            {
               @Override
               public void to(Class<? extends TypeFormatter> formatter)
               {
                  formatters.put(type, formatter);
               }
            };
         }
      };
      beanManager.fireEvent(configurator);
   }
}
