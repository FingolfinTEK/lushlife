package gso.service;

import gso.model.HelloModel;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.googlecode.objectify.Objectify;

@Service
public class HelloService {

	@Inject
	Objectify objectify;

	public String find(long id) {
		return objectify.find(HelloModel.class, id).getMessage();
	}

	public Long persist(String message) {
		HelloModel model = new HelloModel();
		model.setMessage(message);
		objectify.put(model);
		return model.getId();
	}

}
