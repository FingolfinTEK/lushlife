package glassbottle.plugins.navigator;

import java.lang.reflect.Method;

public interface NavigationManager
{

   public void navigate(Method method, Object returnValue);

   public void navigate(Method method, Navigation navigation);

}
