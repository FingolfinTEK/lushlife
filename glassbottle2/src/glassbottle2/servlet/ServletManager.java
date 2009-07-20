package glassbottle2.servlet;

import glassbottle2.scope.EventScoped;

import javax.enterprise.inject.Named;
import javax.enterprise.inject.Produces;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@EventScoped
public class ServletManager
{
   private HttpServletRequest request;

   private HttpServletResponse response;

   public void setRequest(HttpServletRequest request)
   {
      this.request = request;
   }

   public void setResponse(HttpServletResponse response)
   {
      this.response = response;
   }

   @Produces
   @Named("request")
   public HttpServletRequest getRequest()
   {
      return request;
   }

   @Produces
   @Named("response")
   public HttpServletResponse getResponse()
   {
      return response;
   }

}
