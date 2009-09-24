package org.lushlife.kamikaze.mvc.resources;

import java.io.IOException;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

import org.lushlife.kamikaze.resources.ResourceCache;

public abstract class ResourceController {
	@Inject
	ResourceCache cache;

	protected String getPackage() {
		return this.getClass().getPackage().getName();
	}

	@GET
	@Path("{path}/js")
	public StreamingOutput loadJavaScript(@PathParam("path") String id) {
		final String path = (getPackage() + ".js.").replace(".", "/") + id;
		return new StreamingOutput() {

			public void write(OutputStream output) throws IOException,
					WebApplicationException {
				cache.write(path).to(output);
			}

		};
	}

	@GET
	@Path("{path}/css")
	@Consumes("text/css")
	public StreamingOutput loadCss(@PathParam("path") String id) {
		final String path = (getPackage() + ".css.").replace(".", "/") + id;
		return new StreamingOutput() {

			public void write(OutputStream output) throws IOException,
					WebApplicationException {
				cache.write(path).to(output);
			}

		};
	}
}
