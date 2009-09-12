package stla.spi;

public interface LogProviderDecorator {

	public boolean isTarget(LogProvider provider);

	public LogProvider decorate(LogProvider logProvider);

}
