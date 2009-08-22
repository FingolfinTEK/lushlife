package glassbottle2.extension.resources;

public class AddPath
{
   private StringBuilder sb = new StringBuilder();

   public AddPath path(Object path)
   {
      if (sb.length() != 0)
      {
         sb.append("/");
      }
      sb.append(path);
      return this;
   }

   @Override
   public String toString()
   {
      return sb.toString();
   }

}
