package gso.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("helloworld.rcp")
public interface HelloWorld extends RemoteService {

	public String find(long id);

	public Long persist(String message);

}
