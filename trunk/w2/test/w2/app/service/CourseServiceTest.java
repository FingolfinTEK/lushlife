package w2.app.service;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;

import w2.GaeSpringRunner;
import w2.app.model.Course;
import w2.app.model.SLatLng;

@RunWith(GaeSpringRunner.class)
@ContextConfiguration("classpath*:META-INF/spring/application-context*.xml")
public class CourseServiceTest {

	@Test
	public void test() {
		ObjectifyFactory factory = new ObjectifyFactory();
		factory.register(Course.class);
		Objectify objectify = factory.begin();
		Course course = new Course();
		SLatLng latLng = new SLatLng();
		course.setLatLng(latLng);
		objectify.put(course);
		objectify = factory.begin();
		Course course2 = objectify.get(Course.class, course.getId());
	}

}
