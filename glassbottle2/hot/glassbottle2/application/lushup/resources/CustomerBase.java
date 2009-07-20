package glassbottle2.application.lushup.resources;

import glassbottle2.application.lushup.model.Customer;
import glassbottle2.application.lushup.view.Index;
import glassbottle2.plugins.view.Render;

import javax.enterprise.inject.Current;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.StreamingOutput;

public class CustomerBase
{
   @Current
   Customer customer;

   @GET
   @Path("{id}/{name}")
   // @Produces("application/json")
   public StreamingOutput getCustomer2(@PathParam("id")
   int id, @PathParam("name")
   String name)
   {
      customer.setId(id);
      customer.setFirstName(name);
      return new Render(Index.class);
   }

   @GET
   @Path("{id}")
   // @Produces("application/json")
   public Render getCustomer(@PathParam("id")
   int id)
   {
      customer.setId(id);
      return new Render(Index.class);
   }

}