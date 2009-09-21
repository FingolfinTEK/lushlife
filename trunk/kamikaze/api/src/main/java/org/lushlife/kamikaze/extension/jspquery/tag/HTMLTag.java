package org.lushlife.kamikaze.extension.jspquery.tag;

public abstract class HTMLTag<T extends Tag<T>> extends TagBase<T>
{
   abstract public T name(Object name);

   abstract public String name();

   abstract public T id(Object id);

   abstract public String id();

   abstract public String style();

   public String styleClass()
   {
      return (String) attr.get("class");
   }

   public T styleClass(Object clazz)
   {
      attr("class", clazz);
      return (T) this;
   }

   abstract public String title();

   abstract public T title(Object id);

   abstract public String lang();

   abstract public T lang(Object id);

   abstract public String dir();

   abstract public T dir(Object id);

   abstract public String accesskey();

   abstract public T accesskey(Object key);

   abstract public String tabindex();

   abstract public T tabindex(Object value);

}
