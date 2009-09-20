package com.google.code.lushlife.stla.util;

public class LogLog {

	public static void reportFailure(String message, Throwable e) {
		System.out.println(message);
		if (e != null) {
			e.printStackTrace(System.out);
		}
	}

}
