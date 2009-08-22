package org.slf4j.i18n.example;

import org.slf4j.i18n.annotations.Error;
import org.slf4j.i18n.annotations.Message;

public enum LogMessages {

	@Error("error message {}") // The log level is bound to Error. 
	TEST0001,				   

	@Message("waring message {}") // The log level is not specified.
	TEST0002

}
