package glassbottle2.extension.groovy;

import glassbottle2.binding.Encoding;
import glassbottle2.binding.StartupTime;
import glassbottle2.context.ServletPathInfo;
import glassbottle2.extension.groovy.impl.TemplateManager;
import glassbottle2.scope.EventScoped;
import glassbottle2.util.markup.Markup;
import glassbottle2.util.markup.NestTag;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.inject.Current;
import javax.enterprise.inject.Named;

import org.apache.commons.lang.StringEscapeUtils;

@Named("l")
@EventScoped
public class LFunction
{
   @Current
   TemplateManager manager;

   @StartupTime
   Long startuptime;

   @Current
   ServletPathInfo info;

   public void css(String resource)
   {
      css(resource, new HashMap<String, String>());
   }

   public void js(String resource)
   {
      js(resource, new HashMap<String, String>());
   }

   public void js(String resource, Map<String, String> attr)
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

      manager.getWriter().write(new Markup().tag("script", attr, new NestTag()
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

   public void css(String resource, Map<String, String> attr)
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

      manager.getWriter().write(new Markup().tag("link", attr).toString());

   }

   public String h(Object object)
   {
      return StringEscapeUtils.escapeHtml(String.valueOf(object));
   }
}
