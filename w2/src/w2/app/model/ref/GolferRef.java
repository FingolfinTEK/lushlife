package w2.app.model.ref;

import w2.app.model.Course;
import w2.app.model.Golfer;

import com.googlecode.objectify.annotation.Entity;

@Entity
public class GolferRef extends Ref<Golfer> {
	private static final long serialVersionUID = 1L;

	public GolferRef() {
		super();
	}

	public GolferRef(Long refId) {
		super(refId);
	}

	@Override
	public Class<Golfer> getClazz() {
		return Golfer.class;
	}

}
