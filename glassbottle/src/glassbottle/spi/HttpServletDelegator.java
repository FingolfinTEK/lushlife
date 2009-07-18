package glassbottle.spi;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HttpServletDelegator {

	void delegate(HttpServletRequest request, HttpServletResponse response);

}
