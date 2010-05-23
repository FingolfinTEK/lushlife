package test;

import java.util.Map;

import org.lushlife.guicexml.spi.NameSpaceBinding;

public class NameSpaceBind extends NameSpaceBinding {

	@Override
	protected void configure() {
		namespace("http://code.google.com/p/lushlife/guice-xml/test")
				.toPackage(NameSpaceBind.class.getPackage());
	}
}
