package gso.rcp;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ex.gwt.spring.SpringRemoteServiceServlet;
import gso.client.HelloWorld;
import gso.service.HelloService;

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
