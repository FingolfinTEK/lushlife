package lushfile.core.context;

import java.util.HashMap;

import com.google.inject.servlet.RequestScoped;

@RequestScoped
public class RequestScopedMap extends HashMap<String, Object> {

	private static final long serialVersionUID = 7943776061761666324L;

}
