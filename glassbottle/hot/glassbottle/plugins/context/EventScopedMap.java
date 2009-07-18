package glassbottle.plugins.context;

import glassbottle.plugins.context.scope.EventScoped;

import java.util.HashMap;


@EventScoped
public class EventScopedMap extends HashMap<String, Object>
{
   private static final long serialVersionUID = -7669259251435470850L;
}
