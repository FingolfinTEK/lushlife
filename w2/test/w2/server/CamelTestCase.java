package w2.server;

import java.io.File;

import junit.framework.Assert;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

//@RunWith(GaeSpringRunner.class)
//@ContextConfiguration("classpath*:META-INF/spring/applicationContext*.xml")
public class CamelTestCase extends CamelTestSupport {

	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		return new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				String token = "317056955-79vsiJrpm7vPscr9czEoLvEVwtki9hZ9nYpcBjut";
				String tokenSecret = "5Aa4zvgiiQgsw0qgUUqXVOtCJq0vquLWgIAbsfuZ8";
				from("file://target/inbox").process(new Processor() {

					@Override
					public void process(Exchange exchange) throws Exception {
						System.out.println(exchange.getIn().getBody());
					}
				})
				// .to("file://target/outbox");
						.to("twitter:/?" + //
								"token=" + token + //
								"&tokenSecret=" + tokenSecret);
			}
		};
	}

	@Test
	public void test() throws InterruptedException {
		template.sendBodyAndHeader("file://target/inbox", "ÇƒÇ∑Ç∆Å@Ç∑ÇƒÅ[ÇΩÇ∑",
				Exchange.FILE_NAME, "hello.text");
		Thread.sleep(1000);
		File target = new File("target/outbox/hello.text");
		Assert.assertTrue(target.exists());
		target.delete();
	}
}
