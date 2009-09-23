package negroni.extension.jmx;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

public abstract class AbstractMBeanClient implements MBeanClientMixin {
	ObjectName objectName;
	MBeanServerConnection server;

	public ObjectName getObjectName() {
		return objectName;
	}

	public void setObjectName(ObjectName objectName) {
		this.objectName = objectName;
	}

	public MBeanServerConnection getServer() {
		return server;
	}

	public void setServer(MBeanServerConnection server) {
		this.server = server;
	}
}
