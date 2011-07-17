package app.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import app.repository.TwitterRepository;

@Controller
@RequestMapping("/twitter")
public class TwiterController {
	@Inject
	TwitterRepository repository;

	@RequestMapping(method = RequestMethod.GET)
	public String get(Model model) throws TwitterException {
		Twitter twitter = repository.get();
		model.addAttribute("status", twitter.getUserTimeline());
		return "/twitter.jsp";
	}

}
