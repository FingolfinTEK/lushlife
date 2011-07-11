package app.service;

import javax.inject.Inject;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import app.service.HelloService;

import ex.spring.GaeSpringRunner;

@RunWith(GaeSpringRunner.class)
@ContextConfiguration("classpath*:META-INF/spring/application-context*.xml")
public class HelloServiceTest {

	@Inject
	HelloService service;

	@Test
	public void helloServiceTest() {
		Long id = service.persist("Hello World");
		String message = service.find(id);
		Assert.assertEquals("Hello World", message);
	}

}
