package org.lushlife.guicexml;

import org.lushlife.stla.Info;

public enum GuiceXmlLogMessage {
	@Info("read xml file [{0}]")
	READ_XML_FILE,

	@Info("install component [{0}]")
	INSTALL_COMPONENT,

	@Info("install factory [{0}]")
	INSTALL_FACTORY
}
