package glassbottle2.extension.jspquery;

import glassbottle2.extension.jspquery.tag.TagBase;
import glassbottle2.scope.EventScoped;

import java.util.LinkedList;

@EventScoped
public class JSPQueryManager
{
   LinkedList<TagBase<?>> tags = new LinkedList<TagBase<?>>();

   public <T extends TagBase<T>> T $(Class<T> clazz)
   {
      T tagBase = TagBase.of(clazz);
      tagBase.setManager(this);
      return tagBase;
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
