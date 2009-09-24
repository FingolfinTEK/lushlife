package org.lushlife.kamikaze.resources;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.lushlife.kamikaze.util.date.StartupTime;
import org.lushlife.kamikaze.util.io.WriteTo;
import org.lushlife.kamikaze.util.loader.ClassLoaderUtil;
import org.lushlife.kamikaze.util.lock.DoubleCheckBlocking;
import org.lushlife.stla.Log;
import org.lushlife.stla.Logging;

@Singleton
public class ResourceCacheImpl implements ResourceCache {
	static Log logger = Logging.getLog(ResourceCacheImpl.class);

	@Inject
	ClassLoader loader;

	@Inject
	@StartupTime
	Long starupTime;

	private Lock lock = new ReentrantLock();

	private Map<String, byte[]> cache;

	@PostConstruct
	public void init() {
		// try {
		// cache = CacheManager.getInstance().getCacheFactory().createCache(
		// new ConcurrentHashMap());
		// } catch (CacheException e) {
		// throw new RuntimeException(e);
		// }
		cache = new ConcurrentHashMap<String, byte[]>();
	}

	public WriteTo write(final String resource) {
		return new WriteTo() {
			public void to(OutputStream stream) throws IOException {
				byte[] out = new DoubleCheckBlocking<String, byte[]>() {

					@Override
					protected Map<String, byte[]> cache() {
						return cache;
					}

					@Override
					protected byte[] create() {
						try {
							return ClassLoaderUtil.load(loader, resource)
									.asBytes();
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					}

					@Override
					protected String key() {
						return resource;
					}

					@Override
					protected Lock lock() {
						return lock;
					}

				}.get();
				stream.write(out);
			}
		};
	}

}
