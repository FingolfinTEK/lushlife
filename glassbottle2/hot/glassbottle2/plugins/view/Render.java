package glassbottle2.plugins.view;

import glassbottle2.GlassBottle;

import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

public class Render implements StreamingOutput
{
   private Class<? extends Page> viewClazz;

   public Render(Class<? extends Page> viewClazz)
   {
      this.viewClazz = viewClazz;
   }

   @Override
   public void write(OutputStream outputStream) throws IOException, WebApplicationException
   {
      Page page = GlassBottle.getInjector().getInstanceByType(viewClazz);
      page.write(outputStream);
   }
}
