package glassbottle2;

import java.net.URL;

public interface WebBeansBinder
{

   public void install(WebBeansModule module);

   public void clazz(Class<?> clazz);

   public void xml(URL url);

}
