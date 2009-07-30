package glassbottle2.extension.resources;

import glassbottle2.binding.Encoding;
import glassbottle2.binding.StartupTime;
import glassbottle2.context.ServletPathInfo;
import glassbottle2.scope.EventScoped;
import glassbottle2.util.markup.Markup;
import glassbottle2.util.markup.NestTag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.inject.Current;

import org.apache.commons.lang.StringEscapeUtils;

@EventScoped
public class ResourceManager
{
   @StartupTime
   private Long startuptime;

   @Current
   private ServletPathInfo info;

   public String css(String resource) throws IOException
   {
      return css(resource, new HashMap<String, String>());
   }

   public String js(String resource) throws IOException
   {
      return js(resource, new HashMap<String, String>());
   }

   public String js(String resource, Map<String, String> attr) throws IOException
   {

      if (!attr.containsKey("type"))
      {
         attr.put("type", "text/javascript");
      }

      if (!attr.containsKey("charset"))
      {
         attr.put("charset", encoding);
      }

      attr.put("src", toUrl("js", resource));

      return (new Markup().tag("script", attr, new NestTag()
      {
         public void markup(Markup markup)
         {
         }
      }).toString());

   }

   @Encoding
   private String encoding;

   private String toUrl(String type, String resource)
   {
      return "/" + this.info.getServletPath() + "/" + resource + "/" + type + "?" + startuptime;
   }

   public String css(String resource, Map<String, String> attr) throws IOException
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

      attr.put("href", toUrl("css", resource));

      return new Markup().tag("link", attr).toString();

   }

   public String h(Object object)
   {
      return StringEscapeUtils.escapeHtml(String.valueOf(object));
   }
}
