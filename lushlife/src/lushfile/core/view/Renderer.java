package lushfile.core.view;

import java.io.IOException;

public interface Renderer {
	public void render() throws IOException;

	public Renderer init(String viewResouces);
}
