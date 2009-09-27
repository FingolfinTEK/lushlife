package example;

import org.lushlife.negroni.Mixined;

@Mixined(MixInMethodMissingSample.class)
public abstract class MixInMethodMissingImpl {

	public abstract void invoke();

}
