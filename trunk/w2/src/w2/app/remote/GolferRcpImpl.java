package w2.app.remote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import w2.app.client.rcp.GolferRcp;
import w2.app.model.Golfer;
import w2.app.util.CollectionUtil;
import w2.extensions.gwt.spring.SpringRemoteServiceServlet;

import com.google.appengine.api.datastore.QueryResultIterable;
import com.googlecode.objectify.Objectify;

@RequestMapping("*/golfer.rcp")
@Controller
public class GolferRcpImpl extends SpringRemoteServiceServlet implements
		GolferRcp {

	private static final long serialVersionUID = 4762409636759911182L;
	@Inject
	Objectify objectify;

	@Transactional
	public long addGolfer(Golfer golfer) {
		objectify.put(golfer);
		return golfer.getId();
	}

	@Override
	public List<Golfer> findAll(int start, int length) {
		QueryResultIterable<Golfer> golfers = objectify.query(Golfer.class)
				.offset(start).limit(length).fetch();
		return CollectionUtil.toList(golfers);
	}

	@Override
	public Golfer findById(long userId) {
		return objectify.get(Golfer.class, userId);
	}

	@Transactional
	public void update(Golfer golfer) {
		objectify.put(golfer);
	}

	@Override
	public List<Long> delete(Set<Golfer> selectedSet) {
		List<Long> delete = new ArrayList<Long>();
		for (Golfer g : selectedSet) {
			delete.add(g.getId());
			objectify.delete(Golfer.class, g.getId());
		}
		Collections.sort(delete);
		return delete;
	}

}
