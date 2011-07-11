package app.rcp;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import app.client.HelloWorld;
import app.service.HelloService;

import ex.gwt.spring.SpringRemoteServiceServlet;

@RequestMapping("*/helloworld.rcp")
@Controller
public class HelloWorldImpl extends SpringRemoteServiceServlet implements
		HelloWorld {

	private static final long serialVersionUID = -7044764689209364151L;

	@Inject
	HelloService service;

	@Override
	public String find(long id) {
		return service.find(id);
	}

	@Override
	public Long persist(String message) {
		return service.persist(message);
	}

}
