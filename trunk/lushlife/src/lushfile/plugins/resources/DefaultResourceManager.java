package lushfile.plugins.resources;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletResponse;

import lushfile.core.LushLife;
import lushfile.core.lock.DoubleCheckBlocking;
import lushfile.core.util.LushIO;
import lushfile.plugins.context.RequestContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class DefaultResourceManager implements ResourceManager {
	static Logger logger = LoggerFactory
			.getLogger(DefaultResourceManager.class);

	@Inject
	ClassLoader loader;

	@Inject
	@Named("startupTime")
	Long starupTime;

	private Lock lock = new ReentrantLock();

	@Inject
	Provider<RequestContext> context;

	@Inject
	Provider<HttpServletResponse> response;

	private Map<String, byte[]> resources = new ConcurrentHashMap<String, byte[]>();

	@Override
	public WriteTo write(final String contextName, final String type,
			final String resource) {
		return new WriteTo() {
			@Override
			public void to(OutputStream stream) throws IOException {
				byte[] out = new DoubleCheckBlocking<String, byte[]>() {

					@Override
					protected Map<String, byte[]> cache() {
						return resources;
					}

					@Override
					protected byte[] create() {
						try {
							logger
									.info(
											"load resources context={},type={},resoruce={}",
											new String[] { contextName, type,
													resource });
							return LushIO.load(
									loader,
									LushLife.resolvePackage(contextName) + "."
											+ type, resource).asBytes();
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					}

					@Override
					protected String key() {
						return contextName + "#" + type + "#" + resource;
					}

					@Override
					protected Lock lock() {
						return lock;
					}

				}.get();

				stream.write(out);
				writeHeader(type, resource, response.get());
			}
		};
	}

	protected void writeHeader(String type, String resource,
			HttpServletResponse httpServletResponse) {
		if (type.equals("css")) {
			httpServletResponse.setContentType("text/css");
		}
		if (type.equals("javascript")) {
			httpServletResponse.setContentType("text/javascript");
		}
		if (type.equals("image")) {
			String[] strs = resource.split("\\.");
			httpServletResponse
					.setContentType("image/" + strs[strs.length - 1]);
		}

	}

	public String toUrl(String contextName, String type, String resource) {
		return "/" + context.get().getBaseName() + "/Resources/" + contextName
				+ "/" + type + "/" + resource + "?" + starupTime;
	}

	@Override
	public String createUrl(String contextName, String type, String resource) {
		String url = toUrl(contextName, type, resource);
		return url;
	}
}
