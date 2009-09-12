package stlog.spi;

public interface LogProviderFactory {
	LogProvider getLogger(String category);

	void initialize();
}
