package lushfile.plugins.resources;

import java.io.IOException;
import java.io.OutputStream;

public interface WriteTo {

	public void to(OutputStream stream) throws IOException;
}
