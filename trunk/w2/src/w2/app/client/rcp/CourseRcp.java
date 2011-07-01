package w2.app.client.rcp;

import java.util.List;

import w2.app.model.Course;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("course.rcp")
public interface CourseRcp extends RemoteService {
	List<Course> findAll(int start, int length);

	Course find(Long id);

	Long add(Course course);

	void update(Course course);

	List<Long> delete(Iterable<Course> courses);
}
