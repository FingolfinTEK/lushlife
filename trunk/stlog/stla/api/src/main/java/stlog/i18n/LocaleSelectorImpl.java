package stlog.i18n;

import java.util.Locale;

import stlog.spi.LocaleSelector;

public class LocaleSelectorImpl implements LocaleSelector {

	ThreadLocal<Locale> threadLocale = new ThreadLocal<Locale>();

	public Locale getLocale() {
		Locale locale = threadLocale.get();
		if (locale != null) {
			return locale;
		}
		return Locale.getDefault();
	}

	public void setLocale(Locale locale) {
		threadLocale.set(locale);
	}

}
