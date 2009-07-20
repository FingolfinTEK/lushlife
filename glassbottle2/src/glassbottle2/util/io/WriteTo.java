package glassbottle2.util.io;

import java.io.IOException;
import java.io.OutputStream;

public interface WriteTo
{

   public void to(OutputStream stream) throws IOException;
}
