package gso.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface HelloWorldAsync {

	void find(long id, AsyncCallback<String> callback);

	void persist(String message, AsyncCallback<Long> callback);

}
