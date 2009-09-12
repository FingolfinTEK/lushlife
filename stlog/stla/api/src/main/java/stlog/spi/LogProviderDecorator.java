package stlog.spi;

public interface LogProviderDecorator {

	public LogProvider decorate(LogProvider logProvider);

}
