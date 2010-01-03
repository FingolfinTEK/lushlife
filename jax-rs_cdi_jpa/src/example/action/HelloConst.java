package example.action;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

public class HelloConst {
	@Named("hello")
	@Produces
	static final String hello = "‚±‚ñ‚É‚¿‚Í";

}
