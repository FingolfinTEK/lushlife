package w2.app.client.rcp;

import java.util.List;

import w2.app.model.Course;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CourseRcpAsync {

	void find(Long id, AsyncCallback<Course> callback);

	void add(Course course, AsyncCallback<Long> callback);

	void update(Course course, AsyncCallback<Void> callback);

	void delete(Iterable<Course> courses, AsyncCallback<List<Long>> callback);

	void findAll(int start, int length, AsyncCallback<List<Course>> callback);

}
