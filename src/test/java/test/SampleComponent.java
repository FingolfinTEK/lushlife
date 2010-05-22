package test;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

public class SampleComponent {

	List<Integer> a;
	List<String> b;
	Map<String, String> c;

	Object value;

	Object value2;

	Object value3;

	@PostConstruct
	public void init() {
		System.out.println("post construct");
	}
}
