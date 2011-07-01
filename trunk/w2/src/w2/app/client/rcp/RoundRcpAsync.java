package w2.app.client.rcp;

import java.util.List;

import w2.app.model.Round;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RoundRcpAsync {

	void add(Round course, AsyncCallback<Long> callback);

	void delete(Iterable<Round> courses, AsyncCallback<List<Long>> callback);

	void findAll(int start, int length, AsyncCallback<List<Round>> callback);

	void update(Round course, AsyncCallback<Void> callback);

	void findById(long userId, AsyncCallback<Round> callback);

}
