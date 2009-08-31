package glassbottle2.el;

import glassbottle2.scope.EventScoped;

import java.util.AbstractMap;
import java.util.Set;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

@EventScoped
public class NamingResolverMap extends AbstractMap<String, Object>
{

   @Inject
   private ELVariableEventScopedMap map;

   @Inject
   private BeanManager manager;

   @Inject
   private NamingResolver resolver;

   public Object get(Object key)
   {
      if (map.containsKey(key))
      {
         return map.get(key);
      }
      Bean<?> bean = resolver.resolve(String.valueOf(key));
      if (bean != null)
      {
         return manager.getReference(bean, Object.class, manager.createCreationalContext(bean));
      }
      return null;
   }

   @Override
   public Object put(String key, Object value)
   {
      return map.put(key, value);
   }

   @Override
   public boolean containsKey(Object key)
   {
      return this.get(key) != null;
   }

   public Set<java.util.Map.Entry<String, Object>> entrySet()
   {
      throw new UnsupportedOperationException();
   }

}
