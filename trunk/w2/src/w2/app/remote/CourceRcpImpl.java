package w2.app.remote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import w2.app.client.rcp.CourseRcp;
import w2.app.model.Course;
import w2.app.util.CollectionUtil;
import w2.extensions.gwt.spring.SpringRemoteServiceServlet;

import com.google.appengine.api.datastore.QueryResultIterable;
import com.googlecode.objectify.Objectify;

@RequestMapping("*/course.rcp")
@Controller
public class CourceRcpImpl extends SpringRemoteServiceServlet implements
		CourseRcp {
	@Inject
	Objectify objectify;

	@Override
	public Course find(Long id) {
		return objectify.find(Course.class, id);
	}

	@Transactional
	public Long add(Course course) {
		objectify.put(course);
		return course.getId();
	}

	@Transactional
	@Override
	public void update(Course course) {
		objectify.put(course);
	}

	public List<Long> delete(Iterable<Course> courses) {
		List<Long> delete = new ArrayList<Long>();
		for (Course g : courses) {
			delete.add(g.getId());
			objectify.delete(Course.class, g.getId());
		}
		Collections.sort(delete);
		return delete;
	}

	@Override
	public List<Course> findAll(int start, int length) {
		QueryResultIterable<Course> courses = objectify.query(Course.class)
				.offset(start).limit(length).fetch();
		return CollectionUtil.toList(courses);
	}

}
