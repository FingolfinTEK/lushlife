package org.lushlife.kamikaze.mvc.view;

import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

import org.lushlife.kamikaze.Kamikaze;

public class Render implements StreamingOutput {
	private Class<? extends Page> viewClazz;

	public Render(Class<? extends Page> viewClazz) {
		this.viewClazz = viewClazz;
	}

	public void write(OutputStream outputStream) throws IOException,
			WebApplicationException {
		Page page = Kamikaze.getInjector().getInstance(viewClazz);
		page.write(outputStream);
	}
}
