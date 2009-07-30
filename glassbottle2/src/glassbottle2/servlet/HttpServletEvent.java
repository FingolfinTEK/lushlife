package glassbottle2.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpServletEvent
{
   HttpServletRequest request;
   HttpServletResponse response;

   public HttpServletEvent(HttpServletRequest request, HttpServletResponse response)
   {
      this.request = request;
      this.response = response;
   }

   public HttpServletRequest getRequest()
   {
      return request;
   }

   public HttpServletResponse getResponse()
   {
      return response;
   }

}
