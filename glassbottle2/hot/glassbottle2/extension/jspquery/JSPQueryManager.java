package glassbottle2.extension.jspquery;

import glassbottle2.Injector;
import glassbottle2.extension.jspquery.tag.TagBase;
import glassbottle2.scope.EventScoped;

import java.util.LinkedList;

import javax.enterprise.inject.Current;

@EventScoped
public class JSPQueryManager
{
   LinkedList<TagBase<?>> tags = new LinkedList<TagBase<?>>();

   @Current
   Injector injector;

   public <T> T $(Class<T> clazz)
   {
      if (TagBase.class.isAssignableFrom(clazz))
      {
         TagBase<?> tagBase = TagBase.of((Class<? extends TagBase>) clazz);
         tagBase.setManager(this);
         return (T) tagBase;
      }
      else
      {
         return injector.getInstance(clazz);
      }
   }

   public void addStack(TagBase<?> tag)
   {
      tags.add(tag);
   }

   public String end()
   {
      TagBase<?> last = tags.removeLast();
      return last.end();
   }

}
