package w2.app.service;

import javax.inject.Inject;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import w2.GaeSpringRunner;
import w2.app.model.Golfer;

@RunWith(GaeSpringRunner.class)
@ContextConfiguration("classpath*:META-INF/spring/application-context*.xml")
public class GolferServiceTest {

	@Inject
	GolferService service;

	@Test
	public void testAddUser() {
		Golfer golfer = service.add("takeshi", "takeshi.kondo@gmail.com");
		Assert.assertNotNull(service.findById(golfer.getId()));
		golfer = service.add("takeshi", "takeshi.kondo@gmail.com");
		Assert.assertNotNull(service.findById(golfer.getId()));
	}
}
