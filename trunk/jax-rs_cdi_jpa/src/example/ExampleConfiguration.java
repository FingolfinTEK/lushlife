package example;

import javax.enterprise.inject.Produces;

import org.lushlife.persistence.PersistenceUnitName;

public class ExampleConfiguration {

	@PersistenceUnitName
	@Produces
	static public String PERSITENCE_UNIT = "transactions-optional";
}
