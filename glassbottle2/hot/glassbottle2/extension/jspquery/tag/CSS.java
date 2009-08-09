package glassbottle2.extension.jspquery.tag;

public abstract class CSS extends TagBase<CSS>
{
   @Override
   public String tag()
   {
      return "link";
   }

   public abstract CSS type(String type);

   public abstract CSS rel(String type);

   public abstract CSS media(String type);

   public abstract CSS href(String url);

   @Override
   protected void initialization()
   {
      if (!attr.containsKey("type"))
      {
         attr.put("type", "text/css");
      }

      if (!attr.containsKey("rel"))
      {
         attr.put("rel", "Stylesheet");
      }

      if (!attr.containsKey("media"))
      {
         attr.put("media", "screen");
      }
   }

   public String toString()
   {
      initialization();
      return simple();
   }

}
