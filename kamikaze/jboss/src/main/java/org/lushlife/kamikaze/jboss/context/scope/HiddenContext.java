//package org.lushlife.kamikaze.jboss.context.scope;
//
//import java.util.Map;
//
//import javax.enterprise.context.spi.Contextual;
//
//import org.jboss.webbeans.context.AbstractMapContext;
//import org.jboss.webbeans.context.api.BeanStore;
//import org.jboss.webbeans.context.api.ContexutalInstance;
//import org.jboss.webbeans.context.api.helpers.AbstractMapBackedBeanStore;
//import org.lushlife.kamikaze.context.Contexts;
//import org.lushlife.kamikaze.scope.HiddenScoped;
//
//public class HiddenContext extends AbstractMapContext {
//
//	static public HiddenContext INSTANCE = new HiddenContext();
//
//	protected HiddenContext() {
//		super(HiddenScoped.class);
//	}
//
//	@Override
//	protected BeanStore getBeanStore() {
//
//		return new AbstractMapBackedBeanStore() {
//
//			@Override
//			public Map<Contextual<? extends Object>, ContexutalInstance<? extends Object>> delegate() {
//				return ()Contexts.getHiddenScope();
//			}
//
//		};
//	}
//
//	@Override
//	protected boolean isCreationLockRequired() {
//		return false;
//	}
//
// }
