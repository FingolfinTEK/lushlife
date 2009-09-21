package application.lushup.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.StreamingOutput;

import org.lushlife.kamikaze.Injector;
import org.lushlife.kamikaze.jsp.JSPPage;
import org.lushlife.kamikaze.view.Page;

import application.lushup.model.Customer;

public class CustomerBase
{
   @Inject
   Injector injector;

   @Inject
   Customer customer;

   @GET
   @Path("{id}/{name}")
   // @Produces("application/json")
   public StreamingOutput getCustomer2(@PathParam("id") int id, @PathParam("name") String name)
   {
      customer.setId(id);
      // customer.setFirstName(name);
      customer.setFirstName("xxxxxxxx");
      return JSPPage.to("/test.jsp");
   }

   @GET
   @Path("{id}")
   public Page getCustomer(@PathParam("id") int id)
   {
      customer.setId(id);
      customer.setFirstName("last name name name");
      return JSPPage.to("/test.jsp");
   }

}