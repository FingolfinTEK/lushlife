package application.lushup.resources;

import glassbottle2.Injector;
import glassbottle2.extension.jsp.JSPPage;
import glassbottle2.view.Page;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.StreamingOutput;

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
      customer.setFirstName(name);
      return JSPPage.to("/test.jsp");
   }

   @GET
   @Path("{id}")
   public Page getCustomer(@PathParam("id") int id)
   {
      
      customer.setId(id);
      return JSPPage.to("/test.jsp");
   }

}