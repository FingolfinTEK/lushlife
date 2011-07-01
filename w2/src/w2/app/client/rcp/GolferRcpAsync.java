package w2.app.client.rcp;

import java.util.List;
import java.util.Set;

import w2.app.model.Golfer;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GolferRcpAsync {

	void addGolfer(Golfer golfer, AsyncCallback<Long> callback);

	void update(Golfer golfer, AsyncCallback<Void> callback);

	void findById(long userId, AsyncCallback<Golfer> golfer);

	void findAll(int start, int length, AsyncCallback<List<Golfer>> callback);

	void delete(Set<Golfer> selectedSet, AsyncCallback<List<Long>> asyncCallback);

}
