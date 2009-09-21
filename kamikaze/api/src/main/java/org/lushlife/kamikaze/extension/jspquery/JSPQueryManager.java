package org.lushlife.kamikaze.extension.jspquery;


import java.util.LinkedList;

import javax.inject.Inject;

import org.lushlife.kamikaze.Injector;
import org.lushlife.kamikaze.extension.jspquery.tag.TagBase;
import org.lushlife.kamikaze.scope.EventScoped;


@EventScoped
public class JSPQueryManager
{
   LinkedList<TagBase<?>> tags = new LinkedList<TagBase<?>>();

   @Inject
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
