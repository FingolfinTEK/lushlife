package glassbottle2.plugins.view;

import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.WebApplicationException;

public interface Page
{
   public void write(OutputStream arg0) throws IOException, WebApplicationException;
}
