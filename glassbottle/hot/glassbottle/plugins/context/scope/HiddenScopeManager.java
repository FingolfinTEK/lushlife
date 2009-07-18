package glassbottle.plugins.context.scope;
//package lushlife.plugins.context.scope;
//package lushlife.plugins.context.scope;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.io.ObjectStreamClass;
//import java.util.Map;
//
//import javax.servlet.ServletRequest;
//
//import lushfile.core.context.LushContext;
//
//import org.apache.commons.codec.binary.Base64;
//
//import com.google.inject.Inject;
//import com.google.inject.name.Named;
//
//public class HiddenScopeManager {
//
//	@Inject
//	@Named("lushHiddenKey")
//	private String hiddenKey;
//
//	public String getHiddenKey() {
//		return hiddenKey;
//	}
//
//	public String toHidden() {
//		Map<String, Object> hidden = LushContext.getHiddenScope();
//		if (hidden.size() == 0) {
//			return "";
//		}
//		try {
//			ByteArrayOutputStream bao = new ByteArrayOutputStream();
//			ObjectOutputStream oos = new ObjectOutputStream(bao);
//			oos.writeObject(hidden);
//			oos.flush();
//			byte[] array = bao.toByteArray();
//			byte[] base64 = Base64.encodeBase64(array);
//			oos.close();
//			return new String(base64);
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		} finally {
//		}
//	}
//
//	@Inject
//	ClassLoader loader;
//
//	public void restore(ServletRequest req) {
//		String value = req.getParameter(hiddenKey);
//		if (value != null) {
//			if (value.isEmpty()) {
//				return;
//			}
//			byte[] base64 = value.getBytes();
//			byte[] object = Base64.decodeBase64(base64);
//			try {
//				ByteArrayInputStream input = new ByteArrayInputStream(object);
//				ObjectInputStream ois = new ObjectInputStream(input) {
//					@Override
//					protected Class<?> resolveClass(ObjectStreamClass desc)
//							throws IOException, ClassNotFoundException {
//						String className = desc.getName();
//						return loader.loadClass(className);
//					}
//				};
//				Map<String, Object> map = (Map<String, Object>) ois
//						.readObject();
//				ois.close();
//				LushContext.getHiddenScope().putAll(map);
//			} catch (IOException e) {
//				throw new RuntimeException(e);
//			} catch (ClassNotFoundException e) {
//				throw new RuntimeException(e);
//			}
//		}
//	}
//}
