package w2.app.model.ref;

import w2.app.model.Course;

import com.googlecode.objectify.annotation.Entity;

@Entity
public class CourseRef extends Ref<Course> {
	private static final long serialVersionUID = 1L;

	public CourseRef() {
		super();
	}

	public CourseRef(Long refId) {
		super(refId);
	}

	@Override
	public Class<Course> getClazz() {
		return Course.class;
	}

}
