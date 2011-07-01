package w2.app.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import w2.app.model.Golfer;

import com.googlecode.objectify.Objectify;

@Service
public class GolferService {
	@Inject
	Objectify objectify;

	@Transactional
	public Golfer add(String name, String adress) {
		Golfer golfer = new Golfer();
		golfer.setName(name);
		golfer.setMailAdress(adress);
		objectify.put(golfer);
		return golfer;
	}

	public Golfer findById(long id) {
		return objectify.find(Golfer.class, id);
	}
}
