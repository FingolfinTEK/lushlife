package glassbottle2.extension.jspquery.tag;

public abstract class HTMLTag<T extends Tag<T>> extends TagBase<T>
{
   abstract public T name(String name);

   abstract public T id(String name);

}
