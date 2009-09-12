package stla;

public interface Log {

	public <E extends Enum<E>> boolean isEnableFor(E logId);

	public <E extends Enum<E>> void log(E logId, Object... params);

	public <E extends Enum<E>> void log(E logId, Throwable e);

}
