package lushfile.core.context;

public interface LushNamingResolver {

	LushNamingResolver init(String basePackage);

	Object getInstneceByName(String name);

}
