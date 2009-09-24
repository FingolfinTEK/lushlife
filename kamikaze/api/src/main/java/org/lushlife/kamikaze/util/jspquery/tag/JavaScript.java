package org.lushlife.kamikaze.util.jspquery.tag;

public abstract class JavaScript extends HTMLTag<JavaScript>
{
   @Override
   public String tag()
   {
      return "script";
   }

   private String encoding;

   public String encoding()
   {
      return encoding;
   }

   public JavaScript encoding(String encoding)
   {
      this.encoding = encoding;
      return this;
   }

   abstract public JavaScript type(String type);

   abstract public JavaScript charset(String type);

   abstract public JavaScript src(String url);

   @Override
   public void initialization()
   {
      if (!attr.containsKey("type"))
      {
         attr.put("type", "text/javascript");
      }

      if (!attr.containsKey("charset"))
      {
         attr.put("charset", encoding);
      }
   }

   public String toString()
   {
      initialization();
      return start() + " " + end();
   }

}
