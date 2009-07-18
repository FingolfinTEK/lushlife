package glassbottle.plugins.view;

public interface ViewHandler
{
   public void render(Class<? extends Page> value);

   public void redirect(Class<? extends Page> value);
}
