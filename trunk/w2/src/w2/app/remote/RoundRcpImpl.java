package w2.app.remote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import w2.app.client.rcp.RoundRcp;
import w2.app.model.Round;
import w2.app.util.CollectionUtil;
import w2.extensions.gwt.spring.SpringRemoteServiceServlet;

import com.google.appengine.api.datastore.QueryResultIterable;
import com.googlecode.objectify.Objectify;

@RequestMapping("*/round.rcp")
@Controller
public class RoundRcpImpl extends SpringRemoteServiceServlet implements
		RoundRcp {

	private static final long serialVersionUID = 4762409636759911182L;
	@Inject
	Objectify objectify;

	@Override
	public List<Round> findAll(int start, int length) {
		QueryResultIterable<Round> golfers = objectify.query(Round.class)
				.offset(start).limit(length).fetch();
		return CollectionUtil.toList(golfers);
	}

	@Override
	public Round findById(long userId) {
		return objectify.get(Round.class, userId);
	}

	@Transactional
	public void update(Round golfer) {
		objectify.put(golfer);
	}

	@Override
	public List<Long> delete(Iterable<Round> selectedSet) {
		List<Long> delete = new ArrayList<Long>();
		for (Round g : selectedSet) {
			delete.add(g.getId());
			objectify.delete(Round.class, g.getId());
		}
		Collections.sort(delete);
		return delete;
	}

	@Transactional
	@Override
	public Long add(Round course) {
		objectify.put(course);
		return course.getId();
	}

}
