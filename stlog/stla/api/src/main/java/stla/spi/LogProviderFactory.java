package stla.spi;

public interface LogProviderFactory {
	LogProvider getLogger(String category);

}
