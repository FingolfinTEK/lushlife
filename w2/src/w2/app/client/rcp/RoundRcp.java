package w2.app.client.rcp;

import java.util.List;

import w2.app.model.Round;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("round.rcp")
public interface RoundRcp extends RemoteService {
	List<Round> findAll(int start, int length);

	Long add(Round course);

	void update(Round course);

	Round findById(long userId);

	List<Long> delete(Iterable<Round> selectedSet);
}
