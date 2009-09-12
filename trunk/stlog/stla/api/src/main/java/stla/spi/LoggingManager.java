package stla.spi;

import java.util.List;

public interface LoggingManager {

	LevelResolver getLevelResolver();

	MessageResolver getMessageResolver();

	List<LogProviderDecorator> getDecorators();

	LocaleSelector getLocaleSelector();

	void initialize();

}
