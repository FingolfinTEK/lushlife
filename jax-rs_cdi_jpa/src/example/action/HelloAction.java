package example.action;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hello")
@ManagedBean
public class HelloAction {

	@Named("hello")
	@Inject
	String hello;

	@GET
	public String hello() {
		return hello;
	}

}
