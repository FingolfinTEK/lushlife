package org.lushlife.kamikaze.json;import java.io.IOException;import java.io.OutputStream;import java.lang.annotation.Annotation;import java.lang.reflect.Type;import javax.ws.rs.Produces;import javax.ws.rs.WebApplicationException;import javax.ws.rs.core.MediaType;import javax.ws.rs.core.MultivaluedMap;import javax.ws.rs.ext.MessageBodyWriter;import javax.ws.rs.ext.Provider;import net.sf.json.JSONObject;@Provider@Produces(MediaType.APPLICATION_JSON)public class JSONWriter implements MessageBodyWriter {	public boolean isWriteable(Class type, Type genericType,			Annotation[] annotations, MediaType mediaType) {		return true;	}	public void writeTo(Object arg0, Class arg1, Type arg2, Annotation[] arg3,			javax.ws.rs.core.MediaType arg4, MultivaluedMap arg5,			OutputStream arg6) throws IOException, WebApplicationException {		try {			JSONObject.fromObject(arg0);			arg6.write(JSONObject.fromObject(arg0).toString().getBytes());		} catch (Exception e) {			throw new WebApplicationException(e);		}	}	public long getSize(Object t, Class type, Type genericType,			Annotation[] annotations, MediaType mediaType) {		return -1;	}}