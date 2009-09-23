package negroni;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.ThreadMXBean;

import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.lushlife.negroni.Enhancer;
import org.lushlife.negroni.Negroni;
import org.lushlife.negroni.core.impl.guice.GuiceConfigurator;
import org.lushlife.negroni.extension.jmx.AbstractMBeanClient;
import org.lushlife.negroni.extension.jmx.MBeanClientMixin;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.matcher.Matchers;

public class JMXInvokeTest {

	static public abstract class Memory implements MemoryMXBean,
			MBeanClientMixin {
		public ObjectName getObjectName() {
			try {
				return new ObjectName(ManagementFactory.MEMORY_MXBEAN_NAME);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		public MBeanServerConnection getServer() {
			return ManagementFactory.getPlatformMBeanServer();
		}
	}

	static public abstract class Loader implements ClassLoadingMXBean,
			MBeanClientMixin {
		public ObjectName getObjectName() {
			try {
				return new ObjectName(
						ManagementFactory.CLASS_LOADING_MXBEAN_NAME);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		public MBeanServerConnection getServer() {
			return ManagementFactory.getPlatformMBeanServer();
		}
	}

	static public abstract class Thread extends AbstractMBeanClient implements
			ThreadMXBean {
		public ObjectName getObjectName() {
			try {
				return new ObjectName(ManagementFactory.THREAD_MXBEAN_NAME);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		public MBeanServerConnection getServer() {
			return ManagementFactory.getPlatformMBeanServer();
		}
	}

	@Test
	public void a01() throws MalformedObjectNameException, NullPointerException {
		if (!(Negroni.config() instanceof GuiceConfigurator)) {
			return;
		}

		Enhancer com = ((GuiceConfigurator) Negroni.config()).add(Memory.class)
				.add(Loader.class).add(Thread.class).add(new Module() {

					public void configure(Binder binder) {
						binder.bindInterceptor(Matchers
								.subclassesOf(Loader.class), Matchers.any(),
								new MethodInterceptor() {

									public Object invoke(
											MethodInvocation invocation)
											throws Throwable {
										System.out.println("call interceptor "
												+ invocation.getMethod());
										return invocation.proceed();
									}

								});
					}
				}).create();
		Memory m = com.getInstance(Memory.class);
		m.gc();
		long max = m.getHeapMemoryUsage().getMax();
		System.out.println(max);
		Loader loader = com.getInstance(Loader.class);
		long loadclasses = loader.getLoadedClassCount();
		System.out.println(loadclasses);
		Thread g = com.getInstance(Thread.class);
		System.out.println(g.getCurrentThreadUserTime());
	}

}
