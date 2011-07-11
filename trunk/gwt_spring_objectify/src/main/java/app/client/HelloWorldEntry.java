package app.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class HelloWorldEntry implements EntryPoint {

	HelloWorldAsync helloworld = GWT.create(HelloWorld.class);

	public void onModuleLoad() {
		helloworld.persist("Hello World", new AsyncCallback<Long>() {

			@Override
			public void onSuccess(Long id) {
				find(id);
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("onFailure", caught);
			}
		});
	}

	private void find(long id) {
		helloworld.find(id, new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				Window.alert(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("onFailure", caught);
			}
		});
	}
}
