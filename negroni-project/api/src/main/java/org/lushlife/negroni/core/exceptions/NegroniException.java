package org.lushlife.negroni.core.exceptions;

public class NegroniException extends RuntimeException
{

   private static final long serialVersionUID = 135077318089536168L;

   public NegroniException()
   {
      super();
   }

   public NegroniException(String message, Throwable cause)
   {
      super(message, cause);
   }

   public NegroniException(String message)
   {
      super(message);
   }

   public NegroniException(Throwable cause)
   {
      super(cause);
   }
}
