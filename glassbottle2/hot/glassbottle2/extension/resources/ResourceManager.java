package glassbottle2.extension.resources;

import glassbottle2.binding.Encoding;
import glassbottle2.binding.StartupTime;
import glassbottle2.context.ServletPathInfo;
import glassbottle2.extension.jspquery.JSPQueryManager;
import glassbottle2.extension.jspquery.tag.CSS;
import glassbottle2.extension.jspquery.tag.JavaScript;
import glassbottle2.scope.EventScoped;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.Path;

import org.apache.commons.lang.StringEscapeUtils;

@EventScoped
public class ResourceManager
{
   @StartupTime
   private Long startuptime;

   @Inject
   private ServletPathInfo info;

   @Inject
   JSPQueryManager jsp;

   public CSS css(Class<?> resourceClass, String resource) throws IOException
   {
      CSS css = jsp.$(CSS.class);
      css.href(toUrl(resourceClass, "css", resource));
      return css;
   }

   public JavaScript js(Class<?> resourceClass, String resource) throws IOException
   {
      JavaScript javaScript = jsp.$(JavaScript.class);
      javaScript.src(toUrl(resourceClass, "js", resource));
      javaScript.encoding(encoding);
      return javaScript;
   }

   @Encoding
   private String encoding;

   private String toUrl(Class<?> resourceClass, String type, String resource)
   {
      return url(resourceClass) + "/" + resource + "/" + type + "?" + startuptime;
   }

   public AddPath url(Class<?> resourceClass)
   {
      return new AddPath().path(info.getServletPath() + "/" + resourceClass.getAnnotation(Path.class).value());

   }

   public String h(Object object)
   {
      return StringEscapeUtils.escapeHtml(String.valueOf(object));
   }
}
