package test;

import javax.annotation.PostConstruct;

public class Com2 {

	@PostConstruct
	public void init() {
		System.out.println("post construct + COM2");
	}
}
