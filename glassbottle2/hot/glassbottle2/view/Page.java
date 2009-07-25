package glassbottle2.view;

import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

public interface Page extends StreamingOutput
{
   public void write(OutputStream arg0) throws IOException, WebApplicationException;
}
