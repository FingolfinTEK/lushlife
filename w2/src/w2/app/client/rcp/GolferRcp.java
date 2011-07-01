package w2.app.client.rcp;

import java.util.List;
import java.util.Set;

import w2.app.model.Golfer;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("golfer.rcp")
public interface GolferRcp extends RemoteService {
	long addGolfer(Golfer golfer);

	List<Golfer> findAll(int start, int length);

	Golfer findById(long userId);

	void update(Golfer golfer);

	List<Long> delete(Set<Golfer> selectedSet);
}
