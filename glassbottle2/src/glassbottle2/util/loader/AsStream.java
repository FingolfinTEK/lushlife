package glassbottle2.util.loader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AsStream
{

   private InputStream inputStream;

   public InputStream asStream()
   {
      return inputStream;
   }

   public AsStream(InputStream inputStream)
   {
      this.inputStream = inputStream;
   }

   public String asString() throws IOException
   {
      StringBuilder sb = new StringBuilder();
      InputStreamReader reader = new InputStreamReader(inputStream);
      try
      {
         char[] buf = new char[1024];
         while (true)
         {
            int read = reader.read(buf);
            if (read < 0)
            {
               break;
            }
            sb.append(buf, 0, read);
         }
         return sb.toString();
      }
      finally
      {
         reader.close();
      }
   }

   public byte[] asBytes() throws IOException
   {
      ByteArrayOutputStream bao = new ByteArrayOutputStream();
      byte[] buf = new byte[1024];
      try
      {
         while (true)
         {
            int size = inputStream.read(buf);
            if (size <= 0)
            {
               break;
            }
            bao.write(buf, 0, size);
         }
         bao.flush();
         return bao.toByteArray();
      }
      finally
      {
         inputStream.close();
      }
   }

}
